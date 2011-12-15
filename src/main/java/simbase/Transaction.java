package simbase;

import productbase.Product;
import agentbase.Buyer;
import agentbase.Seller;
import core.BaseObject;

public class Transaction extends BaseObject {
	Buyer	buyer;
	Seller	seller;
	Product	prod;
	int		quantity;
	double	price;

	public Transaction(Buyer buyer, Seller seller, Product prod, int quantity, double price) {
		super();
		this.buyer = buyer;
		this.seller = seller;
		this.prod = prod;
		this.quantity = quantity;
		this.price = price;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
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

	public String toString() {
		return this.buyer+" <-"+this.prod+"-> "+this.seller;
	}

}
