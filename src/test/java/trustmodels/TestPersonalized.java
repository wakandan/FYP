/**
 *
 */
package trustmodels;

import static org.junit.Assert.*;

import org.junit.Test;

import simbase.Sim;
import simbase.TestSimRun;
import simbase.TestSimRunParent;
import trustmodel.TrustModelBRS;
import trustmodel.TrustModelPersonalized;
import configbase.Config;
import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class TestPersonalized extends TestSimRunParent {

	// @Test
	public void testBRSRun() throws Exception {
		sim.run();
		TrustModelPersonalized personalized = (TrustModelPersonalized) Config.config(
				TrustModelPersonalized.class,
				"src/test/resources/trustmodel/TrustModelPersonalizedConfig.ini");

		personalized.setRatingManager(sim.getRatingManager());
		for (String agentName : sim.getAgentManager().getSellers().getAllNames()) {
			System.out.println(String.format("B1, Advisor %6s -> %.3f | B2, Advisor %6s -> %.3f",
					agentName, personalized.calcTrust("B1", agentName), agentName,
					personalized.calcTrust("B2", agentName)));
		}
	}
}
