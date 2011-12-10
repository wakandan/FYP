/**
 *
 */
package core;

/**
 * @author akai
 * 
 */
public class Account {
	double	balance;

	/**
	 * @param value
	 */
	public void setAccount(double value) {
		this.balance += value;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
