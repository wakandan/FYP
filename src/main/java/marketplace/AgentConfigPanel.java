package marketplace;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class AgentConfigPanel extends JPanel implements FocusListener {
	SpringLayout	layout		= new SpringLayout();
	String[]		labels		= { "Number Of Buyer:", "Number Of Seller:", "Initial Balance:" };
	JLabel[]		label		= new JLabel[3];
	JTextField[]	textfield	= new JTextField[3];
	JButton			next_product_config;

	public AgentConfigPanel() {
		this.setLayout(layout);

		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(labels[i]);
			textfield[i] = new JTextField(20);
			this.add(label[i]);
			this.add(textfield[i]);
			textfield[i].addFocusListener(this);
		}

		textfield[0].setText("<For Example: 100>");
		textfield[1].setText("<For Example: 100>");
		textfield[2].setText("<For Example: 100.54>");

		next_product_config = new JButton("Config");
		this.add(next_product_config);

		layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, textfield[0], 113, SpringLayout.EAST, label[0]);
		layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);

		for (int i = 1; i < label.length; i++) {
			layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH,
					textfield[i - 1]);

			layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST,
					textfield[i - 1]);
			layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH,
					textfield[i - 1]);
			layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST,
					textfield[0]);
		}

		layout.putConstraint(SpringLayout.WEST, next_product_config, 0, SpringLayout.WEST,
				textfield[0]);
		layout.putConstraint(SpringLayout.NORTH, next_product_config, 5, SpringLayout.SOUTH,
				textfield[textfield.length - 1]);
		layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_product_config);

		this.setVisible(false);
	}

	public void configuration() {
		try {
			File file = new File("AgentConfiguration.ini");
			int i = 0;
			while (file.exists()) {
				file = new File("AgentConfiguration" + i + ".ini");
				i++;
			}

			PrintWriter output = new PrintWriter(file);
			output.print("buyerNum=");
			output.println(this.textfield[0].getText());
			output.print("sellerNum=");
			output.println(this.textfield[1].getText());
			output.print("initialBalance=");
			output.println(this.textfield[2].getText());
			output.close();
		} catch (Exception ex) {
			System.out.println("IO Exception occured");
		}
		for (int i = 0; i < textfield.length; i++) {
			this.textfield[i].setText("");
		}
	}

	public void focusGained(FocusEvent e) {
		for (int i = 0; i < textfield.length; i++) {
			if (e.getSource() == textfield[i]) {
				textfield[i].setText("");
			}
		}
	}

	public void focusLost(FocusEvent e) {}
}
