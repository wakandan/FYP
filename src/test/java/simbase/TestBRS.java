/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import java.io.IOException;

import modelbase.BRS;

import org.junit.Test;

import com.almworks.sqlite4java.SQLiteException;

import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class TestBRS extends TestSimRun {

	@Test
	public void testBRSRun() throws Exception {
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
		Sim sim = new Sim();
		sim.setSimConfig(simConfig);
		sim.setDb(dbConfig.setUpDb());
		sim.run();
		BRS brs = new BRS();
		brs.setQuantile(0.01);
		brs.setRatingManager(sim.ratingManager);
		for (String agentName : sim.agentManager.getSellers().getAllNames()) {
			brs.brs(agentName, sim.agentManager.getBuyers().getEntitiesNames());
		}
	}

}
