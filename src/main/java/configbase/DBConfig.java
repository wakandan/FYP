/**
 *
 */
package configbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author akai
 * 
 */
public class DBConfig {
	String				filename;
	ArrayList<String>	ddlFilenames;

	/*Filename==null to make a database in memory*/
	public DBConfig(String filename) {
		this.filename = filename;
		this.ddlFilenames = new ArrayList<String>(); 
	}

	public void addDdlFile(String filename) {
		ddlFilenames.add(filename);
	}

	public SQLiteConnection setUpDb() throws SQLiteException, IOException {
		SQLiteConnection db;
		SQLiteStatement st;
		if (filename!=null)
			db = new SQLiteConnection(new File(filename));
		else
			db = new SQLiteConnection();
		db.open(true);
		for (String file : ddlFilenames) {
			st = db.prepare(DBConfig.readDDL(file));
			st.step();
		}
		return db;
	}
	
	

	/*
	 * A function to read ddl files and execute, create suitable tables for
	 * testing. It's a basic function for reading a file and return the whole
	 * file content
	 */
	public static String readDDL(String filename) throws IOException {
		String line;
		String result = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		while ((line = br.readLine())!=null)
			result += line;
		return result;
	}
	
	protected boolean processConfigKey(String key, String value) {
		if (key.equalsIgnoreCase(filename)) {
			this.filename = value;
			return true;
		}
		else {
			return false;
		}
	}

}
