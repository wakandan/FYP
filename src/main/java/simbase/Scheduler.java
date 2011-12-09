package simbase;

public class Scheduler {
	int currentTimestep;
	public int advanceTime() {
		return currentTimestep++;
	}

	/**
	 * 
	 */
	public void finalizeTimeStep() {
		// TODO Auto-generated method stub
		
	}
}
