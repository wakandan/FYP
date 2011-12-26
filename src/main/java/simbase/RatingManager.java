package simbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;

import com.almworks.sqlite4java.SQLiteConnection;

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
		if (ratings==null)
			return 0;
		for (Rating rating : ratings)
			sum += rating.rating;
		if (ratings.size()>0)
			return sum/ratings.size();
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
				if (result==null) {
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
}
