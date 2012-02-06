/**
 *
 */
package simbase;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.Test;

import core.MyEvent;
import core.MyEventListener;

/**
 * @author akai
 * 
 */
public class TestMyEvent extends TestSimRunParent {

	public void testSimRunWithGUI() throws Exception {
		super.setUp();
		JPanel pane = new MyPanel();
		JFrame frame = new JFrame("My Simple Frame");
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(pane);
		frame.setVisible(true);
		sim.registerEventListeners((MyEventListener) pane);
		sim.run();
	}

	public static void main(String[] argv) throws Exception {
		TestMyEvent testMyEvent = new TestMyEvent();
		testMyEvent.testSimRunWithGUI();
	}
	
	class MyPanel extends JPanel implements MyEventListener {
		JTextArea	textArea;
		JScrollPane	scrollPane;

		/* (non-Javadoc)
		 * 
		 * @see core.MyEventListener#onRecvMyEvent(core.MyEvent) */
		public void onRecvMyEvent(MyEvent event) {
			this.textArea.append(event.text + "\n");
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}

		public MyPanel() {
			super();
			textArea = new JTextArea(30,70);
			textArea.setFont(new Font("Courier New", Font.PLAIN, 14));
			scrollPane = new JScrollPane(textArea);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			this.add(scrollPane);
		}
	}
}
