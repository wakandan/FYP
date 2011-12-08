/**
 *
 */
package generatorbase;

import java.util.ArrayList;

import modelbase.Entity;

import org.joda.time.DateTime;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;

import configbase.Config;

/**
 * @author akai
 *
 */
/**
 * @author akai
 * 
 */
public class EntityManager {
	protected Config			config;
	protected String			sessionId;
	protected SQLiteConnection	db;
	protected ArrayList<Entity>	entities;

	/**
	 * @param prodcf
	 */
	public void setConfig(Config config) {
		this.config = config;
	}

	/**
	 * @throws SQLiteException
	 * 
	 */
	public void commitTransaction() throws SQLiteException {
		db.exec("COMMIT TRANSACTION");
	}

	/**
	 * @throws SQLiteException
	 * 
	 */
	public void beginTransaction() throws SQLiteException {
		db.exec("BEGIN TRANSACTION");
	}

	public SQLiteConnection getDb() {
		return db;
	}

	public void setDb(SQLiteConnection db) {
		this.db = db;
	}

	public EntityManager() {
		sessionId = (new DateTime()).toString();
		entities = new ArrayList<Entity>();
	}

	public void add(Entity e) throws Exception {
		entities.add(e);
	}

	public String getSessionId() {
		return sessionId;
	}

	public int getSize() {
		return entities.size();
	}

	public Entity get(int i) {
		return entities.get(i);
	}

	public Config getConfig() {
		return config;
	}

	
}
