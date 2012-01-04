/**
 *
 */
package simbase;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import configbase.DBConfig;
import configbase.SimConfig;

import simbase.Sim;

/**
 * @author akai
 * 
 */
public class TestSimRun extends TestSimRunParent {

	@Test
	public void testRunSim() throws Exception {
		sim.run();
	}
}
