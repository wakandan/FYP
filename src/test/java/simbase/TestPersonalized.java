/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import org.junit.Test;

import trustmodel.BRS;
import trustmodel.Personalized;
import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class TestPersonalized extends TestSimRun {

	@Test
	public void testBRSRun() throws Exception {
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
		Sim sim = new Sim();
		sim.setSimConfig(simConfig);
		sim.setDb(dbConfig.setUpDb());
		sim.run();
		Personalized personalized = new Personalized();
		personalized.setEpsilon(0.4);
		personalized.setGamma(0.5);
		personalized.setForgetting(0.5);
		personalized.setTimeWindow(5);
		personalized.setRatingManager(sim.ratingManager);
		for (String agentName : sim.agentManager.getBuyers().getAllNames()) {
			System.out.println(String.format("B1, Advisor %6s -> %.3f | B2, Advisor %6s -> %.3f",
					agentName, personalized.personalized("B1", agentName, sim.agentManager
							.getBuyers().getEntitiesNames()), agentName, personalized.personalized(
							"B2", agentName, sim.agentManager.getBuyers().getEntitiesNames())));
		}
	}
}
