package generatorbase;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */

/**
 * @author akai
 *
 */
public class TestNormalDistribution {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {}

	@Test
	public void testNormalDistribution() {
		double mean = 1;
		double variance = 2;
		double delta = 0.000000001;
		NormalDistribution dis = new NormalDistribution(mean, variance);
		assertTrue(dis.pdf(0)>0);
		assertTrue(Math.abs(dis.cdf(1)-0.5)<=delta);
		assertTrue(Math.abs(dis.cdf_range(-100, 100)-1)<=delta);
	}

}
