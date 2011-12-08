/**
 *
 */ 
package simbase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import generatorbase.AgentModel;
import generatorbase.EntityManager;
import generatorbase.NormalDistribution;
import generatorbase.ProductModel;

import org.junit.Before;
import org.junit.Test;

import productbase.ProductManager;

import agentbase.Agent;
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
		/*Generate agents*/
		AgentConfig aConfig = new AgentConfig();
		int buyerNum = 100;
		int sellerNum = 100;
		SQLiteStatement st;
		aConfig.setBuyerNum(buyerNum);
		aConfig.setSellerNum(sellerNum);
		AgentModel aModel = new AgentModel();
		AgentManager aManager = new AgentManager();
		aManager.setDb(db);
		aModel.setConfig(aConfig);
		aModel.generate(aManager);
		
		/*Generate products*/
		int numTypes = 50;
		int numProds = 10000;
		int prcMax = 1000;
		int prcMin = 1;
		int prcMean = 500;
		int numCategories = 15;
		int prcDeviation = 300*300;
		ProductConfig config = new ProductConfig();
		config.setDistribution(new NormalDistribution(prcMean, prcDeviation));
		config.setNumProducts(numProds);
		config.setNumTypes(numTypes);
		config.setPriceMax(prcMax);
		config.setPriceMin(prcMin);
		config.setNumCategories(numCategories);
		ProductModel prodModel = new ProductModel(config);
		ProductManager prodManager = new ProductManager();
		prodManager.setDb(this.db);
		prodManager.setConfig(config);
		prodModel.generate(prodManager);
		
		Sim sim = new Sim();
		sim.setAgentManager(aManager);
		sim.setProdManager(prodManager);
		sim.setDb(db);
		sim.assignProducts();
	}

}
