/*
 * @author: akai
 * @note:
 * 	- Parameters used in this class are mean & VARIANCE. If user wants to use
 * 	standard deviation as their parameter, notice them to square that no.*/
package generatorbase;

import configbase.Config;
import configbase.DistributionConfig;

public abstract class Distribution {
	DistributionConfig	config;

	public Distribution(Config config) {
		this.config = (DistributionConfig) config;
	}

	public double getMean() {
		return config.getMean();
	}

	public void setMean(double mean) {
		config.setMean(mean);
	}

	public double getVar() {
		return config.getVariance();
	}

	public void setVar(double variance) {
		config.setVariance(variance);
	}

	public abstract double pdf(double x);

	public abstract double cdf_range(double x, double y);

	public abstract double cdf(double x);
}
