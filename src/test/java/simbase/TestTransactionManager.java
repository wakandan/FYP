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
		Product prod = sim.inventoryManager
				.getProductsBySellerName(seller.getName())
				.get(sim.inventoryManager.getProductsBySellerName(seller.getName()).keySet()
						.toArray()[0]).getProd();
		sim.bank.setBalance(buyer.getName(), 10000);
		Execution execution = sim.transactionManager.addTransaction(buyer, seller, prod,
				prod.getQuantity(), 100);
		assertTrue(execution==null);
		/* Check for the valid case */
		// assertTrue();
		/* Check for invalid seller's name */
		assertTrue(sim.getTransactionManager().addTransaction(buyer, seller, prod,
				prod.getQuantity()+2, 100)!=null);
	}

	@Test
	public void testProcessTransaction() throws Exception {
		Buyer buyer0 = (Buyer) sim.getAgentManager().getBuyers().get("B0");
		assertTrue(sim.getBalance(buyer0.getName())==0);
		Buyer buyer1 = (Buyer) sim.getAgentManager().getBuyers().get("B1");
		Seller seller = (Seller) sim.getAgentManager().getSellers().get("S0");
		transactionManager.setSim(sim);
		transactionManager.setDb(sim.db);
		Product prod = sim.inventoryManager
				.getProductsBySellerName(seller.getName())
				.get(sim.inventoryManager.getProductsBySellerName(seller.getName()).keySet()
						.toArray()[0]).getProd();
		sim.bank.setBalance(buyer0.getName(), 10000);
		sim.bank.setBalance(buyer1.getName(), 10000);
		if (prod.getQuantity()>=2) {
			sim.getTransactionManager().addTransaction(buyer0, seller, prod, prod.getQuantity()/2,
					prod.getPriceMin());
			sim.getTransactionManager().addTransaction(buyer1, seller, prod,
					prod.getQuantity()-prod.getQuantity()/2, prod.getPriceMin());
			transactionManager.processTransactions();
			if (seller.getLogicModel() instanceof HonestAutoSellerLogicModel) {
				assertTrue(sim.getBalance(buyer0)>0);
				assertTrue(buyer0.getProduct(prod.getName())!=null);
				assertTrue(sim.getBalance(buyer1)>0);
			} else {
				/*
				 * Incase of an auto dishonest seller, he will refuse to sell,
				 * so the balance remains the same
				 */
				assertEquals(10000, sim.getBalance(buyer0), 0.1);
				assertTrue(buyer0.getProduct(prod.getName())==null);
				assertEquals(10000, sim.getBalance(buyer1), 0.1);
			}
			st = sim.db
					.prepare("SELECT COUNT(*) FROM Executions WHERE buyer_name=? AND seller_name=?");
			st.bind(1, buyer0.getName()).bind(2, seller.getName());
			st.step();
			assertTrue(st.columnInt(0)>0);
		}
	}
}
