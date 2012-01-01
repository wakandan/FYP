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
		int rate = ((int) Math.round(1 + prod.getValue() * 4)) % 3;
		if (rate < 1)
			rate = 1;
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
