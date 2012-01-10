/**
 *
 */
package trustmodels;

import org.junit.Test;

import configbase.Config;

import simbase.TestSimRunParent;
import trustmodel.TrustModelTRAVOS;

/**
 * @author akai
 * 
 */
public class TestTRAVOS extends TestSimRunParent {
	@Test
	public void testTRAVOS() throws Exception {
		sim.run();
		TrustModelTRAVOS travos = (TrustModelTRAVOS) Config.config(TrustModelTRAVOS.class,
				"src/test/resources/trustmodel/TrustModelTRAVOSConfig.ini");
		travos.setRatingManager(sim.getRatingManager());
		for (String agentName : sim.getAgentManager().getSellers().getAllNames()) {
			System.out.println(String.format("B1, Seller %6s -> %.3f | B2, Seller %6s -> %.3f",
					agentName, travos.calcTrust("B1", agentName), agentName,
					travos.calcTrust("B2", agentName)));
		}
	}
}
