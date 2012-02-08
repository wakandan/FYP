/**
 *
 */
package core;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author akai
 * 
 */
public class TestMyDB {

	@Test
	public void testDBSetup() throws SQLiteException, IOException {
		String dbFilename = "D:/doc/workspace/sim/db.sqlite";
		String dbFilename1 = "D:/doc/workspace/sim/db1.sqlite";
		String dbInitDir = "D:/doc/workspace/sim/src/main/resources/sql";
		// MyDB mydb = new MyDB(dbFilename, dbInitDir);
		MyDB mydb = new MyDB(dbFilename1, dbInitDir);
		try {
			SQLiteStatement st = mydb.conn.prepare("SELECT * FROM Products LIMIT 1 ");
		} catch (SQLiteException e) {
			e.printStackTrace();
			fail("MyDB was not implemented correctly");
		}
	}
}
