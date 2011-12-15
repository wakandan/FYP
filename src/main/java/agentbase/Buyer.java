package agentbase;

import java.util.ArrayList;

import modelbase.BuyerLogicModel;
import productbase.Product;
import simbase.Rating;
import simbase.Transaction;

public class Buyer extends Agent {

	ArrayList<String>	wishList;
	int					wishListIndex;

	/**
	 * @param string
	 */
	public Buyer(String name) {
		super(name);
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

	public Transaction makeTransaction() {
		return ((BuyerLogicModel) this.logicModel).transact();
	}

	public Rating leaveRating(Seller seller, Product prod) {
		return ((BuyerLogicModel) this.logicModel).calcRating(seller, prod);
	}
}
