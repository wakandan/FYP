/**
 *
 */
package modelbase;

import java.util.ArrayList;

import agentbase.Buyer;
import simbase.Transaction;
import trustmodel.TrustModel;
import core.BaseObject;
import core.Configurable;

/**
 * @author akai
 * 
 */
public abstract class PurchaseLogic extends ActionLogic {
	protected Buyer		buyer;
	public TrustModel	trustModel;

	public PurchaseLogic() {
		super();
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public abstract Transaction transact();

	protected String chooseSeller(ArrayList<String> sellerList) {
		if (trustModel != null) {
			String sellerName = trustModel.chooseSeller(buyer.getName(), sellerList);
			if (sellerName != null) {
				logger.info(String.format("Buyer %s --%s--> %s ",
						buyer.name, trustModel.getClass(), sellerName));
				return sellerName;
			} else {
				System.out.println("No result for buyer " + buyer.name);
			}
		}
		return null;
	}
}
