package generatorbase;

import configbase.Config;

public abstract class EntityModel {
	Config config;

	public void setConfig(Config config) {
		this.config = config;
	}
}
