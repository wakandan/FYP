package simbase;

import generatorbase.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;

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
			if(agent instanceof Buyer)
				return buyerRatings.get(agent.getName());
			else if(agent instanceof Seller)
				return sellerRatings.get(agent.getName());
			else
				return null;
		} catch (NullPointerException e) {
			return null;
		}
	}	
}
