package agentbase;

import modelbase.Entity;
import modelbase.LogicModel;
import productbase.Product;

public abstract class Agent extends Entity{
	int balance;
	Product inventory[];
	LogicModel logicModel;
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
	
	
	
}
