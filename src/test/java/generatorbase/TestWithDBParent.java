/**
 *
 */ 
package generatorbase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.almworks.sqlite4java.SQLiteConnection;

/**
 * @author akai
 * @note: 
 * 	This class will be inherited by other test classes. This parent class set up
 * 	a in-memory database for testing.
 */
public class TestWithDBParent {
	SQLiteConnection db;
	
	@Before
	public void setUp() throws Exception {
		db = new SQLiteConnection();
		db.open(true);
	}
}
