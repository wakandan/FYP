/**
 *
 */
package trustmodel;

import java.util.ArrayList;

import simbase.RatingManager;

/**
 * @author akai
 * 
 */
public abstract class TrustModel {
	RatingManager	ratingManager;

	public RatingManager getRatingManager() {
		return ratingManager;
	}

	public void setRatingManager(RatingManager ratingManager) {
		this.ratingManager = ratingManager;
	}

	public abstract double calcTrust(String buyerName, String sellerName);

	public String chooseSeller(String buyerName, ArrayList<String> sellerNames) {
		String tmpSeller = null;
		double maxTrust = 0;
		for (String sellerName : sellerNames) {
			double trust = calcTrust(buyerName, sellerName);
			if (trust > maxTrust) {
				maxTrust = trust;
				tmpSeller = sellerName;
			}
		}
		return tmpSeller;
	}

}
