/**
 *
 */
package generatorbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agentbase.AgentManager;
import agentbase.SimpleAgentManager;

import com.almworks.sqlite4java.SQLiteStatement;

import configbase.AgentConfig;
import configbase.SimpleAgentConfig;
import core.TestWithDBParent;

/**
 * @author akai
 * 
 */
public class TestAgentModel extends TestWithDBParent {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testAddAgents() throws Exception {
		AgentConfig aConfig = new AgentConfig();
		aConfig.readConfig("src/test/resources/generatorbase/TestAgentModelConfig.ini");
		SQLiteStatement st;
		AgentModel aModel = new AgentModel();
		AgentManager aManager = new AgentManager();
		aManager.setDb(db);
		aModel.setConfig(aConfig);
		aModel.generate(aManager);
		assertEquals(aConfig.getBuyerNum(), aManager.getBuyerNum());
		assertEquals(aConfig.getSellerNum(), aManager.getSellerNum());
		st = db.prepare("SELECT COUNT(*) FROM Agents WHERE sessionId = ? AND aType = ?");
		st.bind(1, aManager.getSessionId()).bind(2, AgentManager.BUYER_AGENT_TYPE);
		st.step();
		assertEquals(aConfig.getBuyerNum(), st.columnInt(0));
	}

	@Test
	public void testSimpleAgentConfig() throws Exception {
		AgentConfig aConfig = new SimpleAgentConfig();
		aConfig.readConfig("src/test/resources/generatorbase/TestAgentModelConfig.ini");
		AgentModel aModel = new AgentModel();
		AgentManager aManager = new SimpleAgentManager();
		aManager.setDb(db);
		aModel.setConfig(aConfig);
		aModel.generate(aManager);
		/*
		 * The ratios in SimpleAgentConfig was hardcoded, so for this test, test
		 * data are also hardcoded. Probability of Honest buyers: 0.7
		 * Probability of Honest sellers: 0.9
		 */
		assertTrue(((SimpleAgentManager) aManager).getHonestBuyerNum()*1.0/aManager.getBuyerNum()>=0.5);
		assertTrue(((SimpleAgentManager) aManager).getHonestSellerNum()*1.0/aManager.getSellerNum()>=0.5);
	}
}
