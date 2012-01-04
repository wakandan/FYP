/**
 *
 */
package trustmodels;

import static org.junit.Assert.*;

import org.junit.Test;

import simbase.Sim;
import simbase.TestSimRun;
import simbase.TestSimRunParent;
import trustmodel.BRS;
import trustmodel.Personalized;
import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class TestPersonalized extends TestSimRunParent {

//	@Test
//	public void testBRSRun() throws Exception {
//		sim.run();
//		Personalized personalized = new Personalized();
//		personalized.setEpsilon(0.4);
//		personalized.setGamma(0.5);
//		personalized.setForgetting(0.5);
//		personalized.setTimeWindow(5);
//		personalized.setRatingManager(sim.getRatingManager());
//		for (String agentName : sim.getAgentManager().getSellers().getAllNames()) {
//			System.out.println(String.format("B1, Advisor %6s -> %.3f | B2, Advisor %6s -> %.3f",
//					agentName, personalized.personalized("B1", agentName), agentName,
//					personalized.personalized("B2", agentName)));
//		}
//	}
}
