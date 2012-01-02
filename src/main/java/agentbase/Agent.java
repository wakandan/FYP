package agentbase;

import java.util.HashMap;
import java.util.Set;

import modelbase.AgentLogicModel;
import modelbase.Entity;
import modelbase.IdentityLogic;
import modelbase.LogicModel;
import productbase.Product;
import simbase.InventoryManager;
import simbase.Sim;

public abstract class Agent extends Entity {
	double						balance;
	HashMap<String, Product>	inventory;
	AgentLogicModel				logicModel;
	InventoryManager			inventoryManager;		/*
														 * To query one's
														 * inventory & product
														 * value
														 */
	boolean						isIdentityChangable;
	IdentityLogic				identityLogic;

	public boolean requestNewIdentity() {
		if (isIdentityChangable) {
			return identityLogic.requestNewIdentity();
		}
		return false;
	}

	public IdentityLogic getIdentityLogic() {
		return identityLogic;
	}

	public void setIdentityLogic(IdentityLogic identityLogic) {
		this.identityLogic = identityLogic;
	}

	public boolean isIdentityChangable() {
		return isIdentityChangable;
	}

	public void setIdentityChangable(boolean isIdentityChangable) {
		this.isIdentityChangable = isIdentityChangable;
	}

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
		isIdentityChangable = false;
	}

	public void registerTransaction(Action action) {

	}

	public LogicModel getLogicModel() {
		return logicModel;
	}

	public void setLogicModel(AgentLogicModel logicModel) {
		this.logicModel = logicModel;
		this.logicModel.setAgent(this);
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

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

}
