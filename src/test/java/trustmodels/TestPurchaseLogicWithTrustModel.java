/**
 *
 */
package trustmodels;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import simbase.TestSimRunParent;
import trustmodel.TrustModelBRS;
import agentbase.AgentMasterConfig;
import agentbase.Buyer;
import configbase.Config;
import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class TestPurchaseLogicWithTrustModel extends TestSimRunParent {

	@Test
	public void testLoadTrustModel() throws Exception {
		// sim.run();
		String configFileName = "src/test/resources/trustmodel/TestPurchaseLogicWithTrustModel_AgentMasterConfig.ini";
		AgentMasterConfig agentMasterConfig = (AgentMasterConfig) Config.config(
				AgentMasterConfig.class, configFileName);
		Buyer buyer = new Buyer("Buyer1");
		agentMasterConfig.configure(buyer);
		assertTrue(buyer.getPurchaseLogic().trustModel instanceof TrustModelBRS);
	}

	@Test
	public void testRunSimWithTrustModelLoaded() throws Exception {
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/TestSimConfigBRS.ini");
		sim.setSimConfig(simConfig);
		sim.run();
	}
}
