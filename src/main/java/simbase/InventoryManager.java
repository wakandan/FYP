/**
 *
 */
package simbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import productbase.Product;

import agentbase.Agent;
import agentbase.Seller;

import com.almworks.sqlite4java.SQLiteException;
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
													 * Hashmap for Seller's name
													 * -> Inventories
													 */
	HashMap<String, ArrayList<Inventory>>	stores; /*
													 * Hashmap for Inventory's
													 * name -> Seller's names
													 */

	public InventoryManager() {
		super();
		owners = new HashMap<String, ArrayList<Inventory>>();
		stores = new HashMap<String, ArrayList<Inventory>>();
	}

	public void add(Entity e) throws Exception {
		Inventory inventory = (Inventory) e;
		SQLiteStatement st;
		String sql;
		super.add(e);
		try {
			sql = "INSERT INTO Inventories(agent_name, prod_name, quantity, price, value) VALUES(?, ?, ?, ?, ?)";
			st = db.prepare(sql);
			st.bind(1, inventory.getAgent().getName()).bind(2, inventory.getProd().getName())
					.bind(3, inventory.quantity).bind(4, inventory.price).bind(5, inventory.value);
			st.step();
		} catch (SQLiteException exception) {
			sql = "UPDATE Inventories SET quantity=quantity+? WHERE agent_name=? AND prod_name=?";
			st = db.prepare(sql);
			st.bind(1, inventory.quantity).bind(2, inventory.agent.getName())
					.bind(3, inventory.prod.getName());
			st.step();
		}

		if (!owners.containsKey(inventory.getAgent().getName())) {
			owners.put(inventory.getAgent().getName(), new ArrayList<Inventory>());
		}
		owners.get(inventory.getAgent().getName()).add(inventory);

		if (inventory.agent instanceof Seller) {
			if (!stores.containsKey(inventory.getProd().getName())) {
				stores.put(inventory.getProd().getName(), new ArrayList<Inventory>());
			}
			stores.get(inventory.getProd().getName()).add(inventory);
		}

	}

	public Inventory add(Agent a, Product p) throws Exception {
		Inventory inventory = new Inventory(a, p);
		a.addProduct(p);
		add(inventory);
		return inventory;
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
		} catch (NullPointerException e) {
			logger.error("Seller "+sellerName+" does not have product: "+prodName);
			return 0;
		}
	}

	public Inventory getInventory(Seller seller, Product prod) {
		return getInventory(seller.getName(), prod.getName());
	}

	public Inventory getInventory(String sellerName, String prodName) {
		for (Inventory inventory : owners.get(sellerName)) {
			if (prodName.equalsIgnoreCase(inventory.getProd().getName()))
				return inventory;
		}
		return null;
	}

	/**
	 * @param seller
	 * @param prod
	 * @return
	 */
	public double getValue(Seller seller, Product prod) {
		try {
			return getInventory(seller, prod).getValue();
		} catch (Exception e) {
			logger.error("Unable to get value for product "+prod);
			return 0;
		}
	}

	public Object[] getAllProductsNames() {
		return this.stores.keySet().toArray();
	}

	public int getAllProductsCount() {
		return this.stores.size();
	}
}
