/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import configbase.SchedulerConfig;

/**
 * @author akai
 * 
 */
public class TestScheduler {

	@Test
	public void testSchedulerConfig() throws IOException {
		SchedulerConfig schedulerConfig = new SchedulerConfig();
		schedulerConfig.readConfig("src/test/resources/simbase/TestSchedulerConfig.ini");
		assertEquals(5, schedulerConfig.getWarmUpPeriod());
		assertEquals(10, schedulerConfig.getMaxTimestep());
	}

}
