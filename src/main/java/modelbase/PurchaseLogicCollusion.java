/**
 *
 */
package modelbase;

import java.util.ArrayList;
import java.util.HashMap;

import configbase.Config;

import agentbase.AgentMasterConfig;
import agentbase.Seller;

import simbase.Inventory;
import simbase.Transaction;

/**
 * @author akai
 * 
 */
public class PurchaseLogicCollusion extends PurchaseLogic {
	String	sellerName;

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public void config() {}

	/* (non-Javadoc)
	 * 
	 * @see modelbase.PurchaseLogic#pickProduct() */
	@Override
	protected String pickProduct() {
		HashMap<String, Inventory> productsOnSale = buyer.getInventoryManager()
				.getProductsBySellerName(sellerName);
		for (Inventory inventory : productsOnSale.values()) {
			return inventory.getProd().getName();
		}
		return null;
	}

	@Override
	protected String chooseSeller(ArrayList<String> sellerNames) {
		return sellerName;
	}

	@Override
	public void setConfig(Config config) {
		super.setConfig(config);
		this.sellerName = config.getConfigEntry("targetSeller");
	}
}