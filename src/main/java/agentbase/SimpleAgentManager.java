/**
 *
 */ 
package agentbase;

import modelbase.AgentLogicModel;
import generatorbase.EntityManager;

/**
 * @author akai
 *
 */
public class SimpleAgentManager extends AgentManager {
	public int getHonestBuyerNum() {
		int honestBuyerNum = 0;
		EntityManager buyers = getBuyers();
		for(int i=0; i<buyers.getSize(); i++)
			if(((AgentLogicModel)((Agent)buyers.get(i)).getLogicModel()).responseQuery()){
				honestBuyerNum++;
			}
		return honestBuyerNum;
	}
	
	public int getHonestSellerNum() {
		int honestSellerNum = 0;
		EntityManager sellers= getSellers();
		for(int i=0; i<sellers.getSize(); i++)
			if(((AgentLogicModel)((Agent)sellers.get(i)).getLogicModel()).responseQuery()){
				honestSellerNum++;
			}
		return honestSellerNum;
	}
}
