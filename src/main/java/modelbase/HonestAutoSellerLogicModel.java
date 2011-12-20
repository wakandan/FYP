/**
 *
 */
package modelbase;

import java.util.HashMap;

import productbase.Product;
import agentbase.Seller;
import simbase.Execution;
import simbase.Inventory;
import simbase.Rating;

/**
 * @author akai
 * 
 */
public class HonestAutoSellerLogicModel extends SellerLogicModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.AgentLogicModel#responseQuery()
	 */
	@Override
	public boolean responseQuery() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#processTransaction(simbase.Execution) Honest
	 * seller will always accept the transaction
	 */
	@Override
	public void processTransaction(Execution execution, HashMap<String, Inventory> myInventory) {
		execution.setSuccess(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#initValue(productbase.Product)
	 */
	@Override
	public double initValue(Product prod) {
		// TODO Auto-generated method stub
		return 1.2;
	}
}