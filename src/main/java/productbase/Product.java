package productbase;

public class Product {
	String name;
	String category;
	int price;
	int quality;
	int shipmentDelay;
	public Product(String name) {
		this.name = name;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
