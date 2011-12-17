package productbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import modelbase.Entity;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author akai
 * @todo: - Add database support
 */
public class ProductManager extends EntityManager {
	/*
	 * Current max index of product list array Terms: - Product: Type of
	 * tradable entity - Item: One among several entity for a type of product
	 */
	int							totalQuantity;
	HashMap<Integer, ArrayList>	categoryList;
	HashMap<String, Double>		value;
	HashMap<String, String>		availableProducts;	/*
													 * List of products with
													 * non-zero quantity
													 */

	public ProductManager() {
		super();
		totalQuantity = 0;
		categoryList = new HashMap<Integer, ArrayList>();
		availableProducts = new HashMap<String, String>();
	}

	/* Add new products to the database */
	@Override
	public void add(Entity entity) throws Exception {
		super.add(entity);
		Product prod = (Product) entity;

		SQLiteStatement st = db
				.prepare("INSERT INTO products(sessionID, name, priceMin, priceMax, quantity, category)"
						+"VALUES (?, ?, ?, ?, ?, ?)");
		st.bind(1, sessionId).bind(2, prod.getName()).bind(3, prod.getPriceMin())
				.bind(4, prod.getPriceMin()).bind(5, prod.getQuantity())
				.bind(6, prod.getCategory());
		st.step();
		totalQuantity += prod.quantity;
		if (!categoryList.containsKey(prod.getCategory())) {
			categoryList.put(prod.getCategory(), new ArrayList<Product>());
		}
		categoryList.get(prod.getCategory()).add(prod);
		if (availableProducts.get(entity.getName())==null) {
			availableProducts.put(entity.getName(), " ");
		}
	}

	public HashMap<Integer, ArrayList> getCategoryList() {
		return categoryList;
	}

	/* Total number of items generated */
	public int getTotalQuantity() {
		return totalQuantity;
	}

	public int getNumCategories() {
		return categoryList.size();
	}

	/* Return all products in one categories */
	public ArrayList getCategory(int i) {
		if (categoryList.containsKey(i)) {
			return categoryList.get(i);
		}
		return null;
	}

	/**
	 * Remove an entity from entity list
	 * 
	 * @param i
	 */
	public void remove(String key) {
		entities.remove(key);
	}

	/*
	 * Get quantity of a product by its name
	 * 
	 * @return: product's quantity on successs, -1 on failure
	 */
	public int getQuantityByName(String prodName) {
		try {
			st = db.prepare("SELECT quantity FROM Products WHERE name=?");
			st.bind(1, prodName);
			if (st.step())
				return st.columnInt(0);
			else
				return -1;
		} catch (SQLiteException e) {
			logger.error("No such product name "+prodName);
			return -1;
		}
	}

	/* Get a list of product names with non-zero quantity */
	public Set<String> getAvailableProducts() {
		return availableProducts.keySet();
	}

	/* Update a product quantity */
	public void update(Product prod) {
		entities.put(prod.getName(), prod);
		if (prod.quantity==0) {
			availableProducts.remove(prod.getName());
		}
	}

	/**
	 * Generate a report of current product's quantities
	 */
	public void reportQuantity() {
		logger.debug("*** Product quantity report ***");
		for(String prodName: entities.keySet()) {
			Product prod = (Product)entities.get(prodName);
			logger.debug(String.format("Product: %-3s, quantity: %-5d", prod.getName(), prod.getQuantity()));			
		}
		logger.debug("*** *** ***");
	}

}
