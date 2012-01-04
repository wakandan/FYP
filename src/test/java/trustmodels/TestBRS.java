/**
 *
 */
package trustmodels;

import org.junit.Test;

import simbase.TestSimRunParent;
import trustmodel.BRS;

/**
 * @author akai
 * 
 */
public class TestBRS extends TestSimRunParent {

	@Test
	public void testBRSRun() throws Exception {
		sim.run();
		BRS brs = new BRS();
		brs.setQuantile(0.05);
		brs.setRatingManager(sim.getRatingManager());
		for (String agentName : sim.getAgentManager().getSellers().getAllNames()) {
			System.out.println("Seller " + agentName + "->"
					+ brs.brs(agentName, sim.getAgentManager().getBuyers().getEntitiesNames()));
		}
	}

}
