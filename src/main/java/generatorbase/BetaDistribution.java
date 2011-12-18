package generatorbase;

import configbase.ProductConfig;

public class BetaDistribution {//extends Distribution{
	private double a;
	private double b;
	private ProductConfig config;
	private int max;
	private int min;
	
	public BetaDistribution(double mean, double variance) {
		//super(mean, variance);
		a = (Math.pow(mean, 2) - Math.pow(mean, 3) - mean * variance) / variance;
		b = (mean - 2*Math.pow(mean, 2) + Math.pow(mean, 3) - variance + mean * variance) / variance;
		max = config.getPriceMax();
		min = config.getPriceMin();
	}
	
	
	public double gamma(double x) {
		double result = 1;
		for (int i = 1; i < x; i++) {
			result = result * i;
		}
		return result;
	}
	
	
	public double pdf(double x) {
		double new_x = (x - min) / (max - min);
		double coefficient = (gamma(a+b)/(gamma(a)*gamma(b)));
		double result = coefficient * Math.pow(new_x, a-1) * Math.pow(new_x-1, b-1);
		return result;
	}

	
	public double cdf_range(double x, double y) {
		return (cdf(y) - cdf(x));
	}

	
	public double cdf(double x) {
		double result = 0;
		double new_x = (x - min) / (max - min);
		for (double j = a; j < (a + b); j++) {
			result = result + ((gamma(a+b) / (gamma(a + b - j) * gamma(j+1))) * Math.pow(new_x, j) * Math.pow(1-new_x, a+b-j-1));  
		}
		double new_result = result * config.getMaxItemNum();
		return new_result;
	}
	
}
