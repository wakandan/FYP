/**
 *
 */
package simbase;

import generatorbase.AgentModel;
import generatorbase.EntityManager;
import generatorbase.ProductModel;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import productbase.Inventory;
import productbase.InventoryManager;
import productbase.Product;
import productbase.ProductManager;
import agentbase.AgentManager;
import agentbase.Seller;

import com.almworks.sqlite4java.SQLiteConnection;

import configbase.ProductConfig;
import configbase.SimConfig;

/**
 * @author akai
 * 
 */
public class Sim {
	AgentManager			agentManager;
	AgentModel				agentModel;
	SQLiteConnection		db;
	SimConfig				simConfig;
	int						timeStep;
	Scheduler				scheduler;
	ProductManager			prodManager;
	ProductModel			prodModel;
	String					sessionId;
	InventoryManager		inventoryManager;
	int						quantityAssigned;
	int						numSellerAssigned;
	private static Logger	logger	= Logger.getLogger("Sim");

	public int getNumSellerAssigned() {
		return numSellerAssigned;
	}

	public AgentManager getAgentManager() {
		return agentManager;
	}

	public int getQuantityAssigned() {
		return quantityAssigned;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setAgentManager(AgentManager agentManager) {
		this.agentManager = agentManager;
		this.agentManager.setSessionId(this.sessionId);
	}

	public SimConfig getSimConfig() {
		return simConfig;
	}

	public void setSimConfig(SimConfig simConfig) {
		this.simConfig = simConfig;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public ProductManager getProdManager() {
		return prodManager;
	}

	public void setProdManager(ProductManager prodManager) {
		this.prodManager = prodManager;
		this.prodManager.setSessionId(this.sessionId);
	}

	public Sim() {
		sessionId = (new DateTime()).toString();
		db = setUpDb();
		prodManager = new ProductManager();
		agentManager = new AgentManager();
		inventoryManager = new InventoryManager();
		agentManager.setDb(db);
		prodManager.setDb(db);
		inventoryManager.setDb(db);
		agentModel = new AgentModel();
		simConfig = new SimConfig();
		scheduler = new Scheduler();
		agentManager.setSessionId(sessionId);
		prodManager.setSessionId(sessionId);
		numSellerAssigned = 0;
	}

	/**
	 * @return
	 */
	private SQLiteConnection setUpDb() {
		SQLiteConnection conn = null;
		return conn;
	}

	public void initialize() throws Exception {
		agentModel.setConfig(simConfig.getAgentConfig());
		prodModel = new ProductModel(simConfig.getProdConfig());
		prodModel.generate(prodManager);
		agentModel.generate(agentManager);
	}

	public static void main(String[] args) {
		int timeStep = 0;
		int maxTimeStep;
		Sim sim = new Sim();
		try {
			sim.initialize();
			sim.assignProducts();
		} catch (Exception e) {
			logger.error("Can't initialize the simulation");
			logger.error("Exiting...");
			System.exit(1);
		}
		maxTimeStep = sim.simConfig.getMaxTimestep();
		while (timeStep<maxTimeStep) {
			sim.scheduler.advanceTime();
			sim.topUpBalance();
			/* Market movement will be here */
			sim.scheduler.finalizeTimeStep();
		}
	}

	/**
	 * At each time step, all balance will be credit a fixed amount of money
	 */
	private void topUpBalance() {
		// TODO Auto-generated method stub

	}

	/**
	 * Assign products to sellers base on categories, as in the market, usually
	 * a seller will sell related products.
	 * 
	 * @throws Exception
	 */
	public void assignProducts() throws Exception {
		int numProd;
		int prodNum;
		int quantityAssignThres = ((ProductConfig) prodManager.getConfig()).getQuantityAssignmentThreadshold();
		boolean prodPicked = false;
		Product prod, tmpProd = null;
		quantityAssigned = 0;
		Seller seller;
		Random prodRandom = new Random();
		Random prodNumRandom = new Random();
		Random prodPriceRandom = new Random();
		EntityManager sellers = agentManager.getSellers();
		prodPicked = false;
		logger.info("Start assigning products to sellers");
		for (int i = 0; i<sellers.getSize()&&!prodPicked; i++) {
			seller = (Seller) sellers.get(i);

			/* Pick products until a non-zero number of items is picked */
			/* No more products to assign, terminate */
			if (prodManager.getSize()==0) {
				logger.info("Products exhausted, no more products to be assigned");
				logger.info("Total of "+(i+1)+" sellers were assigned products");
				prodPicked = true;
				numSellerAssigned = i+1;
				break;
			}

			/* Pick a random product */
			prodNum = prodRandom.nextInt(prodManager.getSize());
			prod = (Product) prodManager.get(prodNum);
			tmpProd = new Product(prod);
			if (prod.getQuantity()<=quantityAssignThres)
				numProd = prod.getQuantity();
			else {
				numProd = prodNumRandom.nextInt(prod.getQuantity());
				while (numProd==0) {
					numProd = prodNumRandom.nextInt(prod.getQuantity());
					if (numProd==0) {
						numProd = prod.getQuantity();
						break;
					}
				}
			}
			quantityAssigned += numProd;
			tmpProd.setQuantity(numProd);
			tmpProd.setPriceMin(prod.getPriceMin());
			tmpProd.setPriceMax(prod.getPriceMin()+prodPriceRandom.nextDouble()*(prod.getPriceMax()-prod.getPriceMin()));
			inventoryManager.add(seller, tmpProd);
			prod.setQuantity(prod.getQuantity()-tmpProd.getQuantity());
			if (prod.getQuantity()==0) {
				prodManager.remove(prodNum);
				logger.debug("Product "+prod.getName()+" is up!");
			}
			logger.debug("Assigned product "+prod.getName()+"("+tmpProd.getQuantity()+") to seller "+seller.getName());
			numSellerAssigned++;

		}
		logger.info("Finished assigning products to sellers");
	}

	/**
	 * @param db2
	 */
	public void setDb(SQLiteConnection db) {
		// TODO Auto-generated method stub
		this.db = db;
		this.inventoryManager.setDb(db);

	}
}
