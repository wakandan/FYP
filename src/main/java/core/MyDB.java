package core;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.DBConfig;

/**
 *
 */

/**
 * @author akai
 * 
 */
public class MyDB extends BaseObject {
	public SQLiteConnection	conn;
	SQLiteStatement			st;
	public String			dbDDLDir;
	public String			dbFilename;

	public MyDB(String filename, String dbDDLDir) {
		this.dbDDLDir = dbDDLDir;
		this.dbFilename = filename;
		try {
			setUp();
		} catch (SQLiteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public void setUp() throws SQLiteException, IOException {
		/* Find all the ddl files in the directory */
		File dir = new File(this.dbDDLDir);
		ArrayList<String> ddlFiles;

		/* Filter the list of returned file */
		FilenameFilter filter = new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.endsWith(".ddl");
			}
		};
		ddlFiles = new ArrayList<String>();
		for (String filename : dir.list(filter)) {
			ddlFiles.add((new File(dir, filename)).getAbsolutePath());
		}
		DBConfig dbConfig = new DBConfig(this.dbFilename);
		dbConfig.ddlFilenames = ddlFiles;
		this.conn = dbConfig.setUpDb();
	}
}
