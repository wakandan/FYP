package configbase;

import agentbase.Buyer;
import agentbase.Seller;

public class AgentConfig extends Config {
	/**
	 * @param config
	 */
	int buyerNum;
	int sellerNum;	
	Buyer buyers[];
	Seller sellers[];
}
