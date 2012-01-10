/**
 *
 */
package configbase;

import java.io.IOException;
import java.util.HashMap;

import modelbase.Entity;

/**
 * @author akai
 * 
 */
public class DistributionConfig extends Config {
	double	mean;
	double	variance;

	public DistributionConfig() {
		super();
	}

	public DistributionConfig(double mean, double variance) {
		super();
		this.mean = mean;
		this.variance = variance;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#processConfigKey(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean processConfigKey(String key, String value) {
		if (key.equalsIgnoreCase("mean"))
			this.mean = Double.parseDouble(value);
		else if (key.equalsIgnoreCase("variance"))
			this.variance = Double.parseDouble(value);
		else
			return false;
		return true;
	}

}
