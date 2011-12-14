/**
 *
 */ 
package modelbase;

import productbase.Product;
import simbase.Execution;

/**
 * @author akai
 *
 */
public class HonestAutoBuyerLogicModel extends AgentLogicModel{

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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see modelbase.LogicModel#initValue(productbase.Product)
	 */
	@Override
	public double initValue(Product prod) {
		// TODO Auto-generated method stub
		return 0;
	}

}
