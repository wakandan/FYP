/**
 *
 */
package modelbase;

import java.util.ArrayList;

import agentbase.Buyer;
import agentbase.Seller;
import productbase.Product;
import simbase.Execution;
import simbase.Rating;

/**
 * @author akai
 * 
 */
public class DishonestAutoBuyerLogicModel extends BuyerLogicModel {
	public Rating calcRating(Seller seller, Product prod) {
		int rate = ((int) Math.round(1+prod.getValue()*4))%3;
		return new Rating((Buyer) this.agent, seller, rate);
	}

	/* (non-Javadoc)
	 * @see modelbase.BuyerLogicModel#chooseSeller(java.util.ArrayList)
	 */
	@Override
	public Seller chooseSeller(ArrayList<String> sellersNames) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see modelbase.BuyerLogicModel#chooseProduct(java.util.ArrayList)
	 */
	@Override
	public Product chooseProduct(ArrayList<String> productsList) {
		// TODO Auto-generated method stub
		return null;
	}

}
