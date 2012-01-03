package configbase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import modelbase.Entity;
import core.BaseObject;

public abstract class Config extends BaseObject {
	public HashMap<String, String>	configEntries;

	public abstract void configure(Entity e);

	public void readConfig(String filename) throws IOException {
		configEntries = Config.readConfigFile(filename);
		String value;
		for (String key : configEntries.keySet()) {
			value = configEntries.get(key);
			if (!processConfigKey(key, value))
				logger.error("Config key maybe invalid: " + key + ", value: " + value);
		}

	}

	/**
	 * @param key
	 * @param value
	 * @return true if the key is processed
	 */
	public abstract boolean processConfigKey(String key, String value);

	/* Read a config file in format config=data, each entry is on its own line.
	 * Ignore lines with "#" character at the beginning as the comment */
	public static HashMap<String, String> readConfigFile(String filename) throws IOException {
		String line;
		String[] lineData;
		HashMap<String, String> result = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		while ((line = br.readLine()) != null) {

			/* Ignore lines start with hash "#". This line will be considered as
			 * comments */
			if (line.startsWith("#"))
				continue;
			lineData = line.split("=");

			/* Ignore invalid lines */
			if (lineData.length < 2)
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
			logger.error("Config file does not have entry: " + key);
		return configEntries.get(key);
	}

	public static Object config(Class klass, String configFile) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchFieldException, IOException {
		Object klassInstance = klass.newInstance();
		Method getAttrMethod = klass.getMethod("getConfigAttributes");
		String[] attrNames = (String[]) getAttrMethod.invoke(klassInstance);
		HashMap<String, String> configMap = Config.readConfigFile(configFile);
		for (String attrName : attrNames) {
			Field field = klass.getField(attrName);
			Class<?> fieldType = field.getType();
			String value = configMap.get(attrName);
			if (value == null) {
				System.out.println("Unable to find key: " + attrName);
				continue;
			}
			if (attrName.endsWith("Class")) {
				/* For dealing with key which are classes */
				Class attrClass = Class.forName(value);
				field.set(klassInstance, attrClass);
			} else if (fieldType.equals(Double.TYPE)) {
				field.setDouble(klassInstance, Double.parseDouble(value));
			} else if (fieldType.equals(Integer.TYPE)) {
				field.setInt(klassInstance, Integer.parseInt(value));
			} else {
				/* If encounter a complex key-value pair, invoke created
				 * object's key processing capability */
				Class parameterTypes[] = new Class[2];
				parameterTypes[0] = String.class;
				parameterTypes[1] = String.class;
				boolean processResult = false;
				try {
					Method processComplexKeyMethod = klass.getMethod("processConfigKey",
							parameterTypes);
					Field configEntriesField = klass.getField("configEntries");
					configEntriesField.set(klassInstance, configMap);
					processResult = (Boolean) processComplexKeyMethod.invoke(klassInstance,
							attrName, value);
				} catch (NoSuchMethodError e) {
					processResult = false;
				}
				if (!processResult)
					field.set(klassInstance, value);
			}
		}
		return klassInstance;
	}
}
