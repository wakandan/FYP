/**
 *
 */
package configbase;

import java.io.IOException;

import modelbase.Entity;

/**
 * @author akai
 * 
 */
public class DistributionConfig extends Config {
	double	mean;
	double	variance;

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
	 * @see configbase.Config#readConfig(java.lang.String)
	 */
	@Override
	public void readConfig(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

}
