/**
 *
 */
package trustmodels;

import org.junit.Test;

import simbase.TestSimRunParent;
import trustmodel.TRAVOS;

/**
 * @author akai
 * 
 */
public class TestTRAVOS extends TestSimRunParent {
	@Test
	public void testTRAVOS() throws Exception {
		sim.run();
		TRAVOS travos = new TRAVOS();
		travos.setNumBins(5);
		travos.setRatingManager(sim.getRatingManager());
		travos.setErrorThreshold(0.2);
		travos.setMinAccuracyValue(0.5);
		for (String agentName : sim.getAgentManager().getSellers().getAllNames()) {
			System.out.println(String.format("B1, Seller %6s -> %.3f | B2, Seller %6s -> %.3f",
					agentName, travos.travos("B1", agentName), agentName,
					travos.travos("B2", agentName)));
		}
	}
}
