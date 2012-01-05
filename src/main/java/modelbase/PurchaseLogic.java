/**
 *
 */
package modelbase;

import java.util.ArrayList;
import java.util.Random;

import agentbase.Buyer;
import agentbase.Seller;
import simbase.Inventory;
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

	/* Follow command pattern for create a transaction
	 * 
	 * - Pick a product name
	 * 
	 * - Get a list of sellers who sell that product
	 * 
	 * - Have the model's trust model to pick a good seller (given the trust
	 * model is available)
	 * 
	 * - If the trust model is not configured/unable to find, then pick the
	 * seller name according to the model's custom strategy. Usually a random
	 * seller will be picked, that's why it's the default behavior */
	public Transaction transact() {
		String prodName = pickProduct();
		ArrayList<String> sellerNames = buyer.getInventoryManager().getSellerNamesByProductName(
				prodName);
		if (sellerNames != null) {
			String sellerName = chooseSeller(sellerNames);
			if (sellerName == null) {
				sellerName = pickSeller(sellerNames);
			}
			if (sellerName != null) {
				Inventory inventory = buyer.getInventoryManager()
						.getInventory(sellerName, prodName);
				return new Transaction(buyer, (Seller) inventory.getAgent(), inventory.getProd(),
						1, inventory.getPrice());
			}
		} else {
			logger.info("No seller for prod: " + prodName);
		}
		return null;
	}

	/* Use available trust model to choose between sellers */
	protected String chooseSeller(ArrayList<String> sellerList) {
		if (trustModel != null) {
			String sellerName = trustModel.chooseSeller(buyer.getName(), sellerList);
			if (sellerName != null) {
				logger.info(String.format("Buyer %s --%s--> %s ", buyer.name,
						trustModel.getClass(), sellerName));
				return sellerName;
			}
		}
		return null;
	}

	/* Use the model's strategy of picking the product name to be bought */
	protected abstract String pickProduct();

	/* Use the model's custome strategy to pick a seller name, given the model's
	 * trust model was not used. By default the model will just pick a random
	 * seller from the list */
	protected String pickSeller(ArrayList<String> sellerNames) {
		return pickRandomSeller(sellerNames);
	}

	/* Pick a random seller from available seller names */
	protected String pickRandomSeller(ArrayList<String> sellerNames) {
		Random random = new Random();
		return sellerNames.get(random.nextInt(sellerNames.size()));
	}
}
