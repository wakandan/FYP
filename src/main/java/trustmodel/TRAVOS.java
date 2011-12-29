package trustmodel;

import generatorbase.BetaDistribution;
import generatorbase.BetaDistributionNew;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import core.Pair;

import simbase.Rating;
import simbase.RatingManager;

import agentbase.Buyer;

/*
 * This class implements the TRAVOS model's approach for filtering
 * inaccurate reputation ratings. We only implement here a method for
 * estimating the probability that an advisor will provide an accurate
 * opinion given its past opinions and later observed interactions with the
 * trustees for which opinions were given
 */
public class TRAVOS {
	int				numBins;

	RatingManager	ratingManager;

	double			errorThreshold;
	double			minAccuracyValue;

	// sid is the seller that the advisor provides ratings for
	// bid is the id of the buyer that estimates the trustworthiness of the
	// advisor aid is the id of the advisor

	public void setRatingManager(RatingManager ratingManager) {
		this.ratingManager = ratingManager;
	}

	public int getNumBins() {
		return numBins;
	}

	public void setNumBins(int numBins) {
		this.numBins = numBins;
	}

	/* Evaluate an advisor's accuracy */
	private Pair<Double, Double> getAdvises(String buyerName, String sellerName, String advisorName) {

		/*
		 * find the rating distribution of advisor -> seller
		 */
		int positive = 0;
		int negative = 0;

		ArrayList<Rating> ratings = ratingManager.getRating(advisorName, sellerName);

		for (Rating rating : ratings) {
			if (rating.getRating() >= 3)
				positive++;
			else
				negative++;
		}

		/*
		 * calculate the expected reputation of the seller based on the ratings
		 * of the advisor the bin that the expected reputation falls into
		 */
		BetaDistributionNew erAdvisorDis = new BetaDistributionNew(positive + 1, negative + 1);
		// double er_advisor = 1.0 * (positive + 1) / (positive + negative + 2);
		double er_advisor = erAdvisorDis.mean();
		double lb = 0;
		double ub = 0;

		for (int i = 0; i < numBins; i++) {
			lb = i * 1.0 / numBins;
			ub = (i + 1) * 1.0 / numBins;
			if (lb <= er_advisor && ub >= er_advisor)
				break;
		}

		int goodOutcome = 0;
		int badOutcome = 0;

		/* Get common ratees between B & A */
		ArrayList<String> commonRatees = ratingManager.findCommonRatees(buyerName, advisorName);
		for (String rateeName : commonRatees) {
			/*
			 * For each ratee, get past transactions between B & ratee,
			 * chronological order
			 */
			ArrayList<Rating> pastRatings = ratingManager.getRating(buyerName, rateeName);

			/*
			 * For each past rating, check its outcome with provided opinions at
			 * the time
			 */
			for (Rating rating : pastRatings) {
				ArrayList<Rating> pastAdvisedRatings = ratingManager.getRatingsBefore(advisorName,
						rateeName, rating.getStime());

				/* No past opinion provided at the time, continue */
				if (pastAdvisedRatings == null)
					continue;

				/*
				 * Update frequency parameters using the outcomes, if the
				 * opinion provided is similar to what is provided now (in a
				 * same bin)
				 */
				if (isBounded(pastAdvisedRatings, lb, ub)) {
					if (rating.getRating() >= 3)
						goodOutcome++;
					else
						badOutcome++;
				}
			}
		}

		/* Calc advisor's accuracy for his opinions */
		BetaDistributionNew dis = new BetaDistributionNew(goodOutcome + 1, badOutcome + 1);
		double advisorAccuracy = dis.CDF(ub) - dis.CDF(lb);

		/*
		 * Based on the calculated accuracy, estimate the true parameters for
		 * the provided opinions
		 */
		BetaDistributionNew uniformDis = new BetaDistributionNew(1, 1);
		double trueExpectedMean = uniformDis.mean() + advisorAccuracy
				* (dis.mean() - uniformDis.mean());
		double trueStandardDeviation = uniformDis.stdDev() + advisorAccuracy
				* (dis.stdDev() - uniformDis.stdDev());
		double m = trueExpectedMean;
		double d = trueStandardDeviation;
		double m1 = 1 - m;

		/**/
		double adjustedSuccessOutcome = (m * m - m * m * m) / (d * d) - m - 1;
		double adjustedFailureOutcome = (m1 * m1 - m1 * m1 * m1) / (d * d) - m1 - 1;
		return new Pair<Double, Double>(adjustedSuccessOutcome, adjustedFailureOutcome);
	}

	/*
	 * Check if a distribution's expected value is bounded by provided lower
	 * bound and upper bound
	 */
	private boolean isBounded(ArrayList<Rating> ratings, double lowerBound, double upperBound) {
		int positive = 0;
		int negative = 0;
		for (Rating rating : ratings) {
			if (rating.getRating() >= 3)
				positive++;
			else
				negative++;
		}

		double expectedValue = 1.0 * (positive + 1) / (positive + negative + 2);
		return expectedValue >= lowerBound && expectedValue <= upperBound;
	}

	/*
	 * Using TRAVOS model to calculate a trust level between buyer & seller.
	 * First trust value is calculated based on his own experience first. If the
	 * confidence level is not very high, adviors who bought from the same
	 * seller before will be consulted
	 */
	public double travos(String buyerName, String sellerName) {
		ArrayList<Rating> ratings = ratingManager.getRating(buyerName, sellerName);
		double trustValue = 0;
		int pos = 0;
		int neg = 0;
		if (ratings != null) {
			for (Rating rating : ratings) {
				if (rating.getRating() >= 3)
					pos++;
				else
					neg++;
			}
			BetaDistributionNew dis = new BetaDistributionNew(pos + 1, neg + 1);
			double trust = dis.mean();
			double accuracy = dis.CDF(trust + errorThreshold) - dis.CDF(trust - errorThreshold);
			if (accuracy >= minAccuracyValue)
				return trust;
		}

		ArrayList<String> advisorNames = ratingManager.findAdvisorNames(buyerName, sellerName);
		double adjustedAlpha = pos;
		double adjustedBeta = neg;
		for (String advisorName : advisorNames) {
			Pair<Double, Double> values = getAdvises(buyerName, sellerName, advisorName);
			adjustedAlpha += values.val1;
			adjustedBeta += values.val2;
		}

		BetaDistributionNew dis = new BetaDistributionNew(adjustedAlpha, adjustedBeta);
		return dis.mean();

	}

	private Pair<Double, Double> calcTrustNAccuracy(ArrayList<Rating> ratings) {
		int pos = 0;
		int neg = 0;
		for (Rating rating : ratings) {
			if (rating.getRating() >= 3)
				pos++;
			else
				neg++;
		}
		BetaDistributionNew dis = new BetaDistributionNew(pos + 1, neg + 1);
		double trust = dis.mean();
		double accuracy = dis.CDF(trust + errorThreshold) - dis.CDF(trust - errorThreshold);
		return new Pair<Double, Double>(trust, accuracy);
	}
}
