/**
 *
 */
package configbase;

import java.io.IOException;
import java.util.HashMap;

import modelbase.Entity;

/**
 * @author akai
 * 
 */
public class SimConfig extends Config {
	int				maxTimestep;
	AgentConfig		agentConfig;
	ProductConfig	prodConfig;
	public double	creditPerTurn;

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

	public int getMaxTimestep() {
		return maxTimestep;
	}

	public void setMaxTimestep(int maxTimestep) {
		this.maxTimestep = maxTimestep;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#readConfig(java.lang.String)
	 */
	@Override
	public void readConfig(String filename) throws IOException {
		HashMap<String, String> configFile = Config.readConfigFile(filename);
		String value;
		for (String key : configFile.keySet()) {
			value = configFile.get(key);
			if (key.equalsIgnoreCase("creditPerTurn")) {
				this.creditPerTurn = Double.parseDouble(value);
			} else if (key.equalsIgnoreCase("maxTimestep")) {
				this.maxTimestep = Integer.parseInt(value);
			}
		}
	}

}
