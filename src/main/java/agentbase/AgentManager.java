package agentbase;

import generatorbase.EntityManager;
import modelbase.Entity;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.AgentConfig;

public class AgentManager extends EntityManager {
	EntityManager			buyers;
	EntityManager			sellers;
	public final static int	BUYER_AGENT_TYPE	= 1;
	public final static int	SELLER_AGENT_TYPE	= 2;

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
		st.bind(1, this.getSessionId()).bind(2, e.getName()).bind(3, ((AgentConfig) this.config).getInitialBalance()).bind(4, aType);
		st.step();
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
		if (e==null) {
			e = sellers.get(agentName);
		}
		return (Agent) e;
	}

}
