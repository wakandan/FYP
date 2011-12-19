/**
 *
 */
package simbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import modelbase.HonestAutoBuyerLogicModel;

import org.junit.Before;
import org.junit.Test;

import agentbase.AgentMasterConfig;
import configbase.DBConfig;

/**
 * @author akai
 * 
 */
public class TestAgentMaster {
	DBConfig	dbConfig;

	@Test
	public void testAgentMasterConfig() throws Exception {
		String masterConfigFile = "src/test/resources/configbase/testAgentMasterConfig.ini";
		AgentMasterConfig config = new AgentMasterConfig();
		config.readConfig(masterConfigFile);
		assertTrue(config.getLogicModel() instanceof HonestAutoBuyerLogicModel);
		int[] productNames = { 0, 4, 5, 7, 9 };
		assertEquals(10, config.getAgentNum());
		for (int name : productNames) {
			assertTrue(config.getWishlist().indexOf(name+"")>-1);
		}
		assertTrue(config.getMasterName().equalsIgnoreCase("AM01"));
	}
}
