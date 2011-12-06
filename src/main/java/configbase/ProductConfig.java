package configbase;

import generatorbase.Distribution;

public class ProductConfig extends Config {
	int				numRanges;
	int				numProducts;
	int				priceMin;
	int				priceMax;
	Distribution	distribution;

	public void setNumRanges(int numRanges) {
		this.numRanges = numRanges;
	}

	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}

	public void setPriceMin(int priceMin) {
		this.priceMin = priceMin;
	}

	public void setPriceMax(int priceMax) {
		this.priceMax = priceMax;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

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
