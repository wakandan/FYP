/**
 *
 */
package simbase;


/**
 * @author akai Class to manage result of transactions.
 */
public class Execution extends Transaction {
	boolean	success;
	String	reason;

	public Execution(Transaction transaction, boolean success) {
		super(transaction.buyer, transaction.seller, transaction.item, transaction.quantity, transaction.price);
		this.success = true;
	}

}
