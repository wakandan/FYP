package simbase;

public abstract class ConfigManager {
	public abstract Config readConfigFile(String filename);
	public abstract void writeConfigFile(Config config);
}
