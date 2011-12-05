package configbase;

import generatorbase.Distribution;

public class ProductConfig extends Config {
	int				numRanges;
	int				numProducts;
	int				priceMin;
	int				priceMax;
	Distribution	distribution;
	
	public int getNumRanges() {
		return numRanges;
	}
	public int getNumProducts() {
		return numProducts;
	}
	public int getPriceMin() {
		return priceMin;
	}
	public int getPriceMax() {
		return priceMax;
	}
	public Distribution getDistribution() {
		return distribution;
	}
}
