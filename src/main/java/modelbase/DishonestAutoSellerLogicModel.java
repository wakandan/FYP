/**
 *
 */
package modelbase;

import java.util.HashMap;

import agentbase.Seller;
import productbase.Product;
import simbase.Execution;
import simbase.Inventory;
import simbase.Rating;

/**
 * @author akai
 * 
 */
public class DishonestAutoSellerLogicModel extends SellerLogicModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.AgentLogicModel#responseQuery()
	 */
	@Override
	public boolean responseQuery() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#processTransaction(simbase.Execution) Dishonest
	 * seller will always decline transaction. This is just an example
	 */
	@Override
	public void processTransaction(Execution execution, HashMap<String, Inventory> myInventory) {
		execution.setSuccess(false);
		execution.setReason("Refuse to sell");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#initValue(productbase.Product)
	 */
	@Override
	public double initValue(Product prod) {
		// TODO Auto-generated method stub
		return 0.8;
	}
}
