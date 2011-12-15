/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import modelbase.AgentLogicModel;
import modelbase.DishonestAutoBuyerLogicModel;
import modelbase.HonestAutoBuyerLogicModel;

import org.junit.Test;

import configbase.SimpleAgentConfig;

import productbase.Product;

import agentbase.Buyer;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public class TestRatingManager {

	@Test
	public void testAddRating() throws Exception {
		Buyer buyer = new Buyer("buyer1");
		Buyer buyer2 = new Buyer("buyer2");
		Seller seller = new Seller("seller1");
		Product prod = new Product("prod1");
		RatingManager ratingManager = new RatingManager();
		Rating rating1 = new Rating(buyer, seller, 4);
		Rating rating2 = new Rating(buyer2, seller, 2);
		ratingManager.addRating(rating1);
		ratingManager.addRating(rating2);
		assertEquals(1, ratingManager.getRatingsByAgent(buyer).size());
		assertEquals(2, ratingManager.getRatingsByAgent(seller).size());
		assertEquals(4, ratingManager.getRatingsByAgent(buyer).get(0).rating);
		assertEquals(2, ratingManager.getRatingsByAgent(buyer2).get(0).rating);
	}

	@Test
	public void testLeaveRating() {
		Buyer buyer = new Buyer("buyer1");
		Buyer buyer2 = new Buyer("buyer2");
		Seller seller = new Seller("seller1");
		Product prod = new Product("prod1");
		prod.setValue(1.1);
		buyer.setLogicModel(new HonestAutoBuyerLogicModel());
		buyer2.setLogicModel(new DishonestAutoBuyerLogicModel());
		assertTrue(buyer.leaveRating(seller, prod).rating>=4);
		assertTrue(buyer2.leaveRating(seller, prod).rating<4);
	}

}
