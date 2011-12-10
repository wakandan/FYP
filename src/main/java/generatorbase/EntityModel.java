package generatorbase;

import com.almworks.sqlite4java.SQLiteException;

import configbase.Config;
import core.BaseObject;

public abstract class EntityModel extends BaseObject{
	protected Config		config;
	protected EntityManager	manager;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public abstract void generate(EntityManager manager) throws SQLiteException, Exception;
}
