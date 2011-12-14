/**
 *
 */ 
package modelbase;

import productbase.Product;
import agentbase.Seller;
import simbase.Execution;

/**
 * @author akai
 *
 */
public class HonestAutoSellerLogicModel extends AgentLogicModel {

	/* (non-Javadoc)
	 * @see modelbase.AgentLogicModel#responseQuery()
	 */
	@Override
	public boolean responseQuery() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see modelbase.LogicModel#processTransaction(simbase.Execution)
	 * Honest seller will always accept the transaction
	 */
	@Override
	public void processTransaction(Execution execution) {
		Seller seller = (Seller) agent;
		execution.setSuccess(true);
	}

	/* (non-Javadoc)
	 * @see modelbase.LogicModel#initValue(productbase.Product)
	 */
	@Override
	public double initValue(Product prod) {
		// TODO Auto-generated method stub
		return 1.2;
	}
}
