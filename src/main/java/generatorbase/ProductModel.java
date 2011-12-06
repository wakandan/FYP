package generatorbase;

import java.util.Random;

import org.apache.log4j.Logger;

import com.almworks.sqlite4java.SQLiteException;

import configbase.Config;
import configbase.ProductConfig;
import productbase.Product;
import productbase.ProductManager;

public class ProductModel extends EntityModel {
	/*
	 * Generate products based on the provided config object. How it works? -
	 * Divide the distribution by the number of product types, work the total
	 * number of items in this price range. Each range will correspond to a 
	 * product. The total area under the distribution curve in the range is
	 * the product quantity. 
	 */

	
	int						prodPrcRanges[];		/* Store the total number of items in each price ranges */
	ProductConfig			prodcf;	
	boolean					prodPrcRangeSet[];		/* Check if total number of items in each range was calculated */
	int						numProdPrcRange;
	int						prodPrcPeriod;
	private static Logger	logger	= Logger.getLogger("ProductModel");

	public ProductModel(Config config) {
		super();
		prodcf = ((ProductConfig)config);		
		this.prodPrcRanges = new int[prodcf.getNumTypes()];
		this.prodPrcRangeSet = new boolean[prodcf.getNumTypes()];
		for (int i = 0; i<prodcf.getNumTypes(); i++)
			this.prodPrcRangeSet[i] = false;
		prodPrcPeriod = (int) ((prodcf.getPriceMax()-prodcf.getPriceMin())*1.0/prodcf.getNumTypes());
	}

	/* Given the price range, return the total number of items in that range */
	public int getNumProdInRange(double minPrice, double maxPrice) {
		return (int) Math.round(prodcf.getNumProducts()*prodcf.getDistribution().cdf_range(minPrice, maxPrice));
	}

	/*
	 * Get or calculate total number of products in the noRange_th range number.
	 * If the number is already saved in previous calculations, use it.
	 * Otherwise calculate that number and then return
	 */
	public int getNumProdInRange(int noRange) {
		if (prodPrcRangeSet[noRange])
			return prodPrcRanges[noRange];
		else {
			int tmp = getNumProdInRange(getMinPrice(noRange), getMaxPrice(noRange));
			prodPrcRanges[noRange] = tmp;
			prodPrcRangeSet[noRange] = true;
			return tmp;
		}
	}

	/* Get min price of items in the rangeNum_th range */
	public double getMinPrice(int rangeNum) {
		return prodcf.getPriceMin()+rangeNum*prodPrcPeriod;
	}

	/* Get max price of items in the rangeNum_th range */
	public double getMaxPrice(int rangeNum) {
		return prodcf.getPriceMin()+(rangeNum+1)*prodPrcPeriod;
	}

	/* (non-Javadoc)
	 * @see generatorbase.EntityModel#generate(generatorbase.EntityManager)
	 */
	@Override
	public void generate(EntityManager manager) throws Exception {
		double prcMin;
		double prcMax;
		int prodQuantity;
		logger.info("Start generating Products");
		
		Random rand = new Random();
		manager.setConfig(prodcf);		
		manager.beginTransaction();
		for (int i = 0; i<prodcf.getNumTypes(); i++) {
			/* Divide the price range */
			prcMin = getMinPrice(i);
			prcMax = getMaxPrice(i);

			/* Work out total number of products in range */
			prodQuantity = getNumProdInRange(i);
			if (prodQuantity<1)
				continue;
			logger.debug("Generating "+prodQuantity+" products in range "+prcMin+" to "+prcMax);
			Product prod = new Product(i+"");
			prod.setPriceMin(prcMin);
			prod.setPriceMax(prcMax);
			prod.setQuantity(prodQuantity);
			
			((ProductManager)manager).add(prod);
		}
		manager.commitTransaction();
		logger.info("Done generating Products");
		logger.info("No. items generated: "+((ProductManager)manager).getTotalQuantity());
	}

}
