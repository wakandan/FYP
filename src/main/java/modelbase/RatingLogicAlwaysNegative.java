/**
 *
 */
package modelbase;

import productbase.Product;
import agentbase.Buyer;
import agentbase.Seller;
import simbase.Execution;
import simbase.Rating;

/**
 * @author akai
 * 
 */
public class RatingLogicAlwaysNegative extends RatingLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.RatingBehaviorLogic#calcRating(simbase.Execution)
	 */
	@Override
	public Rating calcRating(Execution execution, Product prod) {
		int rate = ((int) Math.round(1+prod.getValue()*4))%3;
		if (rate<1)
			rate = 1;
		return new Rating(this.buyer, execution.getSeller(), rate);
	}
}
