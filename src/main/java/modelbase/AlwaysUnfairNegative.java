package modelbase;

import java.util.Random;

import productbase.Product;
import simbase.Execution;
import simbase.Rating;
import agentbase.Buyer;
import agentbase.Seller;
import core.BaseObject;

public class AlwaysUnfairNegative extends RatingLogic {
	public Rating calcRating(Execution execution, Product prod) {
		boolean found = false;
		int rate;
		Random random = new Random();

		for (int i = 0; i < this.buyer.getTargetSeller().size(); i++) {
			String seller_name = execution.getSeller().getName();
			String target = this.buyer.getTargetSeller().get(i).getName();
			if (seller_name.equals(target)) {
				found = true;
			}
		}

		if (found) {
			rate = 0;
		} else {
			rate = (int) Math.round(1 + prod.getValue() * 4);
			if (rate >= 5)
				rate = 4 + (int) Math.round(random.nextDouble());
		}

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
