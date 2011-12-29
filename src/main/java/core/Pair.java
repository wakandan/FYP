/**
 *
 */
package core;

/**
 * @author akai
 * 
 */
public class Pair<K extends Object, V extends Object> {
	public K	val1;
	public V	val2;

	public Pair(K val1, V val2) {
		this.val1 = val1;
		this.val2 = val2;
	}

}
