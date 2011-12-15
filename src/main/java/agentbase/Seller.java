package agentbase;

import java.util.ArrayList;

import modelbase.SellerLogicModel;

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
			((SellerLogicModel) this.logicModel).processTransaction(execution);
			executions.add(execution);
		}
		return executions;
	}

	/**
	 * @param tmpProd
	 * @return
	 */
	public double initValue(Product tmpProd) {
		return ((SellerLogicModel) this.logicModel).initValue(tmpProd);
	}
}
