/**
 *
 */
package productbase;

import agentbase.Agent;
import agentbase.Seller;

import com.almworks.sqlite4java.SQLiteStatement;

import modelbase.Entity;
import generatorbase.EntityManager;

/**
 * @author akai
 * 
 */
public class InventoryManager extends EntityManager {

	public void add(Entity e) throws Exception {
		Inventory inventory = (Inventory) e;
		super.add(e);
		String sql = "INSERT INTO Inventories(agent_name, prod_name, quantity) VALUES(?, ?, ?)";
		SQLiteStatement st = db.prepare(sql);
		st.bind(1, inventory.getAgentName()).bind(2, inventory.getProdName()).bind(3, inventory.getQuantity());
		st.step();
	}

	public void add(Agent a, Product p) throws Exception {
		a.addProduct(p);
		add(new Inventory(a, p));
	}
}
