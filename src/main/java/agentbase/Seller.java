package agentbase;

import java.util.ArrayList;
import java.util.HashMap;

import modelbase.SellerLogicModel;

import productbase.Product;
import simbase.Execution;
import simbase.Inventory;
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
	public ArrayList processTransactions(ArrayList<Transaction> transactions,
			HashMap<String, Inventory> myInventory) {
		ArrayList executions = new ArrayList();
		Execution execution;
		Inventory inventory;
		for (Transaction transaction : transactions) {
			execution = new Execution(transaction, false);
			inventory = myInventory.get(execution.getProd().getName());
			if (inventory==null) {
				execution.setReason(String.format("Seller %5s doesn't have prod. %5s", name,
						execution.getProd().getName()));
			} else if (inventory.getQuantity()<execution.getQuantity()) {
				execution.setReason(String.format("Product %5s not available", execution.getProd()
						.getName()));
			} else {
				((SellerLogicModel) this.logicModel).processTransaction(execution, myInventory);
			}

			/*
			 * Temporarily update the product quantities. If it's not done here,
			 * there could be the case that A.quantity < available quantity, and
			 * B.quantity < available quantity, but A.quantity + B.quantity >
			 * available quantity
			 */
			if (execution.isSuccess()) {
				inventory.setQuantity(inventory.getQuantity()-execution.getQuantity());
			}
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
