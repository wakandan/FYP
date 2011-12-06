/**
 *
 */
package generatorbase;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import productbase.ProductManager;

import configbase.ProductConfig;

/**
 * @author akai
 * 
 */
public class TestProductModel {
	SQLiteConnection db;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		db = new SQLiteConnection();
		db.open(true);
		SQLiteStatement st;
		db.exec("PRAGMA SYNCHRONOUS=OFF");
		st = db.prepare("" +
				"CREATE TABLE Products(sessionId TEXT, " +
				"						quantity NUMERIC, " +
				"						name TEXT, " +
				"						price NUMERIC)");
		st.step();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProductGen() {
		ProductConfig config = new ProductConfig();
		config.setDistribution(new NormalDistribution(500, 300*300));
		config.setNumProducts(10000);
		config.setNumTypes(50);
		config.setPriceMax(1000);
		config.setPriceMin(1);
		ProductModel prodModel = new ProductModel(config);
		try {
			ProductManager prodManager = new ProductManager();
			System.out.println(this.db);
			prodManager.setDb(this.db);
			prodManager.setProdConfig(config);			
			prodModel.genProducts(prodManager);
			SQLiteStatement st = db.prepare("SELECT AVG(quantity) FROM products");
			st.step();
			assertTrue(st.columnDouble(0)>0);
			st = db.prepare("SELECT COUNT(*) FROM products");
			st.step();
			assertTrue(st.columnDouble(0)>0);
			st = db.prepare("SELECT SUM(quantity) FROM products");
			st.step();
			assertTrue(Math.abs(10000-st.columnDouble(0))<=2000);
			
		} catch (SQLiteException e) {
			System.out.println(e);
		}				
		/* Test with a few pre-calculated values */
	}

}
