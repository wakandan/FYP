package agentbase;

import productbase.Product;
import modelbase.PurchaseLogic;
import modelbase.RatingLogic;
import simbase.Execution;
import simbase.Rating;
import simbase.Transaction;

public class Buyer extends Agent {

	RatingLogic		ratingLogic;
	PurchaseLogic	purchaseLogic;

	/**
	 * @param string
	 */
	public Buyer(String name) {
		super(name);
	}

	public RatingLogic getRatingLogic() {
		return ratingLogic;
	}

	public void setRatingLogic(RatingLogic ratingLogic) {
		this.ratingLogic = ratingLogic;
		this.ratingLogic.setBuyer(this);
	}

	public PurchaseLogic getPurchaseLogic() {
		return purchaseLogic;
	}

	public void setPurchaseLogic(PurchaseLogic purchaseLogic) {
		this.purchaseLogic = purchaseLogic;
		this.purchaseLogic.setBuyer(this);
	}

	public Transaction makeTransaction() {
		return this.purchaseLogic.transact();
	}

	/**
	 * @param execution
	 * @return
	 */
	public Rating leaveRating(Execution execution, Product product) {
		return this.ratingLogic.calcRating(execution, product);
	}
}
