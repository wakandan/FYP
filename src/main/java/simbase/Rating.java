package simbase;

import modelbase.Entity;
import agentbase.Buyer;
import agentbase.Seller;

public class Rating extends Entity {
	Buyer	buyer;
	Seller	seller;
	int		rating;

	public Rating(Buyer buyer, Seller seller, int rating) {
		super();
		this.buyer = buyer;
		this.seller = seller;
		this.rating = rating;
	}

	public String toString() {
		return String.format("%5s => %5s: %5.2d", this.buyer.getName(), this.seller.getName(),
				this.rating);
	}

}
