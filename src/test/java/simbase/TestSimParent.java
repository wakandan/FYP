/**
 *
 */
package simbase;

import generatorbase.AgentModel;
import generatorbase.NormalDistribution;
import generatorbase.ProductModel;

import java.util.Random;

import org.junit.Before;

import productbase.ProductManager;
import agentbase.AgentManager;
import configbase.AgentConfig;
import configbase.ProductConfig;
import configbase.SimConfig;
import core.TestWithDBParent;

/**
 * @author akai
 * 
 */
public class TestSimParent extends TestWithDBParent {

	AgentConfig		aConfig;
	ProductConfig	pConfig;
	AgentModel		aModel;
	AgentManager	aManager;
	ProductModel	prodModel;
	ProductManager	prodManager;
	Sim				sim;
	SimConfig		simConfig;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		/* Generate agents */
		aConfig = new AgentConfig();
		aConfig.readConfig("src/test/resources/generatorbase/TestAgentModelConfig.ini");
		aModel = new AgentModel();
		aManager = new AgentManager();
		
		aModel.setConfig(aConfig);
		aManager.setDb(db);
		aModel.generate(aManager);

		/* Generate products */
		int prcMean = 500;
		int prcDeviation = 300*300;
		pConfig = new ProductConfig();
		pConfig.setDistribution(new NormalDistribution(prcMean, prcDeviation));
		pConfig.readConfig("src/test/resources/generatorbase/TestProductConfig.ini");
		
		prodModel = new ProductModel(pConfig);
		prodManager = new ProductManager();
		prodManager.setDb(this.db);
		prodModel.generate(prodManager);

		Bank bank = new Bank();
		
		sim = new Sim(bank);
		sim.setDb(db);
		simConfig = new SimConfig();
		simConfig.readConfig("src/test/resources/simbase/TestSimConfig.ini");
		sim.setSimConfig(simConfig);
		sim.setAgentManager(aManager);
		sim.setProdManager(prodManager);
		
	}
}
