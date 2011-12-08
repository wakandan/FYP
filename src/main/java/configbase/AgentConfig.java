package configbase;

import java.io.IOException;
import java.util.HashMap;

import modelbase.DishonestAutoBuyerLogicModel;
import modelbase.DishonestAutoSellerLogicModel;
import modelbase.Entity;
import modelbase.HonestAutoBuyerLogicModel;
import modelbase.HonestAutoSellerLogicModel;
import agentbase.Agent;
import agentbase.Buyer;
import agentbase.Seller;

public class AgentConfig extends Config {
	/**
	 * @param config
	 */
	int		buyerNum;
	int		sellerNum;
	double	initialBalance;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity)
	 */
	@Override
	public void configure(Entity e) {}

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
			if (key.equalsIgnoreCase("buyerNum"))
				this.buyerNum = Integer.parseInt(value);
			else if (key.equalsIgnoreCase("sellerNum"))
				this.sellerNum = Integer.parseInt(value);
			else if (key.equalsIgnoreCase("initialBalance"))
				this.initialBalance = Double.parseDouble(value);
		}
	}
}
