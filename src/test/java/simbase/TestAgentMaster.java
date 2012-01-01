/**
 *
 */
package simbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import modelbase.PurchaseLogicWishlist;

import org.junit.Test;

import agentbase.AgentMasterConfig;
import agentbase.Buyer;
import configbase.DBConfig;

/**
 * @author akai
 * 
 */
public class TestAgentMaster {
	DBConfig	dbConfig;

	@Test
	public void testAgentMasterConfig() throws Exception {
		String masterConfigFile = "src/test/resources/configbase/testAgentMasterConfigTruthful.ini";
		AgentMasterConfig config = new AgentMasterConfig();
		config.readConfig(masterConfigFile);
		int[] productNames = { 0, 4, 5, 7, 9 };
		assertEquals(10, config.getAgentNum());
		Buyer buyer = new Buyer("B1");
		config.configure(buyer);
		ArrayList<String> wishlist = ((PurchaseLogicWishlist) buyer.getPurchaseLogic())
				.getWishList();
		for (int prodNum : productNames) {
			assertTrue(wishlist.indexOf(prodNum + "") > -1);
		}
		assertTrue(config.getMasterName().equalsIgnoreCase("AM01"));
	}
}
