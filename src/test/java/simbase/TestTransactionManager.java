/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import productbase.Product;

import agentbase.Buyer;
import agentbase.Seller;

/**
 * @author akai
 * 
 */
public class TestTransactionManager extends TestSimParent {

	TransactionManager	transactionManager;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		transactionManager = new TransactionManager();
		sim.setTransactionManager(transactionManager);
	}

	@Test
	public void testAddTransaction() {
		Buyer buyer = (Buyer) sim.getAgentManager().getBuyers().get("B0");
		Seller seller = (Seller) sim.getAgentManager().getSellers().get("S0");
		Product prod = (Product) sim.getProdManager().get("1");
		/*Check for the valid case*/
		assertTrue(sim.getTransactionManager().addTransaction(buyer.getName(), seller.getName(), prod.getName(), prod.getQuantity()));
		/*Check for invalid seller's name*/
		assertTrue(!sim.getTransactionManager().addTransaction(buyer.getName(), "some dummy name", prod.getName(), prod.getQuantity()));
		/*Check for invalid quantity*/
		assertTrue(!sim.getTransactionManager().addTransaction(buyer.getName(), seller.getName(), prod.getName(), prod.getQuantity()+1));
	}

}
