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
import core.TestWithDBParent;

/**
 * @author akai
 * 
 */
public class TestProductModel extends TestWithDBParent {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {}

	@Test
	public void testProductGen() throws Exception {
		int prcMean = 500;
		int prcDeviation = 300*300;
		ProductConfig config = new ProductConfig();
		config.readConfig("src/test/resources/generatorbase/TestProductConfig.ini");
		config.setDistribution(new NormalDistribution(prcMean, prcDeviation));
		ProductModel prodModel = new ProductModel(config);
		ProductManager prodManager = new ProductManager();
		prodManager.setDb(this.db);
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
		assertEquals(config.getNumTypes(), prodManager.getSize());
		st = db.prepare("SELECT AVG(priceMin) FROM products");
		st.step();
		assertTrue(Math.abs(10000-st.columnDouble(0))>0);
		
		/*Test with 50% sure that all the categories will be created*/
		assertTrue(prodManager.getCategoryList().size()>=config.getNumCategories()/2);
		st = db.prepare("SELECT COUNT(category) FROM products");
		st.step();
		assertTrue(st.columnDouble(0)>=config.getNumCategories()/2);
	}

}
