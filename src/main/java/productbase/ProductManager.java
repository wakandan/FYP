package productbase;

import generatorbase.EntityManager;

import java.io.File;

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

	public ProductManager() throws SQLiteException {
		super();
		logger.debug("Initiating new product base with sessionId: "+sessionId);
		totalQuantity = 0;
	}
	
	/* Add new products to the database */
	@Override
	public void add(Entity entity) throws Exception{
		super.add(entity);
		Product prod = (Product)entity;
		
		SQLiteStatement st = db.prepare(""+"INSERT INTO products(sessionID, name, priceMin, priceMax, quantity)  "+"VALUES (?, ?, ?, ?, ?)");
		st.bind(1, sessionId).bind(2, prod.getName()).bind(3, prod.getPriceMin()).bind(4, prod.getPriceMin()).bind(5, prod.getQuantity());
		st.step();
		totalQuantity += prod.quantity;
	}

	/* Total number of items generated */
	public int getTotalQuantity() {
		return totalQuantity;
	}
}
