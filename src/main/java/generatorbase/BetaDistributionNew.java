package generatorbase;

// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 12/26/2007 5:21:59 AM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   BetaDistribution.java

// Referenced classes of package basic:
//            Distribution

public class BetaDistributionNew extends DistributionNew {

	double	a;
	double	b;
	double	c;

	public BetaDistributionNew(double a, double b) {
		setParameters(a, b);
	}

	public BetaDistributionNew() {
		this(1.0D, 1.0D);
	}

	public void setParameters(double a, double b) {
		this.a = a;
		this.b = b;
		c = DistributionNew.logGamma(a + b) - DistributionNew.logGamma(a)
				- DistributionNew.logGamma(b);
		double lower;
		if (a < (double) 1) {
			lower = 0.001D;
		} else {
			lower = 0.0D;
		}
		double upper;
		if (b < (double) 1) {
			upper = 0.999D;
		} else {
			upper = 1.0D;
		}
		double step = 0.1D;
		super.setParameters(0.0D, 1.0D, step, 1);
		setBounds(lower, upper);
	}

	public double density(double x) {
		if ((x < (double) 0) | (x > (double) 1)) {
			return 0.0D;
		}
		if ((x == (double) 0) & (a == (double) 1)) {
			return b;
		}
		if ((x == (double) 0) & (a < (double) 1)) {
			return (1.0D / 0.0D);
		}
		if ((x == (double) 0) & (a > (double) 1)) {
			return 0.0D;
		}
		if ((x == (double) 1) & (b == (double) 1)) {
			return a;
		}
		if ((x == (double) 1) & (b < (double) 1)) {
			return (1.0D / 0.0D);
		}
		if ((x == (double) 1) & (b > (double) 1)) {
			return 0.0D;
		} else {
			return Math.exp(c + (a - (double) 1) * Math.log(x) + (b - (double) 1)
					* Math.log((double) 1 - x));
		}
	}

	public double maxDensity() {
		double mode;
		if (a < (double) 1) {
			mode = 0.001D;
		} else if (b <= (double) 1) {
			mode = 0.999D;
		} else {
			mode = (a - (double) 1) / ((a + b) - (double) 2);
		}
		return density(mode);
	}

	public double mean() {
		return a / (a + b);
	}

	public double variance() {
		return (a * b) / ((a + b) * (a + b) * (a + b + (double) 1));
	}

	public double CDF(double x) {
		return DistributionNew.betaCDF(x, a, b);
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public String name() {
		return "Beta Distribution";
	}

	/*
	 * public double getQuantileOf(double x) { double sum = 0; double step =
	 * 0.002; for (double start = 0; start <= x; start += step) { double mid, w;
	 * if (1 - start > step) { w = step; } else { w = (1 - start); } mid = start
	 * + (w / 2); double density = density(mid); sum += density * w; } return
	 * sum; }
	 */

	public double getProbabilityOfQuantile(double q) {
		if (q == 0)
			return 0;
		else if (q == 1)
			return 1;
		else
			return BinarySearch(q, 0, 1);
	}

	public double BinarySearch(double q, double low, double high) {
		if (high < low)
			return -1; // not found
		double eps = 0.001;
		double mid = (low + high) / 2;
		if (high - low <= eps)
			return mid;
		double midVal = CDF(mid);
		if (java.lang.Math.abs(midVal - q) <= eps) {
			return mid;
		} else if (midVal > q) {
			return BinarySearch(q, low, mid);
		} else {
			return BinarySearch(q, mid, high);
		}
	}
}
