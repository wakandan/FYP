/**
 *
 */ 
package generatorbase;

import static org.junit.Assert.*;

import org.junit.Test;

import productbase.Product;
import agentbase.Agent;
import agentbase.Seller;

/**
 * @author akai
 *
 */
public class TestAgent {

	@Test
	public void testAddRemoveProduct() {
		String agentName = "Agent1";
		String prodName = "Product1";		
		String prodName2 = "Product2"; 
		Agent agent = new Seller(agentName);
		Product prod = new Product(prodName);
		Product prod2 = new Product(prodName2);
		agent.addProduct(prod);
		assertEquals(1, agent.getInventorySize());
		agent.addProduct(prod2);
		assertEquals(2, agent.getInventorySize());
		agent.removeProduct(0);
		assertEquals(prodName2, agent.getProduct(0).getName());
		agent.removeProduct(0);
	}

}
