/**
 *
 */
package simbase;

import productbase.ProductManager;

import com.almworks.sqlite4java.SQLiteConnection;

import configbase.SimConfig;

import generatorbase.AgentModel;
import agentbase.AgentManager;

/**
 * @author akai
 * 
 */
public class Sim {
	AgentManager		agentManager;
	AgentModel			agentModel;
	SQLiteConnection	db;
	SimConfig			simConfig;
	int					timeStep;
	Scheduler			scheduler;
	ProductManager	prodManager;
 
	public Sim() {
		db = setUpDb();
		agentManager = new AgentManager();
		prodManager = new ProductManager();
		agentManager.setDb(db);
		agentModel = new AgentModel();
		simConfig = new SimConfig();
		scheduler = new Scheduler();
	}

	/**
	 * @return
	 */
	private SQLiteConnection setUpDb() {
		SQLiteConnection conn = null;
		return conn;
	}

	public void initialize() {
		agentModel.setConfig(simConfig.getAgentConfig());
	}

	public static void main(String[] args) {
		int timeStep = 0;
		int maxTimeStep;
		Sim sim = new Sim();
		sim.initialize();
		maxTimeStep = sim.simConfig.getMaxTimestep();
		while (timeStep<maxTimeStep) {
			sim.scheduler.advanceTime();
			/*Market movement will be here*/ 
			sim.scheduler.finalizeTimeStep();
		}
	}

}
