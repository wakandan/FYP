package simbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import modelbase.PurchaseLogicRandom;
import modelbase.PurchaseLogicWishlist;
import modelbase.RatingLogicAlwaysNegative;
import modelbase.RatingLogicTruthful;
import modelbase.AlwaysPositive;
import modelbase.AlwaysNegative;
import modelbase.AlwaysUnfairPositive;
import modelbase.AlwaysUnfairNegative;

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
public class TestRatingLogic extends TestWithDBParent {

	// @Test
	/*
	 * public void testAddRating() throws Exception { Buyer buyer = new
	 * Buyer("buyer1"); Buyer buyer2 = new Buyer("buyer2"); Seller seller = new
	 * Seller("seller1"); Product prod = new Product("prod1"); RatingManager
	 * ratingManager = new RatingManager(); Rating rating1 = new Rating(buyer,
	 * seller, 4); Rating rating2 = new Rating(buyer2, seller, 2);
	 * ratingManager.addRating(rating1); ratingManager.addRating(rating2);
	 * assertEquals(1, ratingManager.getRatingsByAgent(buyer).size());
	 * assertEquals(2, ratingManager.getRatingsByAgent(seller).size());
	 * assertEquals(4, ratingManager.getRatingsByAgent(buyer).get(0).rating);
	 * assertEquals(2, ratingManager.getRatingsByAgent(buyer2).get(0).rating); }
	 */

	@Test
	public void testLeaveRating() {
		Buyer buyer = new Buyer("buyer1");
		Buyer buyer2 = new Buyer("buyer2");
		Buyer buyer3 = new Buyer("buyer3");
		Buyer buyer4 = new Buyer("buyer4");
		Seller seller = new Seller("seller1");
		Seller seller2 = new Seller("seller2");
		Product prod = new Product("prod1");
		prod.setValue(1.1);
		buyer3.addTarget(seller2);
		buyer4.addTarget(seller);
		buyer.setPurchaseLogic(new PurchaseLogicWishlist());
		buyer.setRatingLogic(new AlwaysPositive());
		buyer2.setPurchaseLogic(new PurchaseLogicWishlist());
		buyer2.setRatingLogic(new AlwaysNegative());
		buyer3.setPurchaseLogic(new PurchaseLogicWishlist());
		buyer3.setRatingLogic(new AlwaysUnfairPositive());
		buyer4.setPurchaseLogic(new PurchaseLogicWishlist());
		buyer4.setRatingLogic(new AlwaysUnfairNegative());
		Execution execution = new Execution(new Transaction(buyer, seller, prod, 1, 10), true);
		Execution execution2 = new Execution(new Transaction(buyer2, seller, prod, 1, 10), true);
		Execution execution3 = new Execution(new Transaction(buyer3, seller, prod, 1, 10), true);
		Execution execution32 = new Execution(new Transaction(buyer3, seller2, prod, 1, 10), true);
		Execution execution4 = new Execution(new Transaction(buyer4, seller, prod, 1, 10), true);
		Execution execution42 = new Execution(new Transaction(buyer4, seller2, prod, 1, 10), true);
		Rating rate = buyer.leaveRating(execution, prod);
		Rating rate2 = buyer2.leaveRating(execution2, prod);
		Rating rate3 = buyer3.leaveRating(execution3, prod);
		Rating rate32 = buyer3.leaveRating(execution32, prod);
		Rating rate4 = buyer4.leaveRating(execution4, prod);
		Rating rate42 = buyer4.leaveRating(execution42, prod);
		System.out.println("rating for seller of buyer1 is:" + rate.getRating());
		System.out.println("rating for seller of buyer2 is:" + rate2.getRating());
		System.out.println("rating for seller of buyer3 is:" + rate3.getRating());
		System.out.println("rating for seller2 of buyer3 is:" + rate32.getRating());
		System.out.println("rating for seller of buyer4 is:" + rate4.getRating());
		System.out.println("rating for seller2 of buyer4 is:" + rate42.getRating());

		// assertTrue(buyer.leaveRating(new Execution(new Transaction(buyer,
		// seller, prod, 1, 10),
		// true), prod).rating>=4);
		// assertTrue(buyer2.leaveRating(new Execution(new Transaction(buyer2,
		// seller, prod, 1, 10),
		// true), prod).rating<4);
	}
}
