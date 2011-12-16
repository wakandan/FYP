/**
 *
 */
package generatorbase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import configbase.AgentConfig;

import core.TestWithDBParent;

import productbase.Product;
import productbase.ProductManager;
import simbase.Inventory;
import simbase.InventoryManager;
import simbase.Sim;

import agentbase.Agent;
import agentbase.AgentManager;
import agentbase.Buyer;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public class TestInventoryManager extends TestWithDBParent {
	InventoryManager	inventoryManager;

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
		prod2.setPriceMax(395);
		prod.setPriceMax(200);
		Inventory inventory1 = new Inventory(agent, prod);
		Inventory inventory2 = new Inventory(agent, prod2);

		inventoryManager.add(inventory1);
		inventoryManager.add(inventory2);

		st = db.prepare("SELECT COUNT(*) FROM Inventories");
		st.step();
		assertEquals(2, st.columnInt(0));

		st = db.prepare("SELECT quantity FROM Inventories WHERE agent_name=? AND prod_name=?");
		st.bind(1, agent.getName()).bind(2, prod.getName());
		st.step();
		assertEquals(prod.getQuantity(), st.columnInt(0));
		st = db.prepare("SELECT SUM(quantity) FROM Inventories");
		st.step();
		assertEquals(prod.getQuantity()+prod2.getQuantity(), st.columnInt(0));
		st = db.prepare("SELECT * FROM Inventories");
		assertEquals(2, inventoryManager.getProductsBySellerName(agent.getName()).size());

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
		for (Inventory inventory : inventoryManager.getProductsBySellerName(seller1.getName())) {
			if (inventory.getProd().getName().equalsIgnoreCase(prod1.getName()))
				found = true;
		}
		assertTrue(found);

		found = false;
		for (Inventory inventory : inventoryManager.getSellersByProductName(prod1.getName())) {
			if (inventory.getAgent().getName().equalsIgnoreCase(seller1.getName()))
				found = true;
		}
		assertTrue(found);
		assertEquals(1000, inventoryManager.getPrice(seller1.getName(), prod1.getName()), 0.1);
		assertEquals(100, inventoryManager.getInventory(seller1.getName(), prod1.getName())
				.getQuantity(), 0.1);
	}

	@Test
	public void testRestock() throws Exception {
		String sql = "INSERT INTO Agents(name, atype) VALUES(?, ?)";
		st = db.prepare(sql);
		st.bind(1, "seller1").bind(2, AgentManager.SELLER_AGENT_TYPE);
		st.step();
		st = db.prepare(sql);
		st.bind(1, "seller2").bind(2, AgentManager.SELLER_AGENT_TYPE);
		st.step();
		st = db.prepare(sql);
		st.bind(1, "seller3").bind(2, AgentManager.SELLER_AGENT_TYPE);
		st.step();

		ProductManager prodManager = new ProductManager();
		prodManager.setDb(db);
		inventoryManager.setProdManager(prodManager);

		Product prod1 = new Product("p1");
		prod1.setQuantity(1000);
		prod1.setCategory(1);
		prod1.setPriceMax(10);
		prod1.setPriceMin(1);
		prodManager.add(prod1);
		Product prod11 = new Product(prod1);
		prod11.setQuantity(500); /* Current/stock = 1/2 */

		Seller seller1 = new Seller("seller1");
		inventoryManager.add(seller1, prod11);

		Product prod2 = new Product("p2");
		prod2.setQuantity(1500);
		prod2.setCategory(2);
		prod2.setPriceMax(30);
		prod2.setPriceMin(5);
		prodManager.add(prod2);
		Product prod22 = new Product(prod2);
		prod22.setQuantity(500); /* Current/stock = 1/3 */

		Seller seller2 = new Seller("seller2");
		inventoryManager.add(seller2, prod22);

		Product prod3 = new Product("p3");
		prod3.setQuantity(700);
		prod3.setCategory(3);
		prod3.setPriceMax(50);
		prod3.setPriceMin(15);
		prodManager.add(prod3);
		Product prod33 = new Product(prod3);
		prod33.setQuantity(200); /* Current/stock = 2/7 */

		Seller seller3 = new Seller("seller3");
		inventoryManager.add(seller3, prod33);

		// st =
		// db.prepare("SELECT SUM(I.quantity)*1.0/P.quantity AS sum_quantity, prod_name, P.quantity "
		// +"FROM Inventories I, Agents A, Products P "
		// +"WHERE I.agent_name = A.name AND A.atype=? AND I.prod_name=P.name "
		// +"GROUP BY prod_name ORDER BY sum_quantity LIMIT 1");
		// st.bind(1, AgentManager.SELLER_AGENT_TYPE);
		// while (st.step()) {
		// System.out.println(st.columnDouble(0)+" | "+st.columnString(1)+" | "+st.columnInt(2));
		// }
		// ArrayList<Inventory> prods =
		// inventoryManager.getProductsBySellerName(seller2.getName());
		// int totalProdQuantity = 0;
		// for (Inventory inventory : prods) {
		// System.out.println(inventory);
		// totalProdQuantity += inventory.getQuantity();
		// }
		Product prod = inventoryManager.restock(seller2);
		assertEquals(prod.getName(), prod3.getName());
		assertTrue(prod.getQuantity()<prod3.getQuantity());
		assertTrue(inventoryManager.getInventory(seller2.getName(), prod.getName()).getProd()
				.getQuantity()>2);
	}

	@Test
	public void testUpdateInventory() throws Exception {
		Buyer buyer = new Buyer("buyer1");
		Product prod1 = new Product("p1");
		prod1.setQuantity(1000);
		prod1.setCategory(1);
		prod1.setPriceMax(10);
		prod1.setPriceMin(1);
		String sql = "INSERT INTO Products(name, quantity) VALUES(?, ?)";		
		st = db.prepare(sql);
		st.bind(1, prod1.getName()).bind(2, prod1.getQuantity());
		inventoryManager.updateInventory(buyer, prod1);
		assertEquals(1000, inventoryManager.getInventory(buyer, prod1).getQuantity());
		prod1.setQuantity(500);
		inventoryManager.updateInventory(buyer, prod1);
		assertEquals(500, inventoryManager.getInventory(buyer, prod1).getQuantity());
		prod1.setQuantity(0);
		inventoryManager.updateInventory(buyer, prod1);
		assertTrue(inventoryManager.getInventory(buyer, prod1)==null);
		st = db.prepare("SELECT * FROM Inventories WHERE agent_name=? AND prod_name=?");
		st.bind(1, buyer.getName()).bind(2, prod1.getName());
		st.step();
		assertTrue(!st.hasRow());
	}
}
