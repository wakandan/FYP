/**
 *
 */ 
package generatorbase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.TestWithDBParent;

import productbase.InventoryManager;
import productbase.Product;

import agentbase.Agent;
import agentbase.Seller;

/**
 * @author akai
 *
 */
public class TestInventoryManager extends TestWithDBParent{
	InventoryManager inventoryManager;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		inventoryManager = new InventoryManager();
		inventoryManager.setDb(db);
	}
	
	@Test
	public void testAddInventory() throws Exception {
		String agentName = "Agent1";
		String prodName = "Product1";
		String prodName2 = "Product2";
		Agent agent = new Seller(agentName);
		Product prod = new Product(prodName);
		Product prod2 = new Product(prodName2);
		prod.setQuantity(10000);
		prod2.setQuantity(2000);
		inventoryManager.add(agent, prod);
		inventoryManager.add(agent, prod2);
		
		st = db.prepare("SELECT quantity FROM Inventories WHERE agent_name=? AND prod_name=?");
		st.bind(1, agent.getName()).bind(2, prod.getName());
		st.step();
		assertEquals(prod.getQuantity(), st.columnInt(0));
		st = db.prepare("SELECT SUM(quantity) FROM Inventories");		
		st.step();
		assertEquals(prod.getQuantity()+prod2.getQuantity(), st.columnInt(0));
	}

}
