/**
 *
 */
package modelbase;

import productbase.Product;
import simbase.Execution;
import simbase.Rating;
import agentbase.Buyer;
import agentbase.Seller;
import core.BaseObject;

/**
 * @author akai
 * 
 */
public abstract class RatingLogic extends BaseObject {
	Buyer	buyer;

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public abstract Rating calcRating(Execution execution, Product product);
}
