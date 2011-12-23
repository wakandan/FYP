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
public class RatingLogicTruthful extends RatingLogic {

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
		int rate = (int) Math.round(1+prod.getValue()*4);
		if (rate>=5)
			rate = 4+(int) Math.round(random.nextDouble());
		return new Rating(this.buyer, execution.getSeller(), rate);
	}

}
