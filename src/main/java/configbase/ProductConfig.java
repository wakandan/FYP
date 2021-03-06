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
	int				quantityAssignmentThreadshold;	/*
													 * Quantity threadshold of
													 * an product for not
													 * randomizing when
													 * assigning to agents
													 */
	double			qualityRatio;
	/* Distribution for products range <-> price range */
	Distribution	distribution;

	public int getNumCategories() {
		return numCategories;
	}

	public int getQuantityAssignmentThreadshold() {
		return quantityAssignmentThreadshold;
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
	 * @see configbase.Config#processConfigKey(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean processConfigKey(String key, String value) {
		String configFileNameEntry = "distributionConfigFile";
		String configClassNameEntry="distributionConfigClassName";
		ClassLoader classLoader = Distribution.class.getClassLoader();
		if (key.equalsIgnoreCase("numProducts"))
			this.numProducts = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("priceMin"))
			this.priceMin = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("priceMax"))
			this.priceMax = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("numTypes"))
			this.numTypes = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("numCategories"))
			this.numCategories = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("quantityAssignmentThreadshold"))
			this.quantityAssignmentThreadshold = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("distributionClassName")) {
			try {
				/*Dynamically create a new object from the distribution class*/
				Class<Distribution> distributionClass = (Class<Distribution>) classLoader.loadClass(value);
				logger.debug("Initiating distribution class "+distributionClass.getName());
				this.distribution = distributionClass.newInstance();
				
				/*Dynamically load config data into the new object*/
				String configFilename = getConfigEntry(configFileNameEntry);
				String configClassName = getConfigEntry(configClassNameEntry);
				if (configFilename==null || configClassName==null)
					throw new InstantiationException();				
				Class<DistributionConfig> disConfigClass = (Class<DistributionConfig>) classLoader.loadClass(configClassName);
				logger.debug("Initiating distribution config class "+disConfigClass.getName());
				DistributionConfig configInstance = disConfigClass.newInstance();
				configInstance.readConfig(configFilename);
				this.distribution.setConfig(configInstance);
			} catch (ClassNotFoundException e) {
				logger.error("Class "+value+" cannot be found");
				e.printStackTrace();
			} catch (InstantiationException e) {
				logger.error("Class "+value+" cannot be instantiated. Unable to read config entries/Does your class have a default constructor?");
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("Error loading class: "+value);
				e.printStackTrace();
			}
		} else if (key.equalsIgnoreCase(configFileNameEntry) || 
				key.equalsIgnoreCase(configClassNameEntry)) {

		} else
			return false;
		return true;
	}
}
