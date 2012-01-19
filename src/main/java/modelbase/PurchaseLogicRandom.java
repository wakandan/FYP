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

	@Override
	public void config() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * 
	 * @see modelbase.PurchaseLogic#pickProduct() */
	@Override
	protected String pickProduct() {
		int prodCount = buyer.getInventoryManager().getAllProductsCount();
		Random random = new Random();
		String prodName = (String) buyer.getInventoryManager().getAllProductsNames()[random
				.nextInt(prodCount)];
		return prodName;
	}
}
