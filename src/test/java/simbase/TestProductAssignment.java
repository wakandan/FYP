/**
 *
 */
package simbase;

import generatorbase.AgentModel;
import generatorbase.NormalDistribution;
import generatorbase.ProductModel;

import org.junit.Before;
import org.junit.Test;

import productbase.ProductManager;
import agentbase.AgentManager;

import com.almworks.sqlite4java.SQLiteStatement;

import configbase.AgentConfig;
import configbase.ProductConfig;
import core.TestWithDBParent;

/**
 * @author akai
 * 
 */
public class TestProductAssignment extends TestWithDBParent {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		SQLiteStatement st;
		st = db.prepare(readDDL("src/main/resources/sql/Products.ddl"));
		st.step();
		st = db.prepare(readDDL("src/main/resources/sql/Agents.ddl"));
		st.step();
	}

	@Test
	public void testProductAssignment() throws Exception {
		/* Generate agents */
		AgentConfig aConfig = new AgentConfig();
		aConfig.readConfig("src/test/resources/generatorbase/TestAgentModelConfig.ini");
		AgentModel aModel = new AgentModel();
		AgentManager aManager = new AgentManager();
		aModel.setConfig(aConfig);
		aManager.setDb(db);
		aModel.generate(aManager);

		/* Generate products */
		int prcMean = 500;
		int prcDeviation = 300*300;
		ProductConfig config = new ProductConfig();
		config.setDistribution(new NormalDistribution(prcMean, prcDeviation));
		config.readConfig("src/test/resources/generatorbase/TestProductConfig.ini");
		ProductModel prodModel = new ProductModel(config);
		ProductManager prodManager = new ProductManager();
		prodManager.setDb(this.db);
		prodModel.generate(prodManager);

		Sim sim = new Sim();
		sim.setAgentManager(aManager);
		sim.setProdManager(prodManager);
		sim.setDb(db);
		sim.assignProducts();
	}

}
