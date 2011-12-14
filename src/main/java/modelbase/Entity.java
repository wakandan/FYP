/**
 *
 */
package modelbase;

import core.BaseObject;

/**
 * @author akai
 * 
 */
public abstract class Entity extends BaseObject {
	protected String	name;

	public Entity() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	/**
	 * @return
	 */
}
