package generatorbase;

import configbase.Config;

public abstract class Model {
	Config config;

	public void setConfig(Config config) {
		this.config = config;
	}
}
