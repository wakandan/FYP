/**
 *
 */ 
package modelbase;

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
	 */
	@Override
	public void processTransaction(Execution execution) {
		Seller seller = (Seller) agent;
	}
}
