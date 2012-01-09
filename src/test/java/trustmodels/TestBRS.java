/**
 *
 */
package trustmodels;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import agentbase.Buyer;

import configbase.Config;
import configbase.SimConfig;

import simbase.TestSimRunParent;
import trustmodel.TrustModelBRS;

/**
 * @author akai
 * 
 */
public class TestBRS extends TestSimRunParent {

	@Test
	public void testBRSRun() throws Exception {
		sim.run();
		TrustModelBRS brs = (TrustModelBRS) Config.config(TrustModelBRS.class,
				"src/test/resources/trustmodel/TrustModelBRSConfig.ini");
		brs.setRatingManager(sim.getRatingManager());
		for (String sellerName : sim.getAgentManager().getSellers().getAllNames()) {
			System.out.println("Seller " + sellerName + "->" + brs.calcTrust("null", sellerName));
		}
	}

	@Test
	public void testBRSWithSimRun() throws Exception {
		String simConfigFilename = "src/test/resources/simbase/TestSimConfigBRS.ini";
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig(simConfigFilename);
		sim.setSimConfig(simConfig);
		sim.run();
		Buyer buyer = (Buyer) sim.getAgentManager().get("AM_BRS_1");
		assertEquals(0.1, ((TrustModelBRS) buyer.getPurchaseLogic().trustModel).quantile, 0.01);
	}

}
