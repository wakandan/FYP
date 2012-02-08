/**
 *
 */
package modelbase;

import java.util.Random;

import productbase.Product;
import simbase.Execution;
import simbase.Rating;
import agentbase.Seller;

/**
 * This rating behavior logic will simply reflect the quality of the product
 * 
 * @author akai
 * 
 */
public class RatingLogicRandom extends RatingLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.RatingBehaviorLogic#calcRating(agentbase.Seller,
	 * productbase.Product)
	 */
	@Override
	public Rating calcRating(Execution execution, Product prod) {
		// TODO Auto-generated method stub
		Random random = new Random();
		int rate = (int) Math.round(1 + random.nextInt()%4);		
		return new Rating(this.buyer.getName(), execution.getSeller().getName(), rate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.ActionLogic#config()
	 */
	@Override
	public void config() {
		// TODO Auto-generated method stub

	}

}
