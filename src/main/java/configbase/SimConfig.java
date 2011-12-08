/**
 *
 */
package configbase;

import java.io.IOException;

import modelbase.Entity;

/**
 * @author akai
 * 
 */
public class SimConfig extends Config {
	int				maxTimestep;
	AgentConfig		agentConfig;
	ProductConfig	prodConfig;

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

	/* (non-Javadoc)
	 * @see configbase.Config#readConfig(java.lang.String)
	 */
	@Override
	public void readConfig(String filename) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
