/**
 *
 */
package trustmodel;

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
}
