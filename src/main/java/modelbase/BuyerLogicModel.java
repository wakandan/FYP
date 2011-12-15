/**
 *
 */
package modelbase;

import java.util.ArrayList;

import productbase.Product;
import simbase.Rating;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public abstract class BuyerLogicModel extends AgentLogicModel {
	public abstract Rating calcRating(Seller seller, Product prod);

	/**
	 * @param sellersNames
	 * @return
	 */
	public abstract Seller chooseSeller(ArrayList<String> sellersNames);

	public abstract Product chooseProduct(ArrayList<String> productsList);
}
