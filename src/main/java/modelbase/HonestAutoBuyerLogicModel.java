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

	/*
	 * Autobuyer will pick a product out of his wishlist and purchase
	 * sequentially. A wishlist is created if it's null. Wishlist size if random
	 * and products in the wishlist is also random. Seller will pick products in
	 * a cyclic manner
	 * 
	 * @see modelbase.BuyerLogicModel#transact()
	 */
	@Override
	public Transaction transact() {
		Buyer buyer = ((Buyer) agent);
		String prodName;
		ArrayList<Inventory> sellerList;
		Inventory inventory;
		Random random = new Random();
		int wishListIndex = buyer.getWishListIndex();

		/* If the wish list is null, then generate a new one */
		if (buyer.getWishList()==null) {			
			ArrayList<String> wishList = new ArrayList<String>();
			int prodCount = agent.getInventoryManager().getAllProductsCount();
			int totalNumProd = random.nextInt(prodCount);
			if (totalNumProd<2)
				totalNumProd = 2;
			for (int i = 0; i<totalNumProd; i++)
				wishList.add((String) agent.getInventoryManager().getAllProductsNames()[random
						.nextInt(prodCount)]);
			buyer.setWishList(wishList);
		}

		prodName = buyer.getWishList().get(wishListIndex);
		sellerList = agent.getInventoryManager().getSellersByProductName(prodName);
		inventory = sellerList.get(random.nextInt(sellerList.size()));
		buyer.setWishListIndex(wishListIndex++%buyer.getWishList().size());
		return new Transaction((Buyer) this.agent, (Seller) inventory.getAgent(),
				inventory.getProd(), 1, inventory.getPrice());

	}
}
