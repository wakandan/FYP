/**
 *
 */
package modelbase;

import java.util.ArrayList;

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
public class DishonestAutoBuyerLogicModel extends BuyerLogicModel {
	public Rating calcRating(Seller seller, Product prod) {
		int rate = ((int) Math.round(1+prod.getValue()*4))%3;
		return new Rating((Buyer) this.agent, seller, rate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.BuyerLogicModel#chooseInventory()
	 */
	@Override
	public Transaction transact() {
		// TODO Auto-generated method stub
		return null;
	}
}
