/**
 *
 */
package modelbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import agentbase.AgentMasterConfig;
import agentbase.Seller;

import simbase.Inventory;
import simbase.Transaction;

/**
 * @author akai
 * 
 */
public class PurchaseLogicWishlist extends PurchaseLogic {
	ArrayList<String>	wishList;
	int					wishListIndex;

	public PurchaseLogicWishlist() {
		super();
	}

	public ArrayList<String> getWishList() {
		return wishList;
	}

	public void setWishList(ArrayList<String> wishList) {
		this.wishList = wishList;
	}

	public int getWishListIndex() {
		return wishListIndex;
	}

	public void setWishListIndex(int wishListIndex) {
		this.wishListIndex = wishListIndex;
	}

	/* (non-Javadoc)
	 * 
	 * @see modelbase.ActionLogic#config() */
	@Override
	public void config() {
		wishList = new ArrayList<String>(
				Arrays.asList(config.getConfigEntry("wishlist").split(",")));
	}

	/* (non-Javadoc)
	 * 
	 * @see modelbase.PurchaseLogic#pickProduct() */
	@Override
	protected String pickProduct() {
		// TODO Auto-generated method stub
		String prodName;
		ArrayList<Inventory> sellerList;
		Inventory inventory;
		Random random = new Random();
		/* If the wish list is null, then generate a new one */
		if (wishList == null) {
			ArrayList<String> wishList = new ArrayList<String>();
			int prodCount = buyer.getInventoryManager().getAllProductsCount();
			int totalNumProd = random.nextInt(prodCount);
			if (totalNumProd < 2)
				totalNumProd = 2;
			for (int i = 0; i < totalNumProd; i++)
				wishList.add((String) buyer.getInventoryManager().getAllProductsNames()[random
						.nextInt(prodCount)]);
			this.wishList = wishList;
		}

		return wishList.get(wishListIndex);
	}
}
