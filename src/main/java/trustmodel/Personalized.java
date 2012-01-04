/**
 *
 */
package trustmodel;

import generatorbase.BetaDistributionNew;

import java.util.ArrayList;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import core.Pair;

import agentbase.Buyer;
import simbase.Rating;
import simbase.RatingManager;

/**
 * @author akai
 * 
 */
public class Personalized {
	RatingManager				ratingManager;
	/*
	 * The personalized approach allows a buyer to model the trustworthiness of
	 * another buyer (advisor) The ratings provided by less trustworthy advisors
	 * will be discounted.
	 */

	/*
	 * To use the personalized method of this class, the two static parameters
	 * have to be first initialized. This two parameters are used for
	 * determining the weight of private reputation. They represent the buyer's
	 * own preference or nature.
	 */
	double						lamda;
	double						gamma;						// this is the
															// confidence
															// measure
															// when determining
															// the weight of
															// private
															// reputation
	double						epsilon;					// this is the
															// acceptable error
															// for
															// determining the
															// weight of private
															// reputation and
															// the threshold for
															// determining the
															// consistency of
															// advisor's ratings
															// with the
															// majority ratings
	double						inconsistency;
	double						forgetting;
	int							timeWindow;				/*
															 * Do a fixed time
															 * window for now
															 */
	private int					nPosPrivate;
	private int					nNegPrivate;
	SQLiteStatement[]			sqlStatements;
	private static final int	ST_getPrivateRep	= 0;
	private static final int	ST_getPublicRep		= 1;

	public Personalized() {
		this.nNegPrivate = 0;
		this.nPosPrivate = 0;
	}

	public double getForgetting() {
		return forgetting;
	}

	public void setForgetting(double forgetting) {
		this.forgetting = forgetting;
	}

	public int getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(int time_window) {
		this.timeWindow = time_window;
	}

	public RatingManager getRatingManager() {
		return ratingManager;
	}

	public void setRatingManager(RatingManager ratingManager) {
		this.ratingManager = ratingManager;
		this.sqlStatements = new SQLiteStatement[10];
		try {
			this.sqlStatements[ST_getPrivateRep] = this.ratingManager.getDb().prepare(
					"SELECT e1.rating, e2.rating, e1.stime, e2.stime "
							+ "FROM Executions e1, Executions e2 "
							+ "WHERE e1.seller_name = e2.seller_name " + "	AND e1.stime>e2.stime "
							+ "	AND e1.stime-e2.stime<? " + "	AND e1.buyer_name = ? "
							+ "	AND e2.buyer_name = ? AND e1.buyer_name != e2.buyer_name");
			this.sqlStatements[ST_getPublicRep] = this.ratingManager
					.getDb()
					.prepare(
							"SELECT e1.rating, e1.seller_name, AVG(e2.rating) "
									+ "FROM Executions e1, Executions e2 "
									+ "WHERE e1.seller_name = e2.seller_name "
									+ "AND e1.stime > e2.stime AND e1.buyer_name != e2.buyer_name AND e1.buyer_name = ? "
									+ "GROUP BY e1.rating, e1.seller_name");
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getLamda() {
		return lamda;
	}

	public void setLamda(double lamda) {
		this.lamda = lamda;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public double getInconsistency() {
		return inconsistency;
	}

	public void setInconsistency(double inconsistency) {
		this.inconsistency = inconsistency;
	}

	public double personalized(String buyerName, String sellerName) {
		ArrayList<String> advisors = ratingManager.findAdvisorNames(buyerName, sellerName);
		double alpha = 0;
		double beta = 0;
		for (String advisorName : advisors) {
			int positive = 0;
			int negative = 0;
			ArrayList<Rating> ratings = ratingManager.getRating(advisorName, sellerName);
			Pair<Integer, Integer> result = ratingManager.aggregateBinaryRating(ratings, 3);
			double accuracy = calcAccuracy(buyerName, advisorName);
			alpha += accuracy * result.val1;
			beta += accuracy * result.val2;
		}
		return (new BetaDistributionNew(alpha + 1, beta + 1)).mean();
	}

	private double calcAccuracy(String buyerName, String advisorName) {
		/* Minimum # of common raters between consumer & advisor */
		double Nmin = -(1.0 / (2.0 * epsilon * epsilon)) * Math.log((1.0 - gamma) / 2.0);

		/* the weight of the private reputation value */
		double w = 0;
		double privateRating = getPrivateRep(buyerName, advisorName);
		double publicRating = getPublicRep(advisorName);

		if (this.nNegPrivate + this.nPosPrivate >= Nmin) {
			w = 1.0;
		} else {
			w = ((double) (this.nNegPrivate + this.nPosPrivate)) / (Nmin);
		}
		return w * privateRating + (1 - w) * publicRating;
	}

	/*
	 * Return private reputation of an advisor from consumer's perspective. It's
	 * based on the number of shared raters between buyer & advisor
	 */
	private double getPrivateRep(String buyerName, String advisorName) {
		SQLiteStatement st = null;
		this.nNegPrivate = 0;
		this.nPosPrivate = 0;
		try {
			/* Get all seller_names */
			st = sqlStatements[ST_getPrivateRep];
			st.bind(1, this.timeWindow).bind(2, buyerName).bind(3, advisorName);
			double positive = 0;
			double negative = 0;
			int rating1 = 0;
			int rating2 = 0;
			int rating1time = -1;
			int lastratetime = -1;
			int rating2time = -1;
			while (st.step()) {
				rating1 = st.columnInt(0);
				rating2 = st.columnInt(1);
				rating1time = st.columnInt(2);
				rating2time = st.columnInt(3);
				/* Only get 1 recent rating pair to prevent flooding up */
				if (rating1time == lastratetime) {
					continue;
				}

				if (rating1 >= 3 && rating2 >= 3 || rating1 < 3 && rating2 < 3) {
					positive += Math.pow(forgetting, rating1time - rating2time);
					this.nPosPrivate++;
				} else {
					negative += Math.pow(forgetting, rating1time - rating2time);
					this.nNegPrivate++;
				}
				lastratetime = rating1time;
			}
			BetaDistributionNew beta = new BetaDistributionNew((double) positive + 1,
					(double) negative + 1);
			st.reset();
			return beta.mean();
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * Return public reputation of an advisor name. It's based on the number of
	 * majority-consistent rating from other raters
	 */
	private double getPublicRep(String advisorName) {
		SQLiteStatement st = null;
		try {
			st = sqlStatements[ST_getPublicRep];
			st.bind(1, advisorName);
			int positive = 0;
			int negative = 0;
			while (st.step()) {
				int thisRating = st.columnInt(0);
				double avgRating = st.columnDouble(1);
				if (thisRating >= 3 && avgRating >= 3 || thisRating < 3 && avgRating < 3) {
					positive++;
				} else {
					negative++;
				}
			}
			st.reset();
			return (new BetaDistributionNew((double) positive + 1, (double) negative + 1).mean());
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
