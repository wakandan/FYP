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
public class DishonestAutoSellerLogicModel extends AgentLogicModel {

	/* (non-Javadoc)
	 * @see modelbase.AgentLogicModel#responseQuery()
	 */
	@Override
	public boolean responseQuery() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see modelbase.LogicModel#processTransaction(simbase.Execution)
	 * Dishonest seller will always decline transaction. This is just an 
	 * example
	 */
	@Override
	public void processTransaction(Execution execution) {
		execution.setSuccess(false);
	}

	/* (non-Javadoc)
	 * @see modelbase.LogicModel#initValue(productbase.Product)
	 */
	@Override
	public double initValue(Product prod) {
		// TODO Auto-generated method stub
		return 0.8;
	}

}
