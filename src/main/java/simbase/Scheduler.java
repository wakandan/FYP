package simbase;

import core.BaseObject;

public class Scheduler extends BaseObject {
	int	currentTimestep;

	public void advanceTime() {
		logger.info(String.format("************** Time step "+currentTimestep));
	}

	/**
	 * 
	 */
	public void finalizeTimeStep() {
		currentTimestep++;
	}
}
