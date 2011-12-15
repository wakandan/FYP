/**
 *
 */
package modelbase;

import java.util.ArrayList;
import java.util.Random;

import agentbase.Buyer;
import agentbase.Seller;
import productbase.Product;
import simbase.Execution;
import simbase.Rating;

/**
 * @author akai
 * 
 */
public class HonestAutoBuyerLogicModel extends BuyerLogicModel {

	/*
	 * Example of an honest rating. He will leave rating reflects the product's
	 * true value
	 * 
	 * @see modelbase.AgentLogicModel#calcRating(agentbase.Seller,
	 * productbase.Product)
	 */
	@Override
	public Rating calcRating(Seller seller, Product prod) {
		Random random = new Random();
		int rate = (int) Math.round(1+prod.getValue()*4);
		if (rate>=5)
			rate = 4+(int) Math.round(random.nextDouble());
		return new Rating((Buyer) this.agent, seller, rate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.BuyerLogicModel#chooseSeller(java.util.ArrayList)
	 */
	@Override
	public Seller chooseSeller(ArrayList<String> sellersNames) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.BuyerLogicModel#chooseProduct(java.util.ArrayList)
	 */
	@Override
	public Product chooseProduct(ArrayList<String> productsList) {
		// TODO Auto-generated method stub
		return null;
	}
}
