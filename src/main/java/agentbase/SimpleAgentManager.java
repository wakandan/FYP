/**
 *
 */
package agentbase;

import modelbase.AgentLogicModel;
import modelbase.Entity;

/**
 * @author akai
 * 
 */
public class SimpleAgentManager extends AgentManager {
	public int getHonestBuyerNum() {
		int honestBuyerNum = 0;
		for (Entity e : getBuyers().getAll())
			if (((AgentLogicModel) ((Agent) e).getLogicModel()).responseQuery()) {
				honestBuyerNum++;
			}
		return honestBuyerNum;
	}

	public int getHonestSellerNum() {
		int honestSellerNum = 0;
		for (Entity e : getSellers().getAll())
			if (((AgentLogicModel) ((Agent) e).getLogicModel()).responseQuery()) {
				honestSellerNum++;
			}
		return honestSellerNum;
	}
}
