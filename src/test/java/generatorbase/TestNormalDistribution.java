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
		double delta = 0.0000001;
		NormalDistribution dis = new NormalDistribution(mean, variance);
		/* Test obvious facts first */
		assertTrue(dis.pdf(0)>0);
		assertTrue(Math.abs(dis.cdf(1)-0.5)<=delta);
		assertTrue(Math.abs(dis.cdf_range(-100, 100)-1)<=delta);

		/*
		 * Test concrete data, using the calculator at
		 * http://www.math.csusb.edu/
		 * faculty/stanton/probstat/normal_distribution.html
		 */
		dis = new NormalDistribution(500, 50*50);
		assertTrue(Math.abs(dis.cdf_range(100, 400)-0.0227501)<=delta);

	}

}
