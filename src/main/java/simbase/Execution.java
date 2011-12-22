/**
 *
 */
package simbase;

/**
 * @author akai Class to manage result of transactions.
 */
public class Execution extends Transaction {
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_FAILED = 0;
	boolean	success;
	String	reason;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Execution(Transaction transaction, boolean success) {
		super(transaction.buyer, transaction.seller, transaction.prod, transaction.quantity,
				transaction.price);
		this.success = success;
	}

	public String toString() {
		return String.format("%5s <-%5s-> %5s %s %s", this.buyer, this.prod, this.seller,
				(success) ? "(OK)" : "", reason!=null ? "    ("+reason+")" : "");
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
