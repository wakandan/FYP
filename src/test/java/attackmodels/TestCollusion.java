/**
 *
 */
package attackmodels;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.Config;

import simbase.Execution;
import simbase.TestSimRunParent;

/**
 * @author akai
 * 
 */
public class TestCollusion extends TestSimRunParent {

	@Test
	public void testSameBuyer() throws Exception {
		sim.run();
		SQLiteConnection db = sim.getTransactionManager().getDb();
		HashMap<String, String> configValues = Config
				.readConfigFile("src/test/resources/configbase/testAgentMasterConfigCollusion.ini");
		String collusionAgentPrefix = configValues.get("masterName");
		String colludedSellerName = configValues.get("targetSeller");
		int colludedRating = Integer.parseInt(configValues.get("targetRating"));
		SQLiteStatement st = db
				.prepare("SELECT DISTINCT seller_name FROM Executions WHERE buyer_name LIKE ?");
		st.bind(1, "%" + collusionAgentPrefix + "%");
		int count = 0;
		String sellerName = null;
		while (st.step()) {
			count++;
			sellerName = st.columnString(0);
		}
		if (count > 0) {
			assertEquals(1, count);
			assertEquals(colludedSellerName, sellerName);
			st = db.prepare("SELECT AVG(rating) FROM Executions WHERE buyer_name LIKE ? AND status=? GROUP BY seller_name");
			st.bind(1, "%" + collusionAgentPrefix + "%").bind(2, Execution.STATUS_SUCCESS);
			count = 0;
			double avgRating = -1;
			while (st.step()) {
				count++;
				avgRating = st.columnDouble(0);
			}
			assertEquals(1, count);
			assertEquals(colludedRating, avgRating, 0.1);
			st.dispose();
		} else {
			st = db.prepare("SELECT status FROM Executions WHERE buyer_name LIKE ? AND seller_name=?");
			st.bind(1, "%" + collusionAgentPrefix + "%").bind(2, colludedSellerName);
			st.step();
			assertEquals(st.columnInt(0), Execution.STATUS_FAILED);
		}
	}
}
