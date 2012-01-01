/**
 *
 */
package configbase;

import static org.junit.Assert.*;

import generatorbase.NormalDistribution;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import simbase.Sim;

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
	public void testReadProductConfigFile() throws IOException {
		String filename = "src/test/resources/configbase/testProductConfig.ini";
		ProductConfig pc = new ProductConfig();
		pc.readConfig(filename);
		assertEquals(10000, pc.getNumProducts());
		assertEquals(1000, pc.getPriceMax());
		assertEquals(1, pc.getPriceMin());
		assertEquals(15, pc.getNumCategories());
	}

	@Test
	public void testReadAgentConfigFile() throws IOException {
		String filename = "src/test/resources/configbase/testAgentConfig.ini";
		AgentConfig ac = new AgentConfig();
		ac.readConfig(filename);
		assertEquals(100, ac.getBuyerNum());
		assertEquals(100, ac.getSellerNum());
		assertEquals(100.54, ac.getInitialBalance(), 0.1);
	}

	@Test
	public void testSimConfigRead() throws IOException {
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
		Sim sim = new Sim();
		sim.setSimConfig(simConfig);
		assertTrue(sim.getAgentManager().getConfig() != null);
		assertTrue(sim.getProdManager().getConfig() != null);
		assertTrue(((ProductConfig) sim.getProdManager().getConfig()).getDistribution() instanceof NormalDistribution);
		assertEquals(100, ((AgentConfig) sim.getAgentManager().getConfig()).getBuyerNum());
	}

	@Test
	public void testDistributionConfigRead() throws IOException {
		DistributionConfig distributionConfig = new DistributionConfig();
		distributionConfig
				.readConfig("src/test/resources/configbase/testNormalDistributionConfig.ini");
		assertEquals(100, distributionConfig.mean, 0.1);
		assertEquals(2500, distributionConfig.variance, 0.1);
	}

	@Test
	public void testSimConfig() throws IOException {
		SimConfig simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
		assertEquals(3, simConfig.getAgentMasters().keySet().size());
	}

}
