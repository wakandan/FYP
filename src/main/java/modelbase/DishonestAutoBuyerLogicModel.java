/**
 *
 */
package modelbase;

import agentbase.Buyer;
import agentbase.Seller;
import productbase.Product;
import simbase.Execution;
import simbase.Rating;

/**
 * @author akai
 * 
 */
public class DishonestAutoBuyerLogicModel extends AgentLogicModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.AgentLogicModel#responseQuery()
	 */
	@Override
	public boolean responseQuery() {
		// TODO Auto-generated method stub
		return false;
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
	 * Simple dishonest buyer. He will always leave a low rating to the seller
	 * 
	 * @see modelbase.AgentLogicModel#calcRating(agentbase.Seller,
	 * productbase.Product)
	 */
	@Override
	public Rating calcRating(Seller seller, Product prod) {
		int rate = ((int) Math.round(1+prod.getValue()*4))%3;
		return new Rating((Buyer) this.agent, seller, rate);
	}

}
