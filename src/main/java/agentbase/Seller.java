package agentbase;

import productbase.Product;

public class Seller extends Agent{
	/**
	 * @param name
	 */
	public Seller(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void registerShipment() {
		
	}
	
	public Product getProduct() {
		return null;
	}
	
	public void addProduct(Product prod) {
		super.addProduct(prod);
	}
}
