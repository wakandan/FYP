/**
 *
 */
package modelbase;

import java.util.ArrayList;
import java.util.Random;

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

	@Override
	public Transaction transact() {
		// TODO Auto-generated method stub
		String prodName;
		ArrayList<Inventory> sellerList;
		Inventory inventory;
		Random random = new Random();
		/* If the wish list is null, then generate a new one */
		if (wishList==null) {
			ArrayList<String> wishList = new ArrayList<String>();
			int prodCount = buyer.getInventoryManager().getAllProductsCount();
			int totalNumProd = random.nextInt(prodCount);
			if (totalNumProd<2)
				totalNumProd = 2;
			for (int i = 0; i<totalNumProd; i++)
				wishList.add((String) buyer.getInventoryManager().getAllProductsNames()[random
						.nextInt(prodCount)]);
			this.wishList = wishList;
		}

		prodName = wishList.get(wishListIndex);
		sellerList = buyer.getInventoryManager().getSellersByProductName(prodName);

		/* If currently there's no seller having this product, skip */
		if (sellerList.size()>0) {
			inventory = sellerList.get(random.nextInt(sellerList.size()));
			wishListIndex = wishListIndex++%wishList.size();
			return new Transaction(buyer, (Seller) inventory.getAgent(), inventory.getProd(), 1,
					inventory.getPrice());
		} else {
			logger.debug(String.format("No seller's selling product %5s", prodName));
			return null;
		}
	}

}
