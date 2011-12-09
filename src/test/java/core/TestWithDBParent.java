/**
 *
 */
package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author akai
 * @note: This class will be inherited by other test classes. This parent class
 *        set up a in-memory database for testing.
 */
public class TestWithDBParent {
	protected SQLiteConnection	db;
	protected SQLiteStatement st;

	@Before
	public void setUp() throws Exception {
		db = new SQLiteConnection();
		db.open(true);
		st = db.prepare(readDDL("src/main/resources/sql/Products.ddl"));
		st.step();
		st = db.prepare(readDDL("src/main/resources/sql/Agents.ddl"));
		st.step();
		st = db.prepare(readDDL("src/main/resources/sql/Inventories.ddl"));
		st.step();
	}

	/*
	 * A function to read ddl files and execute, create suitable tables for
	 * testing. It's a basic function for reading a file and return the whole
	 * file content
	 */
	public String readDDL(String filename) throws IOException {
		String line;
		String result = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		while ((line = br.readLine())!=null)
			result += line;
		return result;
	}
}
