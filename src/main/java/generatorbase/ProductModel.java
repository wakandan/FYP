package generatorbase;

import java.util.Random;

import org.apache.log4j.Logger;
import configbase.Config;
import configbase.ProductConfig;
import productbase.Product;
import productbase.ProductManager;

public class ProductModel extends Model {
	/*
	 * Generate products based on the provided config object. How it works? -
	 * Divide the distribution into a number of equal ranges, work the total
	 * number of items in this price range. Each range will correspond to a
	 * price range. The total area under the distribution curve in the range is
	 * the total of possible products to have this price range - For each of the
	 * product in the price range, randomize the price and create a new product.
	 */

	private static Logger	logger	= Logger.getLogger("ProductModel");

	/*Generate a list of products following the specified distribution*/
	public ProductManager genProducts() {
		ProductConfig cf = (ProductConfig) config;
		double prcMin;
		double prcMax;
		int numProdInRange;
		logger.info("Start generating Products");
		Random rand = new Random();
		ProductManager prodManager = new ProductManager();
		prodManager.setProdConfig(cf);		
		for (int i = 0; i<cf.getNumRanges(); i++) {
			/* Divide the price range */
			prcMin = prodManager.getMinPrice(i);
			prcMax = prodManager.getMaxPrice(i);

			/* Work out total number of products in range */
			numProdInRange = prodManager.getNumProdInRange(i);
			
			logger.debug("Generating "+numProdInRange+" products in range "+prcMin+" to "+prcMax);
			for (int j = 0; j<numProdInRange; j++) {
				Product prod = new Product(i+"_"+j);
				prod.setPrice(prcMin+rand.nextInt((int) ((prcMax-prcMin)*10000))/10000.0);
				prodManager.add(prod);
			}
		}
		logger.info("Done generating Products");
		logger.info("No. products generated: "+prodManager.size());
		return prodManager;
	}

}
