/**
 *
 */
package simbase;

import generatorbase.EntityManager;

import java.util.HashMap;

import modelbase.Entity;
import agentbase.Agent;
import agentbase.AgentManager;
import agentbase.Buyer;

import com.almworks.sqlite4java.SQLiteException;

import core.Account;

/**
 * Class to manage system's account money.
 * 
 * @author akai
 */
public class Bank extends EntityManager {
	HashMap<String, Account>	accounts;

	public Bank() {
		super();
		accounts = new HashMap<String, Account>();
	}

	/**
	 * Credit all buyers an amount of money
	 * 
	 * @param creditPerTurn
	 * @throws SQLiteException
	 */
	public void creditAllBuyers(double creditPerTurn) throws SQLiteException {
		/* Update their credit in the database */
		st = db.prepare("UPDATE Agents SET balance=balance+? WHERE aType=?");
		st.bind(1, creditPerTurn).bind(2, AgentManager.BUYER_AGENT_TYPE);
		st.step();
		for (Account a : accounts.values()) {
			a.setBalance(a.getBalance()+creditPerTurn);
		}

	}

	/**
	 * @param aManager
	 */
	public void setAgentManger(AgentManager aManager) {
		Agent agent;
		for (Entity e : aManager.getAll()) {
			agent = (Agent) e;
			accounts.put(agent.getName(), new Account());
		}
	}

	public double getBalance(String accountName) {
		if (accounts.containsKey(accountName)) {
			return accounts.get(accountName).getBalance();
		} else {
			accountNotExistError(accountName);
			return 0;
		}
	}

	public void accountNotExistError(String accountName) {
		logger.error("Account "+accountName+" does not exist");
	}

	public void setBalance(String accountName, double value) {
		if (accounts.containsKey(accountName)) {
			accounts.get(accountName).setBalance(value);
		} else {
			accountNotExistError(accountName);
		}
	}

	public void creditBalance(Buyer buyer, double value) {
		if (accounts.containsKey(buyer.getName())) {
			accounts.get(buyer.getName()).setBalance(value+accounts.get(buyer.getName()).getBalance());
		} else {
			accountNotExistError(buyer.getName());
		}
	}

}
