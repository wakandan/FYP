/**
 *
 */
package core;

import java.util.EventListener;

/**
 * @author akai
 * 
 */
public interface MyEventListener extends EventListener {
	public void onRecvMyEvent(MyEvent event);
}
