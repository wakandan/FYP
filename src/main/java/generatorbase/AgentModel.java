package generatorbase;

import org.apache.log4j.Logger;

import configbase.AgentConfig;
import configbase.Config;
import agentbase.Agent;
import agentbase.AgentManager;
import agentbase.Buyer;
import agentbase.Seller;

public class AgentModel extends EntityModel {
	public final static String	BUYER_NAME_PREFIX	= "B";
	public final static String	SELLER_NAME_PREFIX	= "S";

	/*
	 * (non-Javadoc)
	 * 
	 * @see generatorbase.EntityModel#generate(generatorbase.EntityManager)
	 */
	@Override
	public void generate(EntityManager manager) throws Exception {
		Agent tmpAgent;
		logger.info("Start generating Automatic Agents");
		AgentConfig agConfig = (AgentConfig) config;
		AgentManager aManager = (AgentManager) manager;
		manager.setConfig(agConfig);
		aManager.beginTransaction();
		int numBuyer = agConfig.getBuyerNum();
		int numSeller = agConfig.getSellerNum();
		for (int i = 0; i<numBuyer; i++) {
			tmpAgent = new Buyer(BUYER_NAME_PREFIX+i);
			agConfig.configure(tmpAgent);
			aManager.add(tmpAgent);
		}
		for (int i = 0; i<numSeller; i++) {
			tmpAgent = new Seller(SELLER_NAME_PREFIX+i);
			agConfig.configure(tmpAgent);
			aManager.add(tmpAgent);
		}
		aManager.commitTransaction();
		logger.info("Finish generating Automatic Agents");
		logger.info("No. Buyers generated: "+aManager.getBuyerNum());
		logger.info("No. Sellers generated: "+aManager.getSellerNum());
	}
}
