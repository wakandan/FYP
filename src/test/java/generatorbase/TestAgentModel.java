/**
 *
 */ 
package generatorbase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agentbase.AgentManager;
import agentbase.SimpleAgentManager;

import com.almworks.sqlite4java.SQLiteStatement;

import configbase.AgentConfig;
import configbase.SimpleAgentConfig;

/**
 * @author akai
 *
 */
public class TestAgentModel extends TestWithDBParent{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		SQLiteStatement st;
		st = db.prepare("" +
				"CREATE TABLE Agents(sessionId TEXT, " +
				"						name TEXT, " +
				"						balance NUMERIC," +
				"						aType NUMERIC)");
		st.step();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void testAddAgents() throws Exception{
		AgentConfig aConfig = new AgentConfig();
		int buyerNum = 100;
		int sellerNum = 100;
		SQLiteStatement st;
		aConfig.setBuyerNum(buyerNum);
		aConfig.setSellerNum(sellerNum);
		AgentModel aModel = new AgentModel();
		AgentManager aManager = new AgentManager();
		aManager.setDb(db);
		aModel.setConfig(aConfig);
		aModel.generate(aManager);
		assertEquals(buyerNum, aManager.getBuyerNum());
		assertEquals(sellerNum, aManager.getSellerNum());
		st = db.prepare("SELECT COUNT(*) FROM Agents " +
				"WHERE sessionId = ? AND aType = ?");
		st.bind(1, aManager.getSessionId()).bind(2, AgentManager.BUYER_AGENT_TYPE);
		st.step();
		assertEquals(buyerNum, st.columnInt(0));
	}
	
	@Test 
	public void testSimpleAgentConfig() throws Exception{
		AgentConfig aConfig = new SimpleAgentConfig();
		int buyerNum = 100;
		int sellerNum = 100;
		SQLiteStatement st;
		aConfig.setBuyerNum(buyerNum);
		aConfig.setSellerNum(sellerNum);
		AgentModel aModel = new AgentModel();
		AgentManager aManager = new SimpleAgentManager();
		aManager.setDb(db);
		aModel.setConfig(aConfig);
		aModel.generate(aManager);
		/*The ratios in SimpleAgentConfig was hardcoded, so for this test, 
		 * test data are also hardcoded. 
		 * 	Probability of Honest buyers: 0.7
		 * 	Probability of Honest sellers: 0.9*/
		assertTrue(((SimpleAgentManager)aManager).getHonestBuyerNum()*1.0/aManager.getBuyerNum()>=0.5);
		assertTrue(((SimpleAgentManager)aManager).getHonestSellerNum()*1.0/aManager.getSellerNum()>=0.5);
	}
}
