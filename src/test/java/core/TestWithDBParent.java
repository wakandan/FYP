/**
 *
 */
package core;

import org.junit.Before;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.DBConfig;

/**
 * @author akai
 * @note: This class will be inherited by other test classes. This parent class
 *        set up a in-memory database for testing.
 */
public class TestWithDBParent {
	protected SQLiteConnection	db;
	protected SQLiteStatement	st;

	@Before
	public void setUp() throws Exception {
		db = new SQLiteConnection();
		db.open(true);
		st = db.prepare(DBConfig.readDDL("src/main/resources/sql/Products.ddl"));
		st.step();
		st = db.prepare(DBConfig.readDDL("src/main/resources/sql/Agents.ddl"));
		st.step();
		st = db.prepare(DBConfig.readDDL("src/main/resources/sql/Inventories.ddl"));
		st.step();
	}
}
