package productbase;

import configbase.ProductConfig;

/**
 * @author akai
 */
public class ProductManager {
	/* Current max index of product list array */
	int				index;
	int				maxNum;
	Product			products[];
	ProductConfig	prodConfig;

	/* Store the total number of items in each price ranges */
	int				prodPrcRanges[];

	/* Check if total number of items in each range was calculated */
	boolean			prodPrcRangeSet[];
	int				numProdPrcRange;
	int				prodPrcPeriod;
	int				prodPrcRangesOffset	= 10;

	public ProductConfig getProdConfig() {
		return prodConfig;
	}

	public void setProdConfig(ProductConfig prodConfig) {
		this.products = new Product[prodConfig.getNumProducts()+prodPrcRangesOffset];
		this.index = -1;
		this.prodConfig = prodConfig;
		this.prodPrcRanges = new int[prodConfig.getNumRanges()+prodPrcRangesOffset];
		this.prodPrcRangeSet = new boolean[prodConfig.getNumRanges()];
		for (int i = 0; i<prodConfig.getNumRanges(); i++)
			this.prodPrcRangeSet[i] = false;
		prodPrcPeriod = (int) ((prodConfig.getPriceMax()-prodConfig.getPriceMin())*1.0/prodConfig.getNumRanges());
	}

	/* Given the price range, return the total number of items in that range */
	public int getNumProdInRange(double minPrice, double maxPrice) {
		return (int) Math.round(prodConfig.getNumProducts()*prodConfig.getDistribution().cdf_range(minPrice, maxPrice));
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
		return prodConfig.getPriceMin()+rangeNum*prodPrcPeriod;
	}

	/* Get max price of items in the rangeNum_th range */
	public double getMaxPrice(int rangeNum) {
		return prodConfig.getPriceMin()+(rangeNum+1)*prodPrcPeriod;
	}

	public int[] getProdPrcRanges() {
		return prodPrcRanges;
	}

	public void setProdPrcRanges(int[] prodPrcRanges) {
		this.prodPrcRanges = prodPrcRanges;
	}

	/*
	 * Add new product to product list. If there are more than max # of products
	 * specified, then just increase the capacity
	 */
	public void add(Product prod) {
		if (index+1>prodConfig.getNumProducts())
			prodConfig.setNumProducts(index+2);
		products[++index] = prod;
	}

	/* Total number of products generated */
	public int size() {
		return index+1;
	}
}
