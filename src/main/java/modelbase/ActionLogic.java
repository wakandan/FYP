/**
 *
 */
package modelbase;

import configbase.Config;
import core.BaseObject;

/**
 * @author akai
 * 
 */
public abstract class ActionLogic extends BaseObject {
	Config	config;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public abstract void config();

}
