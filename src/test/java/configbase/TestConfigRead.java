/**
 *
 */ 
package configbase;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

/**
 * @author akai
 *
 */
public class TestConfigRead {

	@Test
	public void testReadConfigFile() throws IOException {
		String filename = "src/test/resources/configbase/testConfig.ini";
		HashMap<String, String> configData = Config.readConfigFile(filename);
		assertEquals("100", configData.get("buyerNum"));
		assertEquals("1000000", configData.get("distributionDeviation"));
		assertEquals("NormalDistribution", configData.get("distribution"));		
	}
	
	@Test
	public void testReadProductConfigFile() throws IOException{
		String filename = "src/test/resources/configbase/testProductConfig.ini";
		ProductConfig pc = new ProductConfig();
		pc.readConfig(filename);
		assertEquals(10000, pc.getNumProducts());
		assertEquals(1000, pc.getPriceMax());
		assertEquals(1, pc.getPriceMin());
		assertEquals(15, pc.getNumCategories());
	}

	@Test
	public void testReadAgentConfigFile() throws IOException{
		String filename = "src/test/resources/configbase/testAgentConfig.ini";
		AgentConfig ac = new AgentConfig();
		ac.readConfig(filename);
		assertEquals(100, ac.getBuyerNum());
		assertEquals(100, ac.getSellerNum());
		assertEquals(100.54, ac.getInitialBalance(), 0.1);
	}
	
}
