package simbase;

import core.BaseObject;

public class Scheduler extends BaseObject{
	int currentTimestep;
	public void advanceTime() {
		logger.info("Time step "+(++currentTimestep));
	}

	/**
	 * 
	 */
	public void finalizeTimeStep() {
		// TODO Auto-generated method stub
		
	}
}
