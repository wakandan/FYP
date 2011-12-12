package agentbase;

import java.util.ArrayList;

import productbase.Product;
import simbase.Execution;
import simbase.Transaction;

public class Seller extends Agent {
	/**
	 * @param name
	 */
	public Seller(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Each seller will have a transaction list for all the buy request. They
	 * need to return an execution list in return
	 */
	public ArrayList processTransactions(ArrayList<Transaction> transactions) {
		ArrayList executions = new ArrayList();
		Execution execution;
		for (Transaction transaction : transactions) {
			execution = new Execution(transaction, false);
			/* Check if the price is good */
			logicModel.processTransaction(execution);
			executions.add(execution);
		}
		return executions;
	}
}
