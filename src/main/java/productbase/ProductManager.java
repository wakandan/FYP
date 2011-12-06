package productbase;

import java.io.File;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.ProductConfig;

/**
 * @author akai
 * @todo: 
 * 	- Add database support
 */
public class ProductManager {
	/*
	 * Current max index of product list array Terms: - Product: Type of
	 * tradable entity - Item: One among several entity for a type of product
	 */
	int						index;
	int						maxNum;
	Product					products[];
	ProductConfig			prodConfig;
	int						prodPrcRangesOffset	= 10;
	String					sessionId;
	SQLiteConnection		db;
	int						totalQuantity;
	private static Logger	logger				= Logger.getLogger("ProductManager");

	public ProductManager() throws SQLiteException {
		sessionId = (new DateTime()).toString();
		logger.debug("Initiating new product base with sessionId: "+sessionId);
		totalQuantity = 0;
	}

	public SQLiteConnection getDb() {
		return db;
	}

	public void setDb(SQLiteConnection db) {
		this.db = db;
	}

	public ProductConfig getProdConfig() {
		return prodConfig;
	}

	public void setProdConfig(ProductConfig prodConfig) {
		this.products = new Product[prodConfig.getNumProducts()+prodPrcRangesOffset];
		this.index = -1;
		this.prodConfig = prodConfig;
	}

	/* Add new products to the database */
	public void add(Product prod) throws SQLiteException {
		SQLiteStatement st = db.prepare(""+"INSERT INTO products(sessionID, name, price, quantity)  "+"VALUES (?, ?, ?, ?)");
		st.bind(1, sessionId).bind(2, prod.name).bind(3, prod.price).bind(4, prod.quantity);
		st.step();
		if (index+1>prodConfig.getNumProducts())
			prodConfig.setNumProducts(index+2);
		products[++index] = prod;
		totalQuantity += prod.quantity;
	}

	/* Total number of items generated */
	public int getTotalQuantity() {
		return totalQuantity;
	}
}
