package simbase;

import java.util.Collection;
import java.util.HashMap;

import generatorbase.EntityManager;
import modelbase.Entity;

import agentbase.Agent;
import agentbase.Buyer;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.AgentConfig;

public class AgentManager extends EntityManager {
	Sim						sim;
	EntityManager			buyers;
	EntityManager			sellers;
	HashMap<String, Agent>	customAgents;
	public final static int	BUYER_AGENT_TYPE	= 1;
	public final static int	SELLER_AGENT_TYPE	= 2;
	private static int		staticAgentCount	= 0;

	public Collection getAllBuyers() {
		Collection result = buyers.getAll();
		return result;
	}

	public EntityManager getBuyers() {
		return buyers;
	}

	public EntityManager getSellers() {
		return sellers;
	}

	public AgentManager() {
		super();
		buyers = new EntityManager();
		sellers = new EntityManager();
		customAgents = new HashMap<String, Agent>();
	}

	@Override
	public void add(Entity e) throws Exception {
		int aType;
		SQLiteStatement st;
		super.add(e);
		if (e instanceof Buyer) {
			buyers.add(e);
			aType = BUYER_AGENT_TYPE;
		} else {
			sellers.add(e);
			aType = SELLER_AGENT_TYPE;
		}
		st = db.prepare("INSERT INTO Agents(sessionId, name, balance, atype) VALUES(?, ?, ?, ?)");
		st.bind(1, this.getSessionId()).bind(2, e.getName())
				.bind(3, ((AgentConfig) this.config).getInitialBalance()).bind(4, aType);
		st.step();
		staticAgentCount++;
	}

	/**
	 * @return: total number of buyers in the system
	 */
	public int getBuyerNum() {
		return buyers.getSize();
	}

	/**
	 * @return: total number of sellers in the system
	 */
	public int getSellerNum() {
		// TODO Auto-generated method stub
		return sellers.getSize();
	}

	/**
	 * @param sellerName
	 * @return
	 * @throws SQLiteException
	 */
	public Agent getAgentByName(String agentName) {
		Entity e = buyers.get(agentName);
		if (e == null) {
			e = sellers.get(agentName);
		}
		return (Agent) e;
	}

	public void markAsCustomAgent(Agent agent) {
		this.customAgents.put(agent.getName(), agent);
	}

	public boolean isCustomAgent(Agent agent) {
		return (this.customAgents.get(agent.getName()) != null);
	}

	protected String findNewName() {
		return (++staticAgentCount) + "";
	}

	public void requestNewIdentity(Agent agent) throws SQLiteException {
		String oldName = agent.getName();
		if (agent instanceof Buyer) {
			agent.setName("B" + findNewName());
			buyers.replace(oldName, agent.getName());
		} else {
			agent.setName("S" + findNewName());
			sellers.replace(oldName, agent.getName());
		}

		System.out
				.println(String.format("Changing identity for %s -> %s", oldName, agent.getName()));
		/* Update the agent table */
		st = db.prepare("UPDATE Agents SET name=? WHERE name=?");
		st.bind(1, agent.getName()).bind(2, oldName);
		sim.bank.changeIdentity(oldName, agent.getName());
		sim.transactionManager.changeIdentity(oldName, agent.getName());

		/* Update the identity mapping */
		st = db.prepare("SELECT original FROM Identities WHERE changed=?");
		st.bind(1, oldName);

		String originalName;
		if (st.step()) {
			originalName = st.columnString(0);
		} else {
			originalName = oldName;
		}

		st = db.prepare("INSERT INTO Identities(original, changed) VALUES (?, ?)");
		st.bind(1, originalName).bind(2, agent.getName());
		st.step();

		sim.ratingManager.changeIdentity(oldName, agent);
	}

	public Sim getSim() {
		return sim;
	}

	public void setSim(Sim sim) {
		this.sim = sim;
	}

}
