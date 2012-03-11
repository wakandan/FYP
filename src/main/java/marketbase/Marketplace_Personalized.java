package marketbase;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class Marketplace_Personalized extends JPanel implements FocusListener{
	SpringLayout	layout		= new SpringLayout();
	String[]		labels		= { "Epsilon:", "Gamma:", "Forgetting:", "Time Window:" };
	JLabel[]		label		= new JLabel[4];
	final String[] DEFAULT = {
			"<For Example: 0.4>",
			"<For Example: 0.5>",
			"<For Example: 0.5>",
			"<For Example: 5>"
			};
	JTextField[]	textfield	= new JTextField[4];
	JButton			next_config;

	public Marketplace_Personalized() {
		this.setLayout(layout);

		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(labels[i]);
			textfield[i] = new JTextField(20);
			this.add(label[i]);
			this.add(textfield[i]);
			textfield[i].addFocusListener(this);
		}
		
		this.setTextField();

		layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, textfield[0], 170, SpringLayout.EAST, label[0]);
		layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, textfield[0], -10, SpringLayout.EAST, this);

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

		layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, textfield[textfield.length -1]);

		this.setVisible(false);
	}

	public String configuration(String filename) 
	{
		String fileS = "SavedConfiguration\\" + filename;
		boolean success = new File(fileS).mkdirs();
		try {
			File file = new File(fileS + "\\PersonalizedTrustModelConfiguration.ini");
			PrintWriter output = new PrintWriter(file);
			output.print("epsilon=");
			output.println(this.textfield[0].getText());
			output.print("gamme=");
			output.println(this.textfield[1].getText());
			output.print("forgetting=");
			output.println(this.textfield[2].getText());
			output.print("timeWindow=");
			output.println(this.textfield[3].getText());
			output.close();
			fileS = file.getAbsolutePath();
		} catch (Exception ex) {
			System.out.println("IO Exception occured");
		}
		this.setTextField();
		return fileS;
	}

	public void focusGained(FocusEvent e) {
		for (int i = 0; i < textfield.length; i++) {
			if (e.getSource() == textfield[i]) {
				textfield[i].setText("");
			}
		}
	}

	public void focusLost(FocusEvent e) {

	}
	
	public void setTextField()
	{
		Color color = Color.gray;
		for (int i = 0; i < textfield.length; i++)
		{
			textfield[i].setForeground(color);
			textfield[i].setText(DEFAULT[i]);
			textfield[i].setToolTipText(DEFAULT[i]);
		}	
	}
}