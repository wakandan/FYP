package productbase;

import modelbase.Entity;

public class Product extends Entity {
	int	category;
	double	priceMin, priceMax;
	double	value;
	int		quantity;

	public Product(Product prod) {
		super(prod.getName());
		this.priceMax = prod.getPriceMax();
		this.priceMin = prod.getPriceMin();
		this.quantity = prod.getQuantity();
		this.category = prod.getCategory();
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int i) {
		this.category = i;
	}

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product(String name) {
		super(name);
	}
}
