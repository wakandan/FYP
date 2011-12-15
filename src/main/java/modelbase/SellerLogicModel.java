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
public abstract class SellerLogicModel extends AgentLogicModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#processTransaction(simbase.Execution)
	 */
	public abstract void processTransaction(Execution execution);

	public abstract double initValue(Product prod);

	/**
	 * @return
	 */
	public abstract boolean responseQuery();

}
