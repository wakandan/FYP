package simbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionManager extends EntityManager {
	Sim										sim;
	/*A map between seller's name and an array list of transactions*/
	HashMap<String, ArrayList<Transaction>>	transactions;

	public TransactionManager() {
		super();
		transactions = new HashMap<String, ArrayList<Transaction>>();
	}

	public void setSim(Sim sim) {
		this.sim = sim;
	}

	/*
	 * Add a request into transaction list. Do checks: - If the seller name is
	 * valid - If quantity requested <= quantity available
	 */
	public boolean addTransaction(String buyerName, String sellerName, String item, int quantity) {
		int tmpQuantity = -1;
		Transaction transaction = new Transaction(buyerName, sellerName, item, quantity);
		/* Check if the seller name is valid */
		if (sim.getAgentManager().getAgentByName(sellerName)!=null) {
			if (transactions.containsKey(sellerName)) {
				transactions.get(sellerName).add(transaction);
			} else {
				transactions.put(sellerName, new ArrayList<Transaction>());
				transactions.get(sellerName).add(transaction);
			}

			/* Check if item quantity is valid */
			tmpQuantity = sim.getProdManager().getQuantityByName(item);
			if (tmpQuantity<0||tmpQuantity<quantity) {
				return false;
			}
			return true;

		} else {
			logger.error("No such seller name: "+sellerName);
			return false;
		}
	}
	
	public void executeTransaction() {
		for(String sellerName: transactions.keySet()) {
		}
	}

}
