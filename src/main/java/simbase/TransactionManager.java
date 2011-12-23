package simbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;

import com.almworks.sqlite4java.SQLiteException;

import productbase.Product;

import modelbase.Entity;
import agentbase.Agent;
import agentbase.Buyer;
import agentbase.Seller;

/*
 * Class to manage transaction (buying) between agents
 * @author: akai
 * */
public class TransactionManager extends EntityManager {
	Sim										sim;
	/* A map between seller's name and an array list of transactions */
	HashMap<String, ArrayList<Transaction>>	transactions;

	public TransactionManager() {
		super();
		transactions = new HashMap<String, ArrayList<Transaction>>();
	}

	public void setSim(Sim sim) {
		this.sim = sim;
		setAgents(sim.getAgentManager().getSellers());
	}

	public Execution addTransaction(Buyer buyer, Seller seller, Product product, double price) {
		return addTransaction(new Transaction(buyer, seller, product, 1, price));
	}

	public Execution addTransaction(Buyer buyer, Seller seller, Product product, int quantity,
			double price) {
		return addTransaction(new Transaction(buyer, seller, product, quantity, price));
	}

	/*
	 * Add a transaction into seller's request list. If the transaction is
	 * deemed valid, return null; otherwise return an execution describes the
	 * reason of failing that transaction
	 */
	public Execution addTransaction(Transaction transaction) {
		Seller seller;
		Product product = null;
		String sellerName = transaction.seller.getName();
		String item = transaction.prod.getName();
		int quantity = transaction.quantity;
		Execution execution = new Execution(transaction, false);
		/* Check if the seller name is valid */
		try {
			product = sim.inventoryManager.getInventory(transaction.seller, transaction.prod)
					.getProd();
		} catch (NullPointerException e) {}
		seller = (Seller) sim.getAgentManager().getAgentByName(sellerName);
		if (seller!=null) {
			if (!transactions.containsKey(sellerName)) {
				transactions.put(sellerName, new ArrayList<Transaction>());
			}

			if (product==null) {
				/* Check if item is in seller's possession */
				execution.setReason(String.format("Seller %5s doesn't have prod. %5s", sellerName,
						item));
			} else if (product.getPriceMax()>sim.bank.getBalance(transaction.buyer.getName())) {
				/* Check for buyer's balance */
				execution.setReason(String.format("Buyer %5s: low balance",
						transaction.buyer.getName()));
			} else if (product.getQuantity()<=0) {
				/* Check if there are available quantity */
				execution.setReason(String.format("Product %5s not available", item));
			} else if (product.getQuantity()<quantity) {
				execution.setReason(String.format("Invalid quantity prod. %5s: Req %d v/s Avai %d",
						item, quantity, product.getQuantity()));
			} else {
				/* Transaction recorded, do nothing */
				transactions.get(sellerName).add(transaction);
				return null;
			}
		} else {
			execution.setReason("No such seller name: ["+sellerName+"]");
		}
		return execution;
	}

	@SuppressWarnings("unchecked")
	public void processTransactions() throws Exception {
		logger.info("Processing transactions");

		for (String sellerName : transactions.keySet()) {
			HashMap<String, Inventory> myInventory = this.sim.inventoryManager
					.getProductsBySellerName(sellerName);
			transactions.put(
					sellerName,
					((Seller) sim.agentManager.get(sellerName)).processTransactions(
							transactions.get(sellerName), myInventory));
		}
		for (String sellerName : transactions.keySet()) {
			for (Transaction t : transactions.get(sellerName)) {
				Execution execution = (Execution) t;
				logger.debug(execution);
				if (execution.success)
					processExecution(execution);
			}
			/* Transaction after processing must be removed */
			transactions.put(sellerName, new ArrayList<Transaction>());
			HashMap<String, Inventory> prodSale = sim.inventoryManager
					.getProductsBySellerName(sellerName);
			if (prodSale==null) {
				logger.debug("Restocking for "+sellerName);
				sim.inventoryManager.restock((Seller) sim.agentManager.get(sellerName));
			}
		}
		logger.info("Done processing transactions");
	}

	/**
	 * @param execution
	 * @throws Exception
	 */
	private void processExecution(Execution execution) throws Exception {
		if (execution.success) {
			double prodValue = sim.inventoryManager.getValue(execution.seller, execution.prod);
			/* Credit buyer's balance */
			sim.bank.creditBalance(execution.buyer, execution.quantity
					*((prodValue-1)*execution.price));

			Product oldProd = (Product) sim.inventoryManager.getInventory(execution.seller,
					execution.prod).getProd();
			Product prod = new Product(oldProd);
			prod.setQuantity(execution.quantity);

			/*
			 * The product's real value is now set by the transaction manager.
			 * This is the only place where this value is set validly. Later
			 * when querying about the product's true value, only number in
			 * inventory manager will be used
			 */
			prod.setValue(prodValue);
			oldProd.setQuantity(oldProd.getQuantity()-execution.quantity);
			sim.prodManager.update(oldProd);

			/* Update buyer's inventory */
			sim.inventoryManager.updateInventory(execution.buyer, execution.prod);
			sim.inventoryManager.updateInventory(execution.seller, oldProd);

			/* Leave rating to seller */
			Rating rating = execution.buyer.leaveRating(execution, prod);
			sim.ratingManager.addRating(rating);
			this.updateExecution(execution, rating);
			// logger.debug(rating);
		} else
			this.updateExecution(execution, null);
	}

	public void setAgents(EntityManager entityManager) {
		for (Entity entity : entityManager.getAll()) {
			transactions.put(entity.getName(), new ArrayList<Transaction>());
		}
	}

	public void updateExecution(Execution execution, Rating rating) throws SQLiteException {
		int rate = 0;
		if (rating==null) {
			rate = 0;
		} else
			rate = rating.rating;
		st = db.prepare("INSERT INTO Executions(buyer_name, seller_name, prod_name, status, rating, Stime) "
				+"VALUES(?, ?, ?, ?, ?, ?)");
		st.bind(1, execution.buyer.getName()).bind(2, execution.seller.getName())
				.bind(3, execution.prod.getName())
				.bind(4, (execution.success) ? Execution.STATUS_SUCCESS : Execution.STATUS_FAILED)
				.bind(5, rate).bind(6, sim.scheduler.currentTimestep);
		st.step();
	}
}
