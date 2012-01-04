/**
 *
 */
package modelbase;

import java.util.ArrayList;
import java.util.Random;

import agentbase.Buyer;
import agentbase.Seller;
import simbase.Inventory;
import simbase.Transaction;

/**
 * Model a buyer who just joins the market to buy a random product
 * 
 * @author akai
 * 
 */
public class PurchaseLogicRandom extends PurchaseLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.PurchaseBehaviorLogic#transact()
	 */
	@Override
	public Transaction transact() {
		// TODO Auto-generated method stub
		int prodCount = buyer.getInventoryManager().getAllProductsCount();
		Random random = new Random();
		String prodName = (String) buyer.getInventoryManager().getAllProductsNames()[random
				.nextInt(prodCount)];
		ArrayList<Inventory> sellerList = buyer.getInventoryManager().getSellersByProductName(
				prodName);
		if (sellerList.size() > 0) {
			Inventory inventory = sellerList.get(random.nextInt(sellerList.size()));
			return new Transaction(buyer, (Seller) inventory.getAgent(), inventory.getProd(), 1,
					inventory.getPrice());
		} else {
			logger.debug(String.format("No seller's selling product %5s", prodName));
			return null;
		}
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
