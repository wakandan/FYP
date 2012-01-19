package configbase;

import generatorbase.Distribution;

import java.io.IOException;
import java.util.HashMap;

import modelbase.AgentLogicModel;
import modelbase.DishonestAutoSellerLogicModel;
import modelbase.Entity;
import modelbase.HonestAutoSellerLogicModel;
import agentbase.Agent;
import agentbase.Buyer;
import agentbase.Seller;

public class AgentConfig extends Config {
	/**
	 * @param config
	 */
	int				buyerNum;
	int				sellerNum;
	double			initialBalance;
	AgentLogicModel	logicModel;

	public void setBuyerNum(int buyerNum) {
		this.buyerNum = buyerNum;
	}

	public void setSellerNum(int sellerNum) {
		this.sellerNum = sellerNum;
	}

	public int getBuyerNum() {
		return buyerNum;
	}

	public int getSellerNum() {
		return sellerNum;
	}

	/**
	 * @return
	 */
	public double getInitialBalance() {
		// TODO Auto-generated method stub
		return initialBalance;
	}

	/* (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity) */
	@Override
	public void configure(Entity e) {}

	/* (non-Javadoc)
	 * 
	 * @see configbase.Config#processConfigKey(java.lang.String,
	 * java.lang.String) */
	@Override
	public boolean processConfigKey(String key, String value) {
		if (key.equalsIgnoreCase("buyerNum"))
			this.buyerNum = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("sellerNum"))
			this.sellerNum = Integer.parseInt(value);
		else if (key.equalsIgnoreCase("initialBalance"))
			this.initialBalance = Double.parseDouble(value);
		else
			return false;
		return true;
	}

	/* To be overriden by child classes */
	public double checkHonest(Agent agent) {
		return 0;
	}
}
