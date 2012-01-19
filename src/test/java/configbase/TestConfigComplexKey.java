/**
 *
 */
package configbase;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import modelbase.PurchaseLogicWishlist;

import org.junit.Test;

import agentbase.AgentMasterConfig;
import agentbase.Buyer;

/**
 * @author akai
 * 
 */
public class TestConfigComplexKey {

	@Test
	public void testProcessComplexKey() throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
		String configFileName = "src/test/resources/configbase/testAgentMasterConfigTruthful.ini";
		AgentMasterConfig agentMasterConfig = (AgentMasterConfig) Config.config(
				AgentMasterConfig.class, configFileName);
		Buyer buyer = new Buyer("buyer1");
		agentMasterConfig.configure(buyer);
		ArrayList<String> wishList = ((PurchaseLogicWishlist) buyer.getPurchaseLogic())
				.getWishList();
		int[] products = { 0, 4, 5, 7, 9 };
		for (int prodNum : products) {
			assertTrue(wishList.indexOf(prodNum + "") > -1);
		}
	}
}
