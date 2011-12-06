package productbase;

public class Product {
	String name;
	String category;
	double price;
	int quality;
	int shipmentDelay;
	public Product(String name) {
		this.name = name;
	}
	public void setPrice(double d) {
		this.price = d;
	}
	
}
