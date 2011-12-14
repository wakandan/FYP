/**
 *
 */ 
package generatorbase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.TestWithDBParent;

import productbase.Inventory;
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
	
	@Test
	public void testInventoryOperations() throws Exception {
		String agentName1 = "Agent1";
		String prodName1 = "Product1";
		Agent seller1 = new Seller(agentName1);
		Product prod1 = new Product(prodName1);
		prod1.setPriceMax(1000);
		prod1.setQuantity(100);
		inventoryManager.add(seller1, prod1);
		boolean found = false;
		for(Inventory inventory: inventoryManager.getProductsBySellerName(seller1.getName())) {
			if(inventory.getProd().getName().equalsIgnoreCase(prod1.getName()))
				found = true;
		}
		assertTrue(found);
		
		found = false;
		for(Inventory inventory: inventoryManager.getSellersByProductName(prod1.getName())){
			if(inventory.getAgent().getName().equalsIgnoreCase(seller1.getName()))
				found = true;
		}
		assertTrue(found);
		assertEquals(1000, inventoryManager.getPrice(seller1.getName(), prod1.getName()), 0.1);
		assertEquals(100, inventoryManager.getInventory(seller1.getName(), prod1.getName()).getQuantity(), 0.1);
	}
	
}
