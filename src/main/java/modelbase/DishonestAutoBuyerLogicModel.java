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
import simbase.Inventory;
import simbase.Rating;
import simbase.Transaction;

/**
 * @author akai
 * 
 */
public class DishonestAutoBuyerLogicModel extends BuyerLogicModel {
	public Rating calcRating(Seller seller, Product prod) {
		int rate = ((int) Math.round(1+prod.getValue()*4))%3;
		if (rate<1)
			rate = 1;
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
		Buyer buyer = (Buyer) this.agent;
		int prodCount = agent.getInventoryManager().getAllProductsCount();
		Random random = new Random();
		String prodName = (String) agent.getInventoryManager().getAllProductsNames()[random
				.nextInt(prodCount)];
		ArrayList<Inventory> sellerList = agent.getInventoryManager().getSellersByProductName(
				prodName);
		if (sellerList.size()>0) {
			Inventory inventory = sellerList.get(random.nextInt(sellerList.size()));
			return new Transaction(buyer, (Seller) inventory.getAgent(), inventory.getProd(), 1,
					inventory.getPrice());
		} else {
			logger.debug(String.format("No seller's selling product %5s", prodName));
			return null;
		}

	}
}
