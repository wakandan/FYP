/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import configbase.DBConfig;
import configbase.SimConfig;

import simbase.Sim;

/**
 * @author akai
 * 
 */
public class TestSimRun {
	DBConfig dbConfig;
	@Before
	public void setUp() {
		dbConfig = new DBConfig(null);
		dbConfig.addDdlFile("src/main/resources/sql/Products.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Agents.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Inventories.ddl");		
	}

	@Test
	public void testRunSim() throws Exception {
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
		Sim sim = new Sim();
		sim.setSimConfig(simConfig);
		sim.setDb(dbConfig.setUpDb());
		sim.run();
	}
	
}
