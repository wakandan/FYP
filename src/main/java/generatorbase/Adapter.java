package generatorbase;

public class Adapter extends Distribution {
	private BetaDistribution beta;
	
	public Adapter(double mean, double variance) {
//		super(mean, variance);
		beta = new BetaDistribution(mean, variance);
	}

	@Override
	public double pdf(double x) {
		double result = beta.pdf(x);
		return result;
	}

	@Override
	public double cdf_range(double x, double y) {
		double result = beta.cdf_range(x,y);
		return result;
	}

	@Override
	public double cdf(double x) {
		double result = beta.cdf(x);
		return result;
	}
	
	

}
