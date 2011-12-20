/**
 *
 */
package configbase;

import java.util.Random;

import modelbase.AgentLogicModel;
import modelbase.DishonestAutoBuyerLogicModel;
import modelbase.DishonestAutoSellerLogicModel;
import modelbase.Entity;
import modelbase.HonestAutoBuyerLogicModel;
import modelbase.HonestAutoSellerLogicModel;
import agentbase.Buyer;
import agentbase.Seller;
import agentbase.Agent;

/**
 * @author akai
 * 
 */
public class SimpleAgentConfig extends AgentConfig {

	double	ratioHonestBuyer	= 0.7;
	double	ratioHonestSeller	= 0.9;

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
		if (entity instanceof Buyer) {
			if (decideHonestBuyer(entity)) {
				((Buyer) entity).setLogicModel(new HonestAutoBuyerLogicModel());
			} else {
				((Buyer) entity).setLogicModel(new DishonestAutoBuyerLogicModel());
			}
		} else if (entity instanceof Seller) {
			if (decideHonestSeller(entity)) {
				((Seller) entity).setLogicModel(new HonestAutoSellerLogicModel());
			} else {
				((Seller) entity).setLogicModel(new DishonestAutoSellerLogicModel());
			}
		}
	}

	private boolean decideHonestBuyer(Entity e) {
		Random random = new Random();
		if (random.nextDouble()<=ratioHonestBuyer)
			return true;
		else
			return false;
	}

	private boolean decideHonestSeller(Entity e) {
		Random random = new Random();
		if (random.nextDouble()<=ratioHonestSeller)
			return true;
		else
			return false;
	}
}
