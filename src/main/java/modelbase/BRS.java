package modelbase;

import java.util.ArrayList;
import java.util.Vector;

import simbase.Rating;
import simbase.RatingManager;

import agentbase.Buyer;

import generatorbase.BetaDistributionNew;

public class BRS {

	RatingManager	ratingManager;

	double			quantile;

	public RatingManager getRatingManager() {
		return ratingManager;
	}

	public void setRatingManager(RatingManager ratingManager) {
		this.ratingManager = ratingManager;
	}

	public double getQuantile() {
		return quantile;
	}

	public void setQuantile(double quantile) {
		this.quantile = quantile;
	}

	/*
	 * The BRS system uses an iterated filtering approach to filter out the
	 * ratings provided advisors that are considered as unfair When aggregating
	 * ratings of sellers for estimating their trustworthiness, those unfair
	 * ratings will not be considered.
	 * 
	 * This class provides a function of detecting which advisor is dishonest
	 * and not considered. It can be used later on when aggregating ratings of
	 * advisors This class also provides a function to return reputation of a
	 * seller after filtering out dishonest advisor's ratings
	 * 
	 * This method returns a list of all dishonest buyers for one particular
	 * seller with id number sid
	 */
	public ArrayList brs(String seller_name, ArrayList<String> allBuyers) {

		ArrayList<String> dishonest = new ArrayList<String>();
		ArrayList<String> honest = new ArrayList<String>();
		honest = new ArrayList<String>(allBuyers);

		double reputation = 0;
		int size; // the variable to keep track of the current size of buyers
					// before detecting dishonest buyers

		do {
			size = honest.size();
			/*
			 * num of positive ratings provided by all buyers for the seller
			 */
			int positive = 0;
			/*
			 * num of negative ratings provided by all buyers for the seller
			 */
			int negative = 0;
			for (String buyer_name : allBuyers) {
				// go through each buyer's rating list and find all ratings for
				// the seller
				if (honest.contains(buyer_name)) {
					ArrayList<Rating> ratings = ratingManager.getRatingsByBuyerName(buyer_name);
					if (ratings!=null) {
						for (Rating rating : ratings) {
							if (rating.getRating()>=3) {
								positive++;
							} else {
								negative++;
							}
						}
					}

				}
			}

			// create beta distribution of positive and negative ratings
			BetaDistributionNew rep_distribution = new BetaDistributionNew((double) (positive+1),
					(double) (negative+1));
			// the expected value of the distribution, which is the overall
			// reputation of the seller
			reputation = rep_distribution.mean();

			System.out.println(positive);
			System.out.println(negative);

			for (String buyer_name : allBuyers) {
				if (honest.contains(buyer_name)) {
					ArrayList<Rating> ratings = ratingManager.getRating(buyer_name, seller_name);
					if (ratings!=null) {
						int p = 0; // num of positive ratings provided by each
									// buyer for the seller
						int n = 0; // num of negative ratings provided by each
									// buyer for the seller

						for (Rating rating : ratings) {
							if (rating.getRating()>=3)
								p++;
							else
								n++;
						}

						// create beta distribution of ratings provided by the
						// buyer for the seller
						BetaDistributionNew rep_distribution_buyer = new BetaDistributionNew(
								(double) (p+1), (double) (n+1));
						// lower quantile and upper quantile of the distribution
						// created from the buyer's ratings for the seller

						double l = rep_distribution_buyer.getProbabilityOfQuantile(quantile);
						double u = rep_distribution_buyer.getProbabilityOfQuantile(1-quantile);

						if (reputation<l||reputation>u) {

							// this buyer is dishonest, and add it to the
							// dishont list
							dishonest.add(buyer_name);
							// remove this buyer from the honest list
							honest.remove(buyer_name);
						}
					}
				}
			}
		} while (size!=honest.size());

		// add the reputation of the seller at the end of the list
		ArrayList lists = new ArrayList();
		System.out.println(seller_name+" "+honest.size()+" "+dishonest.size());
		lists.add(dishonest);
		lists.add(reputation+"");
		return lists;
	}
}
