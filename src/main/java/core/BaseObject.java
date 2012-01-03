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
	protected Logger	logger;

	public BaseObject() {
		logger = Logger.getLogger(this.getClass().getName());
		//logger.setLevel(Level.ERROR);
	}
}
