/**
 *
 */
package core;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import simbase.Sim;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class TestMyDB {
	String	dbFilename	= "D:/doc/workspace/sim/db.sqlite";
	String	dbFilename1	= "D:/doc/workspace/sim/db1.sqlite";
	String	dbInitDir	= "D:/doc/workspace/sim/src/main/resources/sql";

	@Test
	public void testDBSetup() throws SQLiteException, IOException {

		// MyDB mydb = new MyDB(dbFilename, dbInitDir);
		MyDB mydb = new MyDB(dbFilename1, dbInitDir);
		try {
			SQLiteStatement st = mydb.conn.prepare("SELECT * FROM Products LIMIT 1 ");
		} catch (SQLiteException e) {
			e.printStackTrace();
			fail("MyDB was not implemented correctly");
		}finally {
			mydb.conn.dispose();
		}		
		
	}

	@Test
	public void testDBWithSimRun() throws Exception {
		MyDB mydb = new MyDB(dbFilename1, dbInitDir);
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
		Sim sim = new Sim();
		sim.setSimConfig(simConfig);
		sim.setDb(mydb.conn);
		sim.run();
	}

	@After
	public void tearDown() {
		System.out.println("Tear down");
		File file = new File(dbFilename1);
		System.out.println(file.delete());
	}
}