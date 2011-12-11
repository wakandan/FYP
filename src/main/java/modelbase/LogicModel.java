package modelbase;

import simbase.Execution;
import generatorbase.Distribution;

public abstract class LogicModel {
	Distribution distribution;

	/**
	 * @param execution
	 */
	public abstract void processTransaction(Execution execution);
	
}
