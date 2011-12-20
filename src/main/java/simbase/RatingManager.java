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
		if (!sellerRatings.containsKey(r.seller.getName())) {
			sellerRatings.put(r.seller.getName(), new ArrayList<Rating>());
		}
		sellerRatings.get(r.seller.getName()).add(r);
	}

	private void addBuyerRating(Rating r) {
		if (!buyerRatings.containsKey(r.buyer.getName())) {
			buyerRatings.put(r.buyer.getName(), new ArrayList<Rating>());
		}
		buyerRatings.get(r.buyer.getName()).add(r);
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
}
