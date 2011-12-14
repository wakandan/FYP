package agentbase;

import java.util.ArrayList;

import productbase.Product;

public class Buyer extends Agent{
	/**
	 * @param string
	 */
	public Buyer(String name) {
		super(name);
	}

	public void placeTransaction() {
		
	}
	
	public Seller chooseSeller(ArrayList<String> sellersNames) {
		return null;
	}
	
	 public Product chooseProduct(ArrayList<String> productsList) {
		 return null;
	 }
}
