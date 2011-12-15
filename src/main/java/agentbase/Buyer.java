package agentbase;

import java.util.ArrayList;

import modelbase.BuyerLogicModel;

import productbase.Product;
import simbase.Rating;

public class Buyer extends Agent {
	/**
	 * @param string
	 */
	public Buyer(String name) {
		super(name);
	}

	/* To be overridden by subclasses */
	public Seller chooseSeller(ArrayList<String> sellersNames) {
		return ((BuyerLogicModel)this.logicModel).chooseSeller(sellersNames);
	}

	/* To be overridden by subclasses */
	public Product chooseProduct(ArrayList<String> productsList) {
		return ((BuyerLogicModel)this.logicModel).chooseProduct(productsList);
	}

	public Rating leaveRating(Seller seller, Product prod) {
		return ((BuyerLogicModel)this.logicModel).calcRating(seller, prod);
	}
}
