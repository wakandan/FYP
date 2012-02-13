/**
 *
 */
package core;

import static org.junit.Assert.*;

import javax.swing.SwingUtilities;

import org.junit.Test;

/**
 * @author akai
 * 
 */
public class TestMyGUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				MyGUI gui = new MyGUI();
			}
		});
	}
}
