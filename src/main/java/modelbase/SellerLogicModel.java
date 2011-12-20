/**
 *
 */
package modelbase;

import java.util.HashMap;

import productbase.Product;
import simbase.Execution;
import simbase.Inventory;

/**
 * @author akai
 * 
 */
public abstract class SellerLogicModel extends AgentLogicModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#processTransaction(simbase.Execution)
	 */
	public void processTransaction(Execution execution, HashMap<String, Inventory> myInventory) {}

	public abstract double initValue(Product prod);

	/**
	 * @return
	 */
	public abstract boolean responseQuery();

}
