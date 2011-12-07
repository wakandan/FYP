package configbase;

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
	int	buyerNum;
	int	sellerNum;
	double initialBalance;

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
	 * @see configbase.Config#configure(modelbase.Entity)
	 */
	@Override
	public void configure(Entity e) {} 
}
