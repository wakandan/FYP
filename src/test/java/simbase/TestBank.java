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
public class TestBank extends TestSimParent {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testCreditPerturn() throws SQLiteException {
		Buyer buyer1 = (Buyer) agentManager.get(0);
		double oldBalance = sim.getBalance(buyer1.getName());
		sim.advanceTime();
		assertEquals(simConfig.getCreditPerTurn(), sim.getBalance(buyer1.getName())-oldBalance, 0.1);
		st = db.prepare("SELECT balance FROM Agents WHERE name=?");
		st.bind(1, buyer1.getName());
		st.step();
		assertEquals(simConfig.getCreditPerTurn(), st.columnDouble(0)-oldBalance, 0.1);
	}

}
