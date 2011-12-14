/**
 *
 */
package productbase;

import java.util.ArrayList;
import java.util.HashMap;

import agentbase.Agent;
import agentbase.Seller;

import com.almworks.sqlite4java.SQLiteStatement;

import modelbase.Entity;
import generatorbase.EntityManager;

/**
 * It replies queries for prices & inventories
 * 
 * @author akai
 */
public class InventoryManager extends EntityManager {

	HashMap<String, ArrayList<Inventory>>	owners; /*
										 * Hashmap for Seller's name ->
										 * Inventories
										 */
	HashMap<String, ArrayList<Inventory>>	stores; /*
										 * Hashmap for Inventory's name ->
										 * Seller's names
										 */

	public InventoryManager() {
		super();
		owners = new HashMap<String, ArrayList<Inventory>>();
		stores = new HashMap<String, ArrayList<Inventory>>();
	}

	public void add(Entity e) throws Exception {
		Inventory inventory = (Inventory) e;
		super.add(e);
		String sql = "INSERT INTO Inventories(agent_name, prod_name, quantity) VALUES(?, ?, ?)";
		SQLiteStatement st = db.prepare(sql);
		st.bind(1, inventory.getAgentName()).bind(2, inventory.getProdName()).bind(3, inventory.getQuantity());
		st.step();

		if (!owners.containsKey(inventory.getAgentName())) {
			owners.put(inventory.getAgentName(), new ArrayList<Inventory>());
		}
		owners.get(inventory.agentName).add(inventory);

		if (!stores.containsKey(inventory.getProdName())) {
			stores.put(inventory.getProdName(), new ArrayList<Inventory>());
		}
		stores.get(inventory.prodName).add(inventory);

	}

	public void add(Agent a, Product p) throws Exception {
		a.addProduct(p);
		add(new Inventory(a, p));
	}

	/**
	 * Return price of a product
	 * 
	 * @param seller
	 * @param product
	 * @return
	 */
	public double getPrice(Seller seller, Product product) {
		try {
			int index = owners.get(seller).lastIndexOf(product.getName());
			return ((Inventory) owners.get(seller.getName()).get(index)).getPrice();
		} catch (Exception e) {
			return 0;
		}
	}

	public ArrayList<Inventory> getProductsBySellerName(String sellerName) {
		return owners.get(sellerName);
	}

	public ArrayList<Inventory> getSellersByProductName(String prodName) {
		return stores.get(prodName);
	}
	
	public double getPrice(String sellerName, String prodName) {
		try {
			return getInventory(sellerName, prodName).getPrice();
		} catch(NullPointerException e) {
			logger.error("Seller "+sellerName+" does not have product: "+prodName);
			return 0;
		}
	}
	
	public Inventory getInventory(String sellerName, String prodName) {
		for(Inventory inventory: owners.get(sellerName)) {
			if(prodName.equalsIgnoreCase(inventory.getProdName()))
				return inventory;
		}
		return null;
	}	
}
