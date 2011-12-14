package configbase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import core.BaseObject;

import modelbase.Entity;

public abstract class Config extends BaseObject {
	HashMap<String, String>	configEntries;

	public abstract void configure(Entity e);

	public void readConfig(String filename) throws IOException {
		configEntries = Config.readConfigFile(filename);
		String value;
		for (String key : configEntries.keySet()) {
			value = configEntries.get(key);
			if (!processConfigKey(key, value))
				logger.error("Error processing parameter name: "+key+", value: "+value);
		}

	}

	/**
	 * @param key
	 * @param value
	 * @return true if the key is processed
	 */
	protected abstract boolean processConfigKey(String key, String value);

	/*
	 * Read a config file in format config=data, each entry is on its own line.
	 * Ignore lines with "#" character at the beginning as the comment
	 */
	public static HashMap<String, String> readConfigFile(String filename) throws IOException {
		String line;
		String[] lineData;
		HashMap<String, String> result = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		while ((line = br.readLine())!=null) {

			/*
			 * Ignore lines start with hash "#". This line will be considered as
			 * comments
			 */
			if (line.startsWith("#"))
				continue;
			lineData = line.split("=");

			/* Ignore invalid lines */
			if (lineData.length<2)
				continue;
			if (!result.containsKey(lineData[0])) {
				result.put(lineData[0].trim(), lineData[1].trim());
			}
		}
		return result;
	}

	/* Return a value for specified config entry */
	public String getConfigEntry(String key) {
		if (!configEntries.containsKey(key))
			logger.error("Config file does not have entry: "+key);
		return configEntries.get(key);
	}

}
