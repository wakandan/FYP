/**
 *
 */
package productbase;

import agentbase.Agent;
import agentbase.Seller;
import modelbase.Entity;

/**
 * 
 * @author akai
 * @note Inventory is a class to represent the relationship between an agent &
 *       products he is in possess. It corresponds to database table Inventories
 *       in the database.
 */
public class Inventory extends Entity {
	String	agentName;
	String	prodName;
	int		quantity;
	double	price;

	public double getPrice() {
		return price;
	}

	/**
	 * @param name
	 *            : the timestampt when the relationship is made
	 */
	public Inventory(String name) {
		super(name);
	}

	public Inventory(Agent a, Product p) {
		super(""); /* Dummy */
		this.agentName = a.getName();
		this.prodName = p.getName();
		this.quantity = p.getQuantity();
		this.price = p.getPriceMax();
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
