/**
 *
 */
package core;

/**
 * @author akai
 *
 */
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MyLogger {
	Logger						logger;
	ArrayList<MyEventListener>	eventListenerList;
	MyEvent						event;

	/**
	 * @param name
	 */
	public MyLogger(String name) {
		event = new MyEvent("");
		logger = Logger.getLogger(name);
		eventListenerList = new ArrayList<MyEventListener>();
	}

	public void info(Object msg) {
		logger.info(msg);
		sendUpdate(msg);
	}

	public void error(Object msg) {
		logger.error(msg);
		sendUpdate(msg);
	}

	/**
	 * @param error
	 */
	public void setLevel(Level level) {
		logger.setLevel(level);
	}

	public void debug(Object msg) {
		logger.debug(msg);
		sendUpdate(msg);
	}

	public void sendUpdate(Object msg) {
		if (msg instanceof String)
			event.text = (String) msg;
		else
			event.text = msg.toString();
		for (MyEventListener listener : eventListenerList) {
			listener.onRecvMyEvent(event);
		}
	}

	public void addMyEventListener(MyEventListener listener) {
		eventListenerList.add(listener);
	}

	public void delMyEventListener(MyEventListener listener) {
		eventListenerList.remove(listener);
	}
}
