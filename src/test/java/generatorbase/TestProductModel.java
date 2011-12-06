/**
 *
 */
package generatorbase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import productbase.ProductManager;

import configbase.ProductConfig;

/**
 * @author akai
 * 
 */
public class TestProductModel {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {}

	@Test
	public void testProductGen() {
		ProductConfig config = new ProductConfig();
		config.setDistribution(new NormalDistribution(5000, 800*800));
		/* Has total of 100 Products */
		config.setNumProducts(1000);

		/* Has total of 5 price ranges */
		config.setNumRanges(20);

		/* Config max/min price */
		config.setPriceMax(10000);
		config.setPriceMin(1);
		ProductModel prodModel = new ProductModel();
		prodModel.setConfig(config);
		ProductManager prodManager = new ProductManager();
		prodManager.setProdConfig(config);
		prodManager = prodModel.genProducts();
		assertTrue(Math.abs(1000-prodManager.size())<=10);
		/* Test with a few pre-calculated values */
		assertEquals(74, prodManager.getNumProdInRange(7));
		assertEquals(234, prodManager.getNumProdInRange(10));
	}

}
