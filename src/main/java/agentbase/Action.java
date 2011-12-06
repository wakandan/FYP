/**
 *
 */ 
package agentbase;

import productbase.Product;

/**
 * @author akai
 *
 */
public abstract class Action {
	String transactionId;
	Product prod;
	Agent seller, buyer;
}
