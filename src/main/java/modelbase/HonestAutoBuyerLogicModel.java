/**
 *
 */
package modelbase;

import java.util.ArrayList;
import java.util.Random;

import agentbase.Buyer;
import agentbase.Seller;
import productbase.Inventory;
import productbase.Product;
import simbase.Execution;
import simbase.Rating;
import simbase.Transaction;

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

	/* (non-Javadoc)
	 * @see modelbase.BuyerLogicModel#transact()
	 */
	@Override
	public Transaction transact() {
		// TODO Auto-generated method stub
		return null;
	}

}
