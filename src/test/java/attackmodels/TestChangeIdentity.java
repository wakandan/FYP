/**
 *
 */
package attackmodels;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simbase.TestSimRunParent;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author akai
 * 
 */
public class TestChangeIdentity extends TestSimRunParent {

	@Before
	public void setUp() throws IOException, SQLiteException {
		super.setUp();
		simConfig.readConfig("src/test/resources/simbase/SimConfigForIdentityChange.ini");
	}

	@Test
	public void test() throws Exception {
		sim.run();
		SQLiteStatement st = sim.getDb().prepare("SELECT * FROM Executions WHERE buyer_name LIKE ?");
		st.bind(1, "AMWhitewash%");
		assertTrue(!st.step());
	}

}
