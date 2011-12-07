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
public class TestProductModel extends TestWithDBParent{	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		SQLiteStatement st;
		st = db.prepare("" +
				"CREATE TABLE Products(sessionId TEXT, " +
				"						quantity NUMERIC, " +
				"						name TEXT, " +
				"						priceMin NUMERIC," +
				"						priceMax NUMERIC)");
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
		int numTypes = 50;
		int numProds = 10000; 
		int prcMax = 1000;
		int prcMin = 1;
		int prcMean = 500;
		int prcDeviation = 300*300;
		ProductConfig config = new ProductConfig();
		config.setDistribution(new NormalDistribution(prcMean, prcDeviation));
		config.setNumProducts(numProds);
		config.setNumTypes(numTypes);
		config.setPriceMax(prcMax);
		config.setPriceMin(prcMin);
		ProductModel prodModel = new ProductModel(config);
		try {
			ProductManager prodManager = new ProductManager();
			prodManager.setDb(this.db);
			prodManager.setConfig(config);			
			prodModel.generate(prodManager);
			SQLiteStatement st = db.prepare("SELECT AVG(quantity) FROM products");
			st.step();
			assertTrue(st.columnDouble(0)>0);
			st = db.prepare("SELECT COUNT(*) FROM products");
			st.step();
			assertTrue(st.columnDouble(0)>0);
			st = db.prepare("SELECT SUM(quantity) FROM products");
			st.step();			
			assertTrue(Math.abs(10000-st.columnDouble(0))<=2000);
			assertEquals(numTypes, prodManager.getSize());
			st = db.prepare("SELECT AVG(priceMin) FROM products");
			st.step();			
			assertTrue(Math.abs(10000-st.columnDouble(0))>0);
			
		} catch (SQLiteException e) {
			System.out.println(e);
		}				
		/* Test with a few pre-calculated values */ catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
