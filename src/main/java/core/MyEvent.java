/**
 *
 */
package core;

import java.util.EventObject;

/**
 * @author akai
 * 
 */
public class MyEvent extends EventObject {
	public String	text;

	public MyEvent(Object arg0) {
		super(arg0);
		if (arg0 instanceof String) {
			text = (String) arg0;
		}
	}

}
