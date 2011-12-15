/**
 *
 */
package modelbase;

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
public class HonestAutoBuyerLogicModel extends AgentLogicModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.AgentLogicModel#responseQuery()
	 */
	@Override
	public boolean responseQuery() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#processTransaction(simbase.Execution)
	 */
	@Override
	public void processTransaction(Execution execution) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.LogicModel#initValue(productbase.Product)
	 */
	@Override
	public double initValue(Product prod) {
		// TODO Auto-generated method stub
		return 0;
	}

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
}
