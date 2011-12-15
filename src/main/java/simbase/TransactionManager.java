package simbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;

import productbase.Product;

import modelbase.Entity;
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

	public boolean addTransaction(Buyer buyer, Seller seller, Product product, double price) {
		return addTransaction(new Transaction(buyer, seller, product, 1, price));
	}

	public boolean addTransaction(Buyer buyer, Seller seller, Product product, int quantity,
			double price) {
		return addTransaction(new Transaction(buyer, seller, product, quantity, price));
	}

	public boolean addTransaction(Transaction transaction) {
		int tmpQuantity = -1;
		Seller seller;
		Product product;
		String buyerName = transaction.buyer.getName();
		String sellerName = transaction.seller.getName();
		String item = transaction.prod.getName();
		int quantity = transaction.quantity;
		double price = transaction.price;
		/* Check if the seller name is valid */
		if ((seller = (Seller) sim.getAgentManager().getAgentByName(sellerName))!=null) {
			if (!transactions.containsKey(sellerName)) {
				transactions.put(sellerName, new ArrayList<Transaction>());
			}
			transactions.get(sellerName).add(transaction);

			/* Check if item is in seller's posession */
			if ((product = seller.getProduct(item))==null) {
				logger.error("This item "+item+" does not belong to seller "+sellerName);
				return false;
			}

			/* Check if item quantity is valid */
			tmpQuantity = ((Product) seller.getProduct(item)).getQuantity();
			if (tmpQuantity<0||tmpQuantity<quantity) {
				logger.error("Invalid quantity for product "+item);
				return false;
			}
			return true;

		} else {
			logger.error("No such seller name: ["+sellerName+"]");
			return false;
		}
	}

	public void processTransactions() throws Exception {
		logger.info("Processing transactions");
		for (String sellerName : transactions.keySet()) {
			transactions.put(sellerName, ((Seller) sim.agentManager.get(sellerName))
					.processTransactions(transactions.get(sellerName)));
		}
		for (String sellerName : transactions.keySet()) {
			for (Transaction t : transactions.get(sellerName)) {
				Execution execution = (Execution) t;
				processExecution(execution);
			}
			/* Transaction after processing must be removed */
			transactions.put(sellerName, new ArrayList<Transaction>());
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
			sim.bank.creditBalance(execution.buyer, execution.quantity
					*((prodValue-1)*execution.price));
			Product oldProd = (Product) sim.inventoryManager.getInventory(execution.seller,
					execution.prod).getProd();
			Product prod = new Product(oldProd);
			prod.setQuantity(execution.quantity);

			/*
			 * The product's real value is now set by the trsanction manager.
			 * This is the only place where this value is set validly. Later
			 * when querying about the product's true value, only number in
			 * inventory manager will be used
			 */
			prod.setValue(prodValue);
			oldProd.setQuantity(oldProd.getQuantity()-execution.quantity);
			sim.inventoryManager.add(execution.buyer, execution.prod);
		}
	}

	public void setAgents(EntityManager entityManager) {
		for (Entity entity : entityManager.getAll()) {
			transactions.put(entity.getName(), new ArrayList<Transaction>());
		}
	}
}
