/**
 *
 */
package agentbase;

import generatorbase.Distribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import modelbase.AgentLogicModel;
import modelbase.BuyerLogicModel;
import modelbase.Entity;
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

	AgentLogicModel		logicModel;
	ArrayList<String>	wishlist;
	int					agentNum;
	String				masterName;

	public AgentLogicModel getLogicModel() {
		return logicModel;
	}

	public ArrayList<String> getWishlist() {
		return wishlist;
	}

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
		((Buyer) e).setLogicModel(logicModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#processConfigKey(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	protected boolean processConfigKey(String key, String value) {
		ClassLoader classLoader = AgentLogicModel.class.getClassLoader();
		if (key.equalsIgnoreCase("agentLogicModelClass")) {
			try {
				this.logicModel = ((Class<AgentLogicModel>) classLoader.loadClass(value))
						.newInstance();
			} catch (Exception e) {
				logger.error("Error loading class: "+value);
				e.printStackTrace();
			}
		} else if (key.equalsIgnoreCase("agentNum")) {
			this.agentNum = Integer.parseInt(value);
		} else if (key.equalsIgnoreCase("wishlist")) {
			/* Handle a fixed wishlist for now; */
			wishlist = new ArrayList<String>(Arrays.asList(value.split(",")));
		} else if (key.equalsIgnoreCase("masterName")) {
			this.masterName = value;
		} else
			return false;
		return true;
	}

}
