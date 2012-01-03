/**
 *
 */
package trustmodels;

import org.junit.Test;

import configbase.Config;

import simbase.TestSimRunParent;
import trustmodel.TrustModelBRS;

/**
 * @author akai
 * 
 */
public class TestBRS extends TestSimRunParent {

	@Test
	public void testBRSRun() throws Exception {
		sim.run();
		TrustModelBRS brs = (TrustModelBRS) Config.config(TrustModelBRS.class,
				"src/test/resources/trustmodel/TrustModelBRSConfig.ini");
		brs.setRatingManager(sim.getRatingManager());
		for (String sellerName : sim.getAgentManager().getSellers().getAllNames()) {
			System.out.println("Seller " + sellerName + "->" + brs.calcTrust("null", sellerName));
		}
	}

}
