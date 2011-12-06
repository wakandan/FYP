package generatorbase;

import com.almworks.sqlite4java.SQLiteException;

import configbase.Config;

public abstract class EntityModel {
	protected Config config;
	protected EntityManager manager;
	public void setConfig(Config config) {
		this.config = config;
	}
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	public abstract void generate(EntityManager manager) throws SQLiteException, Exception;
}
