package agentbase;

import java.util.ArrayList;
import java.util.HashMap;

import configbase.AgentConfig;
import core.Configurable;

import modelbase.AgentLogicModel;

import generatorbase.EntityManager;

public class AgentMaster extends EntityManager {
	String				masterName;
	AgentMasterConfig	masterConfig;

	public String getMasterName() {
		return masterName;
	}

	public AgentMaster(AgentMasterConfig masterConfig) {
		super();
		Buyer buyer;
		this.masterConfig = masterConfig;
		this.masterName = masterConfig.masterName;
		for (int i = 0; i < masterConfig.agentNum; i++) {
			buyer = new Buyer(masterConfig.masterName + "_" + i);
			masterConfig.configure(buyer);
			entities.put(buyer.getName(), buyer);
		}
	}
}
