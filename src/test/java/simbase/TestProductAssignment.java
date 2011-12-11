/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import java.util.Random;

import generatorbase.AgentModel;
import generatorbase.NormalDistribution;
import generatorbase.ProductModel;

import org.junit.Before;
import org.junit.Test;

import productbase.Product;
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
public class TestProductAssignment extends TestSimParent {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testProductAssignment() throws Exception {		
		sim.assignProducts();
		/*
		 * Agent and products generate in a same simulation run must have the
		 * same session id
		 */
		assertEquals(sim.getAgentManager().getSessionId(), sim.getProdManager().getSessionId());

		st = db.prepare("SELECT SUM(quantity) FROM Inventories");
		st.step();
		assertEquals(sim.getQuantityAssigned(), st.columnInt(0));
		Agent agent = (Agent) agentManager.getSellers().get("S0");
		Product product = agent.getProduct(0);

		st = db.prepare("SELECT quantity FROM Inventories WHERE agent_name=? AND prod_name=?");
		st.bind(1, agent.getName()).bind(2, product.getName());
		st.step();
		assertEquals(product.getQuantity(), st.columnInt(0));

		st = db.prepare("SELECT COUNT(agent_name) FROM Inventories");
		st.step();
		assertEquals(sim.getNumSellerAssigned(), st.columnInt(0));
	}

}
