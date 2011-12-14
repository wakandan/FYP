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
	Agent	agent;
	Product	prod;
	int		quantity;
	double	price;
	double	value;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Inventory(Agent agent, Product prod, int quantity, double price, double value) {
		super();
		this.agent = agent;
		this.prod = prod;
		this.quantity = quantity;
		this.price = price;
		this.value = value;
	}
	
	public Inventory(Agent agent, Product prod) {
		super();		
		this.agent = agent;
		this.prod = prod;
		this.quantity = prod.getQuantity();
		this.price = prod.getPriceMax();
		this.value = 0;
		
	}
}
