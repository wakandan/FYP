/**
 *
 */
package simbase;

import generatorbase.AgentModel;
import generatorbase.NormalDistribution;
import generatorbase.ProductModel;

import org.junit.Before;

import productbase.ProductManager;
import configbase.AgentConfig;
import configbase.DistributionConfig;
import configbase.ProductConfig;
import configbase.SimConfig;
import configbase.AgentConfigSimple;
import core.TestWithDBParent;

/**
 * @author akai
 * 
 */
public class TestSimParent extends TestWithDBParent {

	AgentConfig		agentConfig;
	ProductConfig	prodConfig;
	AgentModel		agentModel;
	AgentManager	agentManager;
	ProductModel	prodModel;
	ProductManager	prodManager;
	protected Sim	sim;
	SimConfig		simConfig;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		/* Generate agents */
		agentConfig = new AgentConfigSimple();
		agentConfig.readConfig("src/test/resources/generatorbase/TestAgentModelConfig.ini");
		agentModel = new AgentModel();
		agentManager = new AgentManager();

		agentModel.setConfig(agentConfig);
		agentManager.setDb(db);
		agentModel.generate(agentManager);

		/* Generate products */
		int prcMean = 500;
		int prcDeviation = 300 * 300;
		prodConfig = new ProductConfig();
		prodConfig.setDistribution(new NormalDistribution(new DistributionConfig(prcMean,
				prcDeviation)));
		prodConfig.readConfig("src/test/resources/generatorbase/TestProductConfig.ini");

		prodModel = new ProductModel(prodConfig);
		prodManager = new ProductManager();
		prodManager.setDb(this.db);
		prodModel.generate(prodManager);

		Bank bank = new Bank();

		sim = new Sim(bank);
		sim.setDb(db);
		simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/TestSimConfig.ini");
		sim.setSimConfig(simConfig);
		sim.setAgentManager(agentManager);
		sim.setProdManager(prodManager);

		sim.assignProducts();
	}
}
