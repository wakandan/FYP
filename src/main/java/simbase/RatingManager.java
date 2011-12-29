package simbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;

import agentbase.Agent;
import agentbase.Buyer;
import agentbase.Seller;

public class RatingManager extends EntityManager {
	HashMap<String, ArrayList<Rating>>	sellerRatings, buyerRatings;

	public RatingManager() {
		super();
		this.sellerRatings = new HashMap<String, ArrayList<Rating>>();
		this.buyerRatings = new HashMap<String, ArrayList<Rating>>();
	}

	public void addRating(Rating r) throws Exception {
		super.add(r);
		addSellerRating(r);
		addBuyerRating(r);
	}

	private void addSellerRating(Rating r) {
		if (!sellerRatings.containsKey(r.seller_name)) {
			sellerRatings.put(r.seller_name, new ArrayList<Rating>());
		}
		sellerRatings.get(r.seller_name).add(r);
	}

	private void addBuyerRating(Rating r) {
		if (!buyerRatings.containsKey(r.buyer_name)) {
			buyerRatings.put(r.buyer_name, new ArrayList<Rating>());
		}
		buyerRatings.get(r.buyer_name).add(r);
	}

	public ArrayList<Rating> getRatingsByAgent(Agent agent) {
		try {
			if (agent instanceof Buyer)
				return buyerRatings.get(agent.getName());
			else if (agent instanceof Seller)
				return sellerRatings.get(agent.getName());
			else
				return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public ArrayList<Rating> getRatingsByBuyerName(String buyer_name) {
		return buyerRatings.get(buyer_name);
	}

	public ArrayList<Rating> getRatingsBySellerName(String seller_name) {
		return buyerRatings.get(seller_name);
	}

	public double calcRating(ArrayList<Rating> ratings) {
		double sum = 0;
		if (ratings == null)
			return 0;
		for (Rating rating : ratings)
			sum += rating.rating;
		if (ratings.size() > 0)
			return sum / ratings.size();
		else
			return 0;
	}

	public void reportRating() {
		for (String sellerName : sellerRatings.keySet()) {
			logger.debug(String
					.format("Rating for %5s: %5.2f (x%5d)", sellerName, calcRating(sellerRatings
							.get(sellerName)), sellerRatings.get(sellerName).size()));
		}
	}

	public ArrayList<Rating> getRating(String buyer_name, String seller_name) {
		ArrayList<Rating> result = null;
		try {
			st = db.prepare("SELECT rating, stime FROM Executions WHERE buyer_name=? AND seller_name=? ORDER BY stime");
			st.bind(1, buyer_name).bind(2, seller_name);
			while (st.step()) {
				if (result == null) {
					result = new ArrayList<Rating>();
				}
				Rating rating = new Rating(buyer_name, seller_name, st.columnInt(0));
				rating.setStime(st.columnInt(1));
				result.add(rating);
			}
		} catch (Exception e) {
			logger.error(String.format("Error getting rating for %5s & %5s", buyer_name,
					seller_name));
			System.out.println(e);
		}
		return result;
	}

	public ArrayList<Rating> getRatingsBefore(String buyerName, String sellerName, int stime) {
		ArrayList<Rating> result = null;
		try {
			st = db.prepare("SELECT rating, stime FROM Executions WHERE buyer_name=? AND seller_name=? AND stime<? ORDER BY stime");
			st.bind(1, buyerName).bind(2, sellerName).bind(3, stime);
			while (st.step()) {
				if (result == null) {
					result = new ArrayList<Rating>();
				}
				Rating rating = new Rating(buyerName, sellerName, st.columnInt(0));
				rating.setStime(st.columnInt(1));
				result.add(rating);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Find buyers who transacted with the same seller before the current time
	 * stamp
	 */
	public ArrayList<String> findAdvisorNames(String buyerName, String sellerName) {
		ArrayList<String> result = null;
		try {
			result = new ArrayList<String>();
			st = db.prepare("SELECT buyer_name FROM Executions WHERE buyer_name!=? AND seller_name=?");
			st.bind(1, buyerName).bind(2, sellerName);
			while (st.step()) {
				String advisorName = st.columnString(0);
				result.add(advisorName);
			}
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Find common ratees between a buyer and an advisor
	 */
	public ArrayList<String> findCommonRatees(String buyerName, String advisorName) {
		ArrayList<String> result = null;
		try {
			result = new ArrayList<String>();
			st = db.prepare("SELECT seller_name FROM Executions WHERE buyer_name=? AND seller_name IN (SELECT seller_name FROM Executions where buyer_name=?)");
			st.bind(1, buyerName).bind(2, advisorName);
			while (st.step()) {
				String sellerName = st.columnString(0);
				result.add(sellerName);
			}
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		return result;
	}
}
