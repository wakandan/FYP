package configbase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import modelbase.Entity;

public abstract class Config {
	public abstract void configure(Entity e);
	public abstract void readConfig(String filename) throws IOException;
	
	/*Read a config file in format config=data, each entry is on its own line.
	 * Ignore lines with "#" character at the beginning as the comment*/
	public static HashMap<String, String> readConfigFile(String filename) throws IOException {
		String line;
		String[] lineData;
		HashMap<String, String> result = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		while ((line = br.readLine())!=null) {
			if(line.startsWith("#"))
				continue;
			lineData = line.split("=");
			if(!result.containsKey(lineData[0])) {
				result.put(lineData[0].trim(), lineData[1].trim());
			}
		}
		return result;
	}
}
