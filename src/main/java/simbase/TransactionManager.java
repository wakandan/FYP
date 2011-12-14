package simbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;

import productbase.Product;

import modelbase.Entity;
import agentbase.Seller;

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

	/*
	 * Add a request into transaction list. Do checks: - If the seller name is
	 * valid - If quantity requested <= quantity available
	 */
	public boolean addTransaction(String buyerName, String sellerName, String item, int quantity, double price) {
		int tmpQuantity = -1;
		Seller seller;
		Product product;
		Transaction transaction = new Transaction(buyerName, sellerName, item, quantity, price);
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

	public void processTransactions() {
		logger.info("Processing transactions");
		for (String sellerName : transactions.keySet()) {
			transactions.put(sellerName, ((Seller) sim.agentManager.get(sellerName)).processTransactions(transactions.get(sellerName)));
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
	 */
	private void processExecution(Execution execution) {
		if (execution.success) {
			sim.bank.creditBalance(execution.buyer, execution.quantity*((sim.getValue(execution.item)-1)*execution.price));
			Product oldProd = (Product) sim.prodManager.get(execution.item);
			Product prod = new Product(oldProd);
			prod.setQuantity(execution.quantity);
			oldProd.setQuantity(oldProd.getQuantity()-execution.quantity);
			sim.agentManager.getAgentByName(execution.buyer).addProduct(prod);
		}
	}

	public void setAgents(EntityManager entityManager) {
		for (Entity entity : entityManager.getAll()) {
			transactions.put(entity.getName(), new ArrayList<Transaction>());
		}
	}
}
