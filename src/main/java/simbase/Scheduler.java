package simbase;

import configbase.SchedulerConfig;
import core.BaseObject;

public class Scheduler extends BaseObject {
	int				currentTimestep;
	SchedulerConfig	config;

	public Scheduler() {
		super();
		currentTimestep = 0;
	}

	public boolean isRunning() {
		return this.config.getMaxTimestep()>=currentTimestep;
	}

	public SchedulerConfig getConfig() {
		return config;
	}

	public void setConfig(SchedulerConfig config) {
		this.config = config;
	}

	public boolean isWarmingup() {
		return this.config.getWarmUpPeriod()>=currentTimestep;
	}

	public void advanceTime() {
		currentTimestep++;
		logger.info(String.format("************** Time step "+currentTimestep));
	}

	/**
	 * 
	 */
	public void finalizeTimeStep() {
		currentTimestep++;
	}
}
