/**
 *
 */ 
package modelbase;


/**
 * @author akai
 *
 */
public abstract class Entity {
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Entity(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
}
