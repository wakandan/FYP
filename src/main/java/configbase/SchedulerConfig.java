/**
 *
 */
package configbase;

import modelbase.Entity;

/**
 * @author akai
 * 
 */
public class SchedulerConfig extends Config {
	int	maxTimestep;
	int	warmUpPeriod;

	public int getMaxTimestep() {
		return maxTimestep;
	}

	public int getWarmUpPeriod() {
		return warmUpPeriod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity)
	 */
	@Override
	public void configure(Entity e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#processConfigKey(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean processConfigKey(String key, String value) {
		if (key.equalsIgnoreCase("maxTimestep")) {
			this.maxTimestep = Integer.parseInt(value);
		} else if (key.equalsIgnoreCase("warmupPeriod")) {
			this.warmUpPeriod = Integer.parseInt(value);
		} else
			return false;
		return true;
	}

}
