/**
 *
 */
package modelbase;

import productbase.Product;
import simbase.Rating;
import simbase.Transaction;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public abstract class BuyerLogicModel extends AgentLogicModel {
	public abstract Rating calcRating(Seller seller, Product prod);

	/**
	 * @param sellersNames
	 * @return A transaction to carry out at each time step. It involves
	 *         choosing a seller & product
	 */

	public abstract Transaction transact();
}
