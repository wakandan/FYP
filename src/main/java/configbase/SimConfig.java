/**
 *
 */
package configbase;

import java.io.IOException;
import java.util.HashMap;

import agentbase.AgentMaster;
import agentbase.AgentMasterConfig;

import modelbase.AgentLogicModel;
import modelbase.Entity;

/**
 * @author akai
 * 
 */
public class SimConfig extends Config {
	AgentConfig						agentConfig;
	ProductConfig					prodConfig;
	SchedulerConfig					schedulerConfig;
	public double					creditPerTurn;
	HashMap<String, AgentMaster>	agentMasters;

	public HashMap<String, AgentMaster> getAgentMasters() {
		return agentMasters;
	}

	public SchedulerConfig getSchedulerConfig() {
		return schedulerConfig;
	}

	public double getCreditPerTurn() {
		return creditPerTurn;
	}

	public void setCreditPerTurn(double creditPerTurn) {
		this.creditPerTurn = creditPerTurn;
	}

	public AgentConfig getAgentConfig() {
		return agentConfig;
	}

	public void setAgentConfig(AgentConfig agentConfig) {
		this.agentConfig = agentConfig;
	}

	public ProductConfig getProdConfig() {
		return prodConfig;
	}

	public void setProdConfig(ProductConfig prodConfig) {
		this.prodConfig = prodConfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity)
	 */
	@Override
	public void configure(Entity e) {
		// TODO Auto-generated method stub

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
		try {
			if (key.equalsIgnoreCase("creditPerTurn")) {
				this.creditPerTurn = Double.parseDouble(value);
			} else if (key.equalsIgnoreCase("agentConfigFile")) {
				this.agentConfig.readConfig(value);
			} else if (key.equalsIgnoreCase("productConfigFile")) {
				this.prodConfig = new ProductConfig();
				this.prodConfig.readConfig(value);
			} else if (key.equalsIgnoreCase("agentConfigClass")) {
				try {
					/*
					 * Dynamically create a new object from the distribution
					 * class
					 */
					Class<AgentConfig> agentConfigClass = (Class<AgentConfig>) classLoader
							.loadClass(value);
					logger.debug("Initiating agent logic class "+agentConfigClass.getName());
					this.agentConfig = agentConfigClass.newInstance();
				} catch (ClassNotFoundException e) {
					logger.error("Class "+value+" cannot be found");
					e.printStackTrace();
				} catch (InstantiationException e) {
					logger.error("Class "
							+value
							+" cannot be instantiated. Unable to read config entries/Does your class have a default constructor?");
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Error loading class: "+value);
					e.printStackTrace();
				}
			} else if (key.equalsIgnoreCase("agentMasterConfigFile")) {
				String[] dataList = value.split(";");
				if (agentMasters==null)
					agentMasters = new HashMap<String, AgentMaster>();
				for (int i = 0; i<dataList.length; i++) {
					AgentMasterConfig agentMasterConfig = new AgentMasterConfig();
					agentMasterConfig.readConfig(dataList[i]);
					AgentMaster agentMaster = new AgentMaster(agentMasterConfig);
					agentMasters.put(agentMaster.getMasterName(), agentMaster);
				}
			} else if (key.equalsIgnoreCase("schedulerConfigFile")) {
				this.schedulerConfig = new SchedulerConfig();
				this.schedulerConfig.readConfig(value);
			} else
				return false;
			return true;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return false;
	}
}
