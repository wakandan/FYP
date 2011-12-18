package simbase;

import modelbase.Entity;
import agentbase.Buyer;
import agentbase.Seller;

public class Rating extends Entity {
	Buyer	buyer;
	Seller	seller;
	double	rating;

	public Rating(Buyer buyer, Seller seller, double rating) {
		super();
		this.buyer = buyer;
		this.seller = seller;
		this.rating = rating;
	}

	public String toString() {
		return String.format("%5s => %5s: %5.2f", this.buyer.getName(), this.seller.getName(),
				this.rating);
	}

}
