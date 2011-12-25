package generatorbase;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov Date: 12/26/2007
// 5:23:06 AM
// Home Page : http://members.fortunecity.com/neshkov/dj.html - Check often for
// new version!
// Decompiler options: packimports(3)
// Source File Name: Distribution.java

public abstract class DistributionNew {

	public DistributionNew() {}

	public abstract double density(double d);

	public void setParameters(double minValue, double maxValue, double stepSize, int type) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.stepSize = stepSize;
		this.type = type;
		smallStep = stepSize/(double) 10;
		setBounds(minValue, maxValue);
	}

	public void setBounds(double lower, double upper) {
		lowerValue = lower;
		upperValue = upper;
	}

	public double maxValue() {
		return maxValue;
	}

	public double minValue() {
		return minValue;
	}

	public double stepSize() {
		return stepSize;
	}

	public int type() {
		return type;
	}

	public double lowerValue() {
		return lowerValue;
	}

	public double upperValue() {
		return upperValue;
	}

	public double mean() {
		double sum = 0.0D;
		switch (type) {
		default:
			break;

		case 0: // '\0'
			for (double x = lowerValue; x<upperValue+0.5D*stepSize; x += stepSize)
				sum += x*density(x);

			break;

		case 1: // '\001'
			for (double x = lowerValue; x<upperValue; x += smallStep) {
				double y = x+smallStep;
				sum += (smallStep*(x*density(x)+y*density(y)))/(double) 2;
			}

			break;
		}
		return sum;
	}

	public double variance() {
		double mu = mean();
		double sum = 0.0D;
		switch (type) {
		default:
			break;

		case 0: // '\0'
			for (double x = lowerValue; x<upperValue+0.5D*stepSize; x += stepSize)
				sum += (x-mu)*(x-mu)*density(x);

			break;

		case 1: // '\001'
			for (double x = lowerValue; x<upperValue; x += smallStep) {
				double y = x+smallStep;
				sum += (smallStep*((x-mu)*(x-mu)*density(x)+(y-mu)*(y-mu)*density(y)))/(double) 2;
			}

			break;
		}
		return sum;
	}

	public double stdDev() {
		return Math.sqrt(variance());
	}

	public double quantile(double p) {
		double xAcc = 0.0001D;
		int jMax = 40;
		if (type==0) {
			double x = lowerValue;
			for (double cumProb = density(x); (cumProb<p)&(x<upperValue+0.5D*stepSize); cumProb += density(x))
				x += stepSize;

			return x;
		}
		if (p<=(double) 0)
			return lowerValue;
		if (p>=(double) 1)
			return upperValue;
		double a = lowerValue;
		double b = upperValue;
		double xMid = 0.5D*(a+b);
		int j = 0;
		do {
			if (j>=jMax)
				break;
			xMid = 0.5D*(a+b);
			double dx = b-a;
			double fMid = CDF(xMid);
			if (fMid<=p)
				a = xMid;
			else
				b = xMid;
			if ((Math.abs(dx)<xAcc)|(fMid==p))
				break;
			j++;
		} while (true);
		return xMid;
	}

	public double simulate() {
		return quantile(Math.random());
	}

	public double maxDensity() {
		double max = density(lowerValue);
		for (double x = lowerValue; x<upperValue+0.5D*stepSize; x += stepSize) {
			double d = density(x);
			if (d>max)
				max = d;
		}

		return max;
	}

	public double median() {
		return quantile(0.5D);
	}

	public double CDF(double x) {
		double cdf = 0.0D;
		if (x>upperValue)
			cdf = 1.0D;
		else
			switch (type) {
			default:
				break;

			case 0: // '\0'
				for (double y = lowerValue; y<x+0.5D*stepSize; y += stepSize)
					cdf += density(y);

				break;

			case 1: // '\001'
				double initial;
				if (minValue>(-1.0D/0.0D))
					initial = density(lowerValue)*smallStep;
				else
					initial = 0.0D;
				cdf = initial;
				for (double y = lowerValue; y<x; y += smallStep) {
					double z = y+smallStep;
					cdf += (smallStep*(density(y)+density(z)))/(double) 2;
				}

				break;
			}
		return cdf;
	}

	public double failureRate(double x) {
		return density(x)/((double) 1-CDF(x));
	}

	public String name() {
		return "Distribution";
	}

	public static double perm(double n, double k) {
		k = Math.rint(k);
		if ((k>n)|(k<(double) 0))
			return 0.0D;
		double prod = 1.0D;
		for (int i = 1; (double) i<=k; i++)
			prod *= (n-(double) i)+(double) 1;

		return prod;
	}

	public static double factorial(double n) {
		n = Math.rint(n);
		return perm(n, n);
	}

	public static double binCoef(double n, double k) {
		n = Math.rint(n);
		k = Math.rint(k);
		if (k>n/(double) 2)
			k = n-k;
		return perm(n, k)/factorial(k);
	}

	public static double logGamma(double x) {
		double coef[] = { 76.180091730000001D, -86.505320330000004D, 24.014098220000001D,
				-1.231739516D, 0.00120858003D, -5.3638199999999999E-006D };
		double step = 2.5066282746500002D;
		double fpf = 5.5D;
		double t = x-(double) 1;
		double tmp = t+fpf;
		tmp = (t+0.5D)*Math.log(tmp)-tmp;
		double ser = 1.0D;
		for (int i = 1; i<=6; i++) {
			t += 1;
			ser += coef[i-1]/t;
		}

		return tmp+Math.log(step*ser);
	}

	public static double gamma(double x) {
		return Math.exp(logGamma(x));
	}

	public static double gammaCDF(double x, double a) {
		if (x<=(double) 0)
			return 0.0D;
		if (x<a+(double) 1)
			return gammaSeries(x, a);
		else
			return (double) 1-gammaCF(x, a);
	}

	public static double gammaSeries(double x, double a) {
		int maxit = 100;
		double eps = 2.9999999999999999E-007D;
		double sum = 1.0D/a;
		double ap = a;
		double gln = logGamma(a);
		double del = sum;
		int n = 1;
		do {
			if (n>maxit)
				break;
			ap++;
			del = (del*x)/ap;
			sum += del;
			if (Math.abs(del)<Math.abs(sum)*eps)
				break;
			n++;
		} while (true);
		return sum*Math.exp((-x+a*Math.log(x))-gln);
	}

	public static double gammaCF(double x, double a) {
		int maxit = 100;
		double eps = 2.9999999999999999E-007D;
		double gln = logGamma(a);
		double g = 0.0D;
		double gOld = 0.0D;
		double a0 = 1.0D;
		double a1 = x;
		double b0 = 0.0D;
		double b1 = 1.0D;
		double fac = 1.0D;
		for (int n = 1; n<=maxit; n++) {
			double an = 1.0D*(double) n;
			double ana = an-a;
			a0 = (a1+a0*ana)*fac;
			b0 = (b1+b0*ana)*fac;
			double anf = an*fac;
			a1 = x*a0+anf*a1;
			b1 = x*b0+anf*b1;
			if (a1==(double) 0)
				continue;
			fac = 1.0D/a1;
			g = b1*fac;
			if (Math.abs((g-gOld)/g)<eps)
				break;
			gOld = g;
		}

		return Math.exp((-x+a*Math.log(x))-gln)*g;
	}

	public static double betaCDF(double x, double a, double b) {
		double bt;
		if ((x==(double) 0)|(x==(double) 1))
			bt = 0.0D;
		else
			bt = Math.exp((logGamma(a+b)-logGamma(a)-logGamma(b))+a*Math.log(x)+b
					*Math.log((double) 1-x));
		if (x<(a+(double) 1)/(a+b+(double) 2))
			return (bt*betaCF(x, a, b))/a;
		else
			return (double) 1-(bt*betaCF((double) 1-x, b, a))/b;
	}

	public static double betaCF(double x, double a, double b) {
		int maxit = 100;
		double eps = 2.9999999999999999E-007D;
		double am = 1.0D;
		double bm = 1.0D;
		double az = 1.0D;
		double qab = a+b;
		double qap = a+(double) 1;
		double qam = a-(double) 1;
		double bz = (double) 1-(qab*x)/qap;
		int m = 1;
		do {
			if (m>maxit)
				break;
			double em = m;
			double tem = em+em;
			double d = (em*(b-(double) m)*x)/((qam+tem)*(a+tem));
			double ap = az+d*am;
			double bp = bz+d*bm;
			d = (-(a+em)*(qab+em)*x)/((a+tem)*(qap+tem));
			double app = ap+d*az;
			double bpp = bp+d*bz;
			double aOld = az;
			am = ap/bpp;
			bm = bp/bpp;
			az = app/bpp;
			bz = 1.0D;
			if (Math.abs(az-aOld)<eps*Math.abs(az))
				break;
			m++;
		} while (true);
		return az;
	}

	public static final int	DISCRETE	= 0;
	public static final int	CONTINUOUS	= 1;
	int						type;
	double					minValue;
	double					maxValue;
	double					stepSize;
	double					smallStep;
	double					lowerValue;
	double					upperValue;
}
