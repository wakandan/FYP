package configbase;

import java.io.IOException;
import java.util.HashMap;

import modelbase.Entity;
import generatorbase.Distribution;

public class ProductConfig extends Config {
	int				numTypes;
	int				numProducts;
	int				priceMin;
	int				priceMax;
	int				numCategories;
	int				maxItemNum;
	/* Distribution for products range <-> price range */
	Distribution	distribution;

	public int getNumCategories() {
		return numCategories;
	}

	public void setNumCategories(int numCategories) {
		this.numCategories = numCategories;
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

	public int getNumTypes() {
		return numTypes;
	}

	public void setNumTypes(int numTypes) {
		this.numTypes = numTypes;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity)
	 */
	@Override
	public void configure(Entity e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return
	 */
	public int getMaxItemNum() {
		// TODO Auto-generated method stub
		return maxItemNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#readConfig(java.lang.String)
	 */
	@Override
	public void readConfig(String filename) throws IOException {
		HashMap<String, String> configFile = Config.readConfigFile(filename);
		String value;
		for(String key:configFile.keySet()) {
			value = configFile.get(key.trim());
			if(key.equalsIgnoreCase("numProducts")) 
				this.numProducts = Integer.parseInt(value);
			else if(key.equalsIgnoreCase("priceMin"))
				this.priceMin= Integer.parseInt(value);
			else if(key.equalsIgnoreCase("priceMax"))
				this.priceMax= Integer.parseInt(value);
			else if(key.equalsIgnoreCase("numTypes"))
				this.numTypes= Integer.parseInt(value);
		}
		
	}
}
