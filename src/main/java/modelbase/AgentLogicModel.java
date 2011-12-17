/**
 *
 */
package modelbase;

import java.util.ArrayList;

import productbase.Product;
import simbase.Rating;
import simbase.Sim;
import agentbase.Agent;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public abstract class AgentLogicModel extends LogicModel {
	Agent	agent;
	Sim		sim;

	public Sim getSim() {
		return sim;
	}

	public void setSim(Sim sim) {
		this.sim = sim;
	}

	/*
	 * This function is used for testing the SimpleAgentConfig class. This is
	 * simply to response whether it's a honest agent
	 */
	public AgentLogicModel() {
		super();
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
}
