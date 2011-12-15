package agentbase;

import modelbase.BuyerLogicModel;
import productbase.Product;
import simbase.Rating;
import simbase.Transaction;

public class Buyer extends Agent {
	/**
	 * @param string
	 */
	public Buyer(String name) {
		super(name);
	}

	public Transaction makeTransaction() {
		return ((BuyerLogicModel) this.logicModel).transact();
	}

	public Rating leaveRating(Seller seller, Product prod) {
		return ((BuyerLogicModel) this.logicModel).calcRating(seller, prod);
	}
}
