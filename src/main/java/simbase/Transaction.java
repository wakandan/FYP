package simbase;

import core.BaseObject;

public class Transaction extends BaseObject {
	String	buyer;
	String	seller;
	String	item;
	int		quantity;

	public Transaction(String buyer, String seller, String item, int quantity) {
		super();
		this.buyer = buyer;
		this.seller = seller;
		this.item = item;
		this.quantity = quantity;
	}

}
