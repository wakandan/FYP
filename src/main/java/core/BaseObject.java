/**
 *
 */
package core;

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author akai
 * 
 */
public class BaseObject {
	public MyLogger	logger;

	public BaseObject() {
//		logger = Logger.getLogger(this.getClass().getName());
		logger = new MyLogger(this.getClass().getName());
		//		logger.setLevel(Level.ERROR);
	}
}
