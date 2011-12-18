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
<<<<<<< HEAD
		return String.format("%5s => %5s: %5.2d", this.buyer.getName(), this.seller.getName(),
=======
		return String.format("%5s => %5s: %5.2f", this.buyer.getName(), this.seller.getName(),
>>>>>>> 1dc9c46895e88b74577f9adfc18aeb777f260b18
				this.rating);
	}

}
