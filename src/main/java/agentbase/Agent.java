package agentbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import modelbase.Entity;
import modelbase.LogicModel;
import productbase.Product;

public abstract class Agent extends Entity {
	double						balance;
	HashMap<String, Product>	inventory;
	LogicModel					logicModel;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @param name
	 */
	public Agent(String name) {
		super(name);
		inventory = new HashMap<String, Product>();
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
		this.inventory.put(prod.getName(), prod);
	}

	public Product getProduct(String prodName) {
		return (Product) this.inventory.get(prodName);
	}

	public int getInventorySize() {
		return inventory.size();
	}

	/**
	 * @param prodName
	 */
	public void removeProduct(String prodName) {
		inventory.remove(prodName);
	}
	
	public Set<String> getProductNames() {
		return inventory.keySet();
	}

}
