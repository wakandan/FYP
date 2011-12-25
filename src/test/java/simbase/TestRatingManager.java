/**
 *
 */
package simbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import modelbase.PurchaseLogicRandom;
import modelbase.PurchaseLogicWishlist;
import modelbase.RatingLogicAlwaysNegative;
import modelbase.RatingLogicTruthful;

import org.junit.Test;

import com.almworks.sqlite4java.SQLiteException;

import core.TestWithDBParent;

import productbase.Product;
import agentbase.Buyer;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public class TestRatingManager extends TestWithDBParent {

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
		buyer.setPurchaseLogic(new PurchaseLogicWishlist());
		buyer.setRatingLogic(new RatingLogicTruthful());
		buyer2.setPurchaseLogic(new PurchaseLogicRandom());
		buyer2.setRatingLogic(new RatingLogicAlwaysNegative());
		assertTrue(buyer.leaveRating(new Execution(new Transaction(buyer, seller, prod, 1, 10),
				true), prod).rating>=4);
		assertTrue(buyer2.leaveRating(new Execution(new Transaction(buyer2, seller, prod, 1, 10),
				true), prod).rating<4);
	}

	@Test
	public void testGetRating() throws SQLiteException {
		st = db.prepare(
				"INSERT INTO Executions(buyer_name, seller_name, prod_name, status, rating, stime) VALUES (?, ?, 1, ?, 5, 5)")
				.bind(1, "B1").bind(2, "S1").bind(3, Execution.STATUS_SUCCESS);
		st.step();
		RatingManager ratingManager = new RatingManager();
		ratingManager.setDb(db);
		ArrayList<Rating> result = ratingManager.getRating("B1", "S1");
		assertEquals(1, result.size());
	}
}
