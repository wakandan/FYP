/**
 *
 */
package agentbase;

import generatorbase.Distribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import modelbase.AgentLogicModel;
import modelbase.Entity;
import modelbase.IdentityLogic;
import modelbase.PurchaseLogic;
import modelbase.PurchaseLogicWishlist;
import modelbase.RatingLogic;
import configbase.AgentConfig;
import configbase.Config;
import configbase.DistributionConfig;

/**
 * This is use on per-config file basis.
 * 
 * @author akai
 * 
 */
public class AgentMasterConfig extends Config {

	Class<PurchaseLogic>	purchaseLogic;
	Class<RatingLogic>		ratingLogic;
	Class<IdentityLogic>	identityLogic;
	int						agentNum;
	String					masterName;
	AgentMaster				master;

	public int getAgentNum() {
		return agentNum;
	}

	public String getMasterName() {
		return masterName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity)
	 */
	@Override
	public void configure(Entity e) {
		Buyer buyer = (Buyer) e;
		try {
			buyer.setPurchaseLogic(purchaseLogic.newInstance());
			buyer.getPurchaseLogic().setConfig(this);
			buyer.getPurchaseLogic().config();
			buyer.setRatingLogic(ratingLogic.newInstance());
			buyer.getRatingLogic().setConfig(this);
			buyer.getRatingLogic().config();
			if (identityLogic != null) {
				buyer.setIdentityLogic(identityLogic.newInstance());
				buyer.getIdentityLogic().setAgent(buyer);
				buyer.getIdentityLogic().setConfig(this);
				buyer.getIdentityLogic().config();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#processConfigKey(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected boolean processConfigKey(String key, String value) {
		String purchaseLogicConfigKey = "agentPurchaseLogicClass";
		String ratingLogicConfigKey = "agentRatingLogicClass";
		String identityLogicConfigKey = "identityLogicClass";
		ClassLoader classLoader = AgentLogicModel.class.getClassLoader();
		if (key.equalsIgnoreCase(purchaseLogicConfigKey)
				|| key.equalsIgnoreCase(ratingLogicConfigKey)
				|| key.equalsIgnoreCase(identityLogicConfigKey)) {
			try {
				if (key.equalsIgnoreCase(purchaseLogicConfigKey)) {
					this.purchaseLogic = ((Class<PurchaseLogic>) classLoader.loadClass(value));
				} else if (key.equalsIgnoreCase(ratingLogicConfigKey))
					this.ratingLogic = ((Class<RatingLogic>) classLoader.loadClass(value));
				else if (key.equalsIgnoreCase(identityLogicConfigKey))
					this.identityLogic = ((Class<IdentityLogic>) classLoader.loadClass(value));
			} catch (Exception e) {
				logger.error("Error loading class: " + value);
				e.printStackTrace();
			}
		} else if (key.equalsIgnoreCase("agentNum")) {
			this.agentNum = Integer.parseInt(value);
		} else if (key.equalsIgnoreCase("masterName")) {
			this.masterName = value;
		} else
			return false;
		return true;
	}

	public Class<PurchaseLogic> getPurchaseLogic() {
		return purchaseLogic;
	}

	public void setPurchaseLogic(Class<PurchaseLogic> purchaseLogic) {
		this.purchaseLogic = purchaseLogic;
	}

	public Class<RatingLogic> getRatingLogic() {
		return ratingLogic;
	}

	public void setRatingLogic(Class<RatingLogic> ratingLogic) {
		this.ratingLogic = ratingLogic;
	}

}