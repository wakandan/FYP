/**
 *
 */
package modelbase;

import agentbase.Agent;

/**
 * @author akai
 * 
 */
public abstract class IdentityLogic extends ActionLogic {
	Agent	agent;

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.ActionLogic#config()
	 */
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public abstract boolean requestNewIdentity();
}
