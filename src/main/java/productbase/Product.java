package productbase;

public class Product {
	String	name;
	String	category;
	double	price;
	int		quality;
	int		shipmentDelay;
	int		quantity;

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product(String name) {
		this.name = name;
	}

	public void setPrice(double d) {
		this.price = d;
	}
	
	public String toString() {
		return String.format("%s(%d-%.3f)", name, quantity, price);
	}

}
