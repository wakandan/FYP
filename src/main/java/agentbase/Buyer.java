package agentbase;

import java.util.ArrayList;

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
		return null;
	}

	/* To be overridden by subclasses */
	public Product chooseProduct(ArrayList<String> productsList) {
		return null;
	}

	public Rating leaveRating(Seller seller, Product prod) {
		return this.logicModel.calcRating(seller, prod);
	}
}
