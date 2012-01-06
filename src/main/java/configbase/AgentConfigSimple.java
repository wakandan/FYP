/**
 *
 */
package configbase;

import java.util.HashMap;
import java.util.Random;

import modelbase.DishonestAutoSellerLogicModel;
import modelbase.Entity;
import modelbase.HonestAutoSellerLogicModel;
import modelbase.PurchaseLogicRandom;
import modelbase.PurchaseLogicWishlist;
import modelbase.RatingLogicAlwaysNegative;
import modelbase.RatingLogicTruthful;
import agentbase.Agent;
import agentbase.Buyer;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public class AgentConfigSimple extends AgentConfig {

	double					ratioHonestBuyer	= 0.7;
	double					ratioHonestSeller	= 0.9;
	HashMap<String, Double>	honest;

	public AgentConfigSimple() {
		super();
		honest = new HashMap<String, Double>();
	}

	public double getRatioHonestBuyer() {
		return ratioHonestBuyer;
	}

	public void setRatioHonestBuyer(double ratioHonestBuyer) {
		this.ratioHonestBuyer = ratioHonestBuyer;
	}

	public double getRatioHonestSeller() {
		return ratioHonestSeller;
	}

	public void setRatioHonestSeller(double ratioHonestSeller) {
		this.ratioHonestSeller = ratioHonestSeller;
	}

	/**
	 * @param tmpAgent
	 */
	public void configure(Entity entity) {
		double honestValue = 0;
		if (entity instanceof Buyer) {
			Buyer buyer = (Buyer) entity;
			if (decideHonestBuyer(buyer)) {
				buyer.setPurchaseLogic(new PurchaseLogicWishlist());
				buyer.setRatingLogic(new RatingLogicTruthful());
				honestValue = 1;
			} else {
				buyer.setPurchaseLogic(new PurchaseLogicRandom());
				buyer.setRatingLogic(new RatingLogicAlwaysNegative());
				honestValue = 0;
			}
		} else if (entity instanceof Seller) {
			Seller seller = (Seller) entity;
			if (decideHonestSeller(seller)) {
				seller.setLogicModel(new HonestAutoSellerLogicModel());
				honestValue = 1;
			} else {
				seller.setLogicModel(new DishonestAutoSellerLogicModel());
				honestValue = 0;
			}
		}
		honest.put(entity.getName(), honestValue);
	}

	private boolean decideHonestBuyer(Entity e) {
		Random random = new Random();
		if (random.nextDouble() <= ratioHonestBuyer)
			return true;
		else
			return false;
	}

	private boolean decideHonestSeller(Entity e) {
		Random random = new Random();
		if (random.nextDouble() <= ratioHonestSeller)
			return true;
		else
			return false;
	}

	@Override
	public double checkHonest(Agent agent) {
		return honest.get(agent.getName());
	}
}
