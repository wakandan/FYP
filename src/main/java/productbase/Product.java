package productbase;

import modelbase.Entity;

public class Product extends Entity{
	String	category;
	double	priceMin, priceMax;
	public double getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(double priceMin) {
		this.priceMin = priceMin;
	}

	public double getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(double priceMax) {
		this.priceMax = priceMax;
	}

	int		quality;
	int		shipmentDelay;
	int		quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product(String name) {
		super();
		this.name = name;
	}

	/**
	 * @param entity
	 */
	public Product(Entity entity) {
		super();
	}

	public String toString() {
		return String.format("%s(%d:%.3f-%.3f)", name, quantity, priceMin, priceMax);
	}

}
