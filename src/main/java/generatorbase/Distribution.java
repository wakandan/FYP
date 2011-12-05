package generatorbase;

public abstract class Distribution {
	double mean;
	double var;
	
	public Distribution(double mean, double variance) {
		this.mean = mean;
		this.var = variance;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getVar() {
		return var;
	}
	public void setVar(double variance) {
		this.var = variance;
	}
	public abstract double pdf(double x);
	public abstract double cdf_range(double x, double y);
	public abstract double cdf(double x);
}
