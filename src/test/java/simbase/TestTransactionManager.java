/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import modelbase.HonestAutoSellerLogicModel;

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
		Product prod = seller.getProduct((String) seller.getProductNames().toArray()[0]);
		/* Check for the valid case */
		assertTrue(sim.getTransactionManager().addTransaction(buyer.getName(), seller.getName(), prod.getName(), prod.getQuantity(), 100));
		/* Check for invalid seller's name */
		assertTrue(!sim.getTransactionManager().addTransaction(buyer.getName(), "some dummy name", prod.getName(), prod.getQuantity(), 100));
		/* Check for invalid quantity */
		assertTrue(!sim.getTransactionManager().addTransaction(buyer.getName(), seller.getName(), prod.getName(), prod.getQuantity()+2, 100));
	}

	@Test
	public void testProcessTransaction() {
		Buyer buyer0 = (Buyer) sim.getAgentManager().getBuyers().get("B0");
		assertTrue(sim.getBalance(buyer0.getName())==0);
		Buyer buyer1 = (Buyer) sim.getAgentManager().getBuyers().get("B1");
		Seller seller = (Seller) sim.getAgentManager().getSellers().get("S0");
		Product prod = seller.getProduct((String) seller.getProductNames().toArray()[0]);
		if (prod.getQuantity()>=2) {
			sim.getTransactionManager().addTransaction(buyer0.getName(), seller.getName(), prod.getName(), prod.getQuantity()/2, prod.getPriceMin());
			sim.getTransactionManager().addTransaction(buyer1.getName(), seller.getName(), prod.getName(), prod.getQuantity()/2, prod.getPriceMin());
			transactionManager.processTransactions();
			System.out.println("Buyer's balance: "+sim.getBalance(buyer0.getName()));
			System.out.println("Product's value: "+sim.getValue(prod.getName()));
			if (seller.getLogicModel() instanceof HonestAutoSellerLogicModel) {
				assertTrue(sim.getBalance(buyer0.getName())>0);
				assertTrue(buyer0.getProduct(prod.getName())!=null);
				assertTrue(sim.getBalance(buyer1.getName())>0);
			} else {
				assertTrue(sim.getBalance(buyer0.getName())==0);
				assertTrue(buyer0.getProduct(prod.getName())==null);
				assertTrue(sim.getBalance(buyer1.getName())==0);
			}
		}
	}
}
