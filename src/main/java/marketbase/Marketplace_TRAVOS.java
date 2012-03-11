package marketbase;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class Marketplace_TRAVOS extends JPanel implements FocusListener {
	SpringLayout	layout		= new SpringLayout();
	String[]		labels		= { "Number Of Bins:", "Error Threadshold:",
			"Minimum Accuracy Value:" };
	final String[] DEFAULT = {
			"<For Example: 5>",
			"<For Example: 0.2>",
			"<For Example: 0.5>"
	};
	JLabel[]		label		= new JLabel[3];
	JTextField[]	textfield	= new JTextField[3];
	JButton			next_config;

	public Marketplace_TRAVOS() {
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

		layout.putConstraint(SpringLayout.WEST, textfield[0], 123, SpringLayout.EAST, label[0]);
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

		layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, textfield[textfield.length - 1]);

		this.setVisible(false);
	}

	public String configuration(String filename) 
	{
		String fileS = "SavedConfiguration\\" + filename;
		boolean success = new File(fileS).mkdirs();
		try {
			File file = new File(fileS + "\\TRAVOSTrustModelConfiguration.ini");
			int i = 0;
			while (file.exists()) {
				file = new File("TRAVOSTrustModelConfiguration" + i + ".ini");
				i++;
			}

			PrintWriter output = new PrintWriter(file);
			output.print("numBins=");
			output.println(this.textfield[0].getText());
			output.print("errorThredshold=");
			output.println(this.textfield[1].getText());
			output.print("minAccuracyValue=");
			output.println(this.textfield[2].getText());
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
				textfield[i].setForeground(Color.black);
				if (textfield[i].getText().equalsIgnoreCase(DEFAULT[i]))
				{
					textfield[i].setText("");
				}
			}
		}
	}

	public void focusLost(FocusEvent e) {}
	
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