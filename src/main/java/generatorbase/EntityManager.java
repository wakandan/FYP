/**
 *
 */
package generatorbase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import modelbase.Entity;

import org.joda.time.DateTime;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import configbase.Config;
import core.BaseObject;

/**
 * @author akai
 *
 */
/**
 * @author akai
 * 
 */
public class EntityManager extends BaseObject {
	protected Config					config;
	protected String					sessionId;
	protected SQLiteConnection			db;
	protected HashMap<String, Entity>	entities;
	protected SQLiteStatement			st;
	ArrayList<String>					entitiesNames;

	public ArrayList<String> getEntitiesNames() {
		return entitiesNames;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

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
		super();
		sessionId = (new DateTime()).toString();
		entities = new HashMap<String, Entity>();
		entitiesNames = new ArrayList<String>();
	}

	public void add(Entity e) throws Exception {
		entities.put(e.getName(), e);
		entitiesNames.add(e.getName());
	}

	public String getSessionId() {
		return sessionId;
	}

	public int getSize() {
		return entities.keySet().size();
	}

	public Entity get(String entityName) {
		return entities.get(entityName);
	}

	public Config getConfig() {
		return config;
	}

	public Collection<Entity> getAll() {
		return entities.values();
	}

	public Entity getEntityByName(String entityName) {
		return entities.get(entityName);
	}

	public Set<String> getAllNames() {
		return entities.keySet();
	}

	public void remove(String name) {
		entities.remove(name);
	}

	public void replace(String oldName, String newName) {
		if (entities.containsKey(oldName)) {
			entities.put(newName, entities.get(oldName));
			entities.remove(oldName);
		}
	}

}
