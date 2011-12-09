/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agentbase.Buyer;

import com.almworks.sqlite4java.SQLiteException;

/**
 * @author akai
 * 
 */
public class TestSim extends TestSimParent {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testCreditPerturn() throws SQLiteException {
		Buyer buyer1 = (Buyer) aManager.get(0);
		sim.advanceTime();
		double oldBalance = buyer1.getBalance();
		sim.advanceTime();
		assertEquals(simConfig.getCreditPerTurn(), buyer1.getBalance()-oldBalance, 0.1);
		st = db.prepare("SELECT balance FROM Agents WHERE name=?");
		st.bind(1, buyer1.getName());
		st.step();
		assertEquals(simConfig.getCreditPerTurn(), st.columnDouble(0)-oldBalance, 0.1);
	}

}
