package simbase;

import modelbase.Entity;
import agentbase.Buyer;
import agentbase.Seller;

public class Rating extends Entity {
	String	buyer_name;
	String	seller_name;
	int		rating;
	int		stime;

	public Rating(String buyer_name, String seller_name, int rating) {
		super();
		this.buyer_name = buyer_name;
		this.seller_name = seller_name;
		this.rating = rating;
	}

	public Rating(Buyer buyer, Seller seller, int rating) {
		super();
		this.buyer_name = buyer.getName();
		this.seller_name = seller.getName();
		this.rating = rating;
	}

	public String toString() {
		return String.format("%5s => %5s: %5.2d", buyer_name, seller_name, this.rating);
	}

	public int getRating() {
		return rating;
	}

	public int getStime() {
		return stime;
	}

	public void setStime(int stime) {
		this.stime = stime;
	}

}
