/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.almworks.sqlite4java.SQLiteException;

import configbase.DBConfig;
import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class TestSimRunParent {

	protected DBConfig	dbConfig;
	protected Sim		sim;
	protected SimConfig	simConfig;

	@Before
	public void setUp() throws IOException, SQLiteException {
		dbConfig = new DBConfig(null);
		dbConfig.addDdlFile("src/main/resources/sql/Products.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Agents.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Inventories.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Executions.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Identities.ddl");
		simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
		sim = new Sim();
		sim.setSimConfig(simConfig);
		sim.setDb(dbConfig.setUpDb());
	}
}
