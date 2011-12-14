/**
 *
 */
package modelbase;

import agentbase.Agent;

/**
 * @author akai
 * 
 */
public abstract class AgentLogicModel extends LogicModel {
	Agent	agent;

	/*
	 * This function is used for testing the SimpleAgentConfig class. This is
	 * simply to response whether it's a honest agent
	 */
	public AgentLogicModel() {
		super();
	}
	public abstract boolean responseQuery();
}
