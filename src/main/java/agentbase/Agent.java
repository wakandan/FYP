package agentbase;

import java.util.ArrayList;

import modelbase.Entity;
import modelbase.LogicModel;
import productbase.Product;

public abstract class Agent extends Entity {
	int					balance;
	ArrayList<Product>	inventory;
	LogicModel			logicModel;

	/**
	 * @param name
	 */
	public Agent(String name) {
		super(name);
		inventory = new ArrayList<Product>();		
	}

	public void registerTransaction(Action action) {

	}

	public float calcRating() {
		return 0;
	}

	public void changeBehavior() {

	}

	public void leave() {

	}

	public void join() {

	}

	public LogicModel getLogicModel() {
		return logicModel;
	}

	public void setLogicModel(LogicModel logicModel) {
		this.logicModel = logicModel;
	}
	
	public void addProduct(Product prod) {
		this.inventory.add(prod);
	}
	
	public Product getProduct(int i) {
		return (Product)this.inventory.get(i);
	}
	
	public int getInventorySize() {
		return inventory.size();
	}
	
	public ArrayList getInventory() {
		return inventory;
	}
	
	public void removeProduct(int i) {
		this.inventory.remove(i);
	}
}
