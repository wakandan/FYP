package productbase;

import generatorbase.EntityManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import modelbase.Entity;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.Config;
import configbase.ProductConfig;

/**
 * @author akai
 * @todo: 
 * 	- Add database support
 */
public class ProductManager extends EntityManager {
	/*
	 * Current max index of product list array Terms: - Product: Type of
	 * tradable entity - Item: One among several entity for a type of product
	 */
	int						totalQuantity;
	private static Logger	logger				= Logger.getLogger("ProductManager");
	HashMap<Integer, ArrayList> categoryList;	

	public ProductManager() {
		super();
		totalQuantity = 0;
		categoryList = new HashMap<Integer, ArrayList>();
	}
	
	/* Add new products to the database */
	@Override
	public void add(Entity entity) throws Exception{
		super.add(entity);
		Product prod = (Product)entity;
		
		SQLiteStatement st = db.prepare("INSERT INTO products(sessionID, name, priceMin, priceMax, quantity, category)" +
				"VALUES (?, ?, ?, ?, ?, ?)");
		st.bind(1, sessionId)
			.bind(2, prod.getName())
			.bind(3, prod.getPriceMin())
			.bind(4, prod.getPriceMin())
			.bind(5, prod.getQuantity())
			.bind(6, prod.getCategory());
		st.step();
		totalQuantity += prod.quantity;
		if(!categoryList.containsKey(prod.getCategory())) {
			categoryList.put(prod.getCategory(), new ArrayList<Product>());
		}
		categoryList.get(prod.getCategory()).add(prod);
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
	
	/*Return all products in one categories*/
	public ArrayList getCategory(int i) {
		if(categoryList.containsKey(i)) {
			return categoryList.get(i);
		}
		return null;
	}

	/**
	 * Remove an entity from entity list
	 * @param i
	 */
	public void remove(int i) {
		
		entities.remove(i);
	}

}
