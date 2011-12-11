package simbase;

import core.BaseObject;

public class Transaction extends BaseObject {
	String	buyer;
	String	seller;
	String	item;
	int		quantity;
	double	price;

	public Transaction(String buyer, String seller, String item, int quantity, double price) {
		super();
		this.buyer = buyer;
		this.seller = seller;
		this.item = item;
		this.quantity = quantity;
		this.price = price;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
