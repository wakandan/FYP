/**
 *
 */
package modelbase;

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

	@Override
	public Transaction transact() {
		HashMap<String, Inventory> productsOnSale = buyer.getInventoryManager()
				.getProductsBySellerName(sellerName);
		for (Inventory inventory : productsOnSale.values()) {
			return new Transaction(buyer, (Seller) inventory.getAgent(), inventory.getProd(), 1,
					inventory.getPrice());
		}
		return null;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public void config() {
		this.sellerName = ((AgentMasterConfig) config).getConfigEntry("targetSeller");
	}
}