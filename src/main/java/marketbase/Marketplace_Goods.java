package marketbase;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Marketplace_Goods extends JPanel implements FocusListener, ActionListener {
	SpringLayout	layout		= new SpringLayout();

	String[]		labels		= { "Number Of Type:", "Number Of Product:", "Maximum Price:",
			"Minimum Price:", "Number Of Categories:", "Quantity Assignment Threadshold:",
			"Distribution Class Name:", "Distribution Config Class Name:",
			"Distribution Config File Name:" };
	
	final String[] DEFAULT =
						{
							"<For Example: 50>",
							"<For Example: 10000>",
							"<For Example: 1000>",
							"<For Example: 1>",
							"<For Example: 15>",
							"<For Example: 10>",
							"<For Example: generatorbase.NormalDistribution>",
							"<For Example: configbase.NormalDistributionConfig>",
							"<For Example: src/test/resources/configbase/testNormalDistributionConfig.ini>"
						};
	JLabel[]		label		= new JLabel[9];
	JTextField[]	textfield	= new JTextField[9];
	ImageIcon open = new ImageIcon("Open.png");
	JButton browse = new JButton(open);

	public Marketplace_Goods() {
		this.setLayout(layout);

		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(labels[i]);
			textfield[i] = new JTextField(20);
			this.add(label[i]);
			this.add(textfield[i]);
			textfield[i].addFocusListener(this);
		}

		setTextField();
		
		browse.addActionListener(this);
		this.add(browse);

		layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, textfield[0], 120, SpringLayout.EAST, label[0]);
		layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, this, 10, SpringLayout.EAST, textfield[0]);

		for (int i = 1; i < label.length; i++) {
			layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH,
					textfield[i - 1]);

			layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST,
					textfield[i - 1]);
			layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH,
					textfield[i - 1]);
			
			if (i == label.length - 1)
			{
				layout.putConstraint(SpringLayout.EAST, textfield[i], 300, SpringLayout.EAST, label[i]);
				
				layout.putConstraint(SpringLayout.WEST, browse, 5, SpringLayout.EAST, textfield[i]);
				layout.putConstraint(SpringLayout.NORTH, browse, 5, SpringLayout.SOUTH, textfield[i-1]);
				layout.putConstraint(SpringLayout.EAST, browse, 0, SpringLayout.EAST, textfield[0]);
				layout.putConstraint(SpringLayout.SOUTH, browse, 0, SpringLayout.SOUTH, textfield[i]);
			}
			else
			{
				layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST,
						textfield[0]);
			}
		}
		layout.putConstraint(SpringLayout.SOUTH, this, 10, SpringLayout.SOUTH, textfield[textfield.length - 1]);
		this.setVisible(true);
	}

	public String configuration(String filename) {
		String fileS = "SavedConfiguration\\" + filename;
		boolean success = new File(fileS).mkdirs();
		try {
			File file = new File(fileS + "\\ProductConfiguration.ini");
			PrintWriter output = new PrintWriter(file);
			output.print("numTypes=");
			output.println(this.textfield[0].getText());
			output.print("numProducts=");
			output.println(this.textfield[1].getText());
			output.print("priceMax=");
			output.println(this.textfield[2].getText());
			output.print("priceMin=");
			output.println(this.textfield[3].getText());
			output.print("numCategories=");
			output.println(this.textfield[4].getText());
			output.print("quantityAssignmentThreadholds=");
			output.println(this.textfield[5].getText());
			output.print("distributionClassName=");
			output.println(this.textfield[6].getText());
			output.print("distributionConfigClassName=");
			output.println(this.textfield[7].getText());
			output.print("distributionConfigFile=");
			output.println(this.textfield[8].getText());
			output.close();
			fileS = file.getAbsolutePath();
		} catch (Exception ex) {
			System.out.println("IO Exception occured");
		}
		this.setTextField();
		return fileS;
	}
	
	public void importConfig(String filename)
	{
		File file = new File(filename);
		String[] key = null;
		int i = 0;
		
		try
		{
			Scanner reader = new Scanner(file);
			
			while (reader.hasNext())
			{
				String data = reader.nextLine();
				key = data.split("=", 0);
				textfield[i].setForeground(Color.black);
				textfield[i].setText(key[1]);
				i++;
			}
			
			reader.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
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
	
	public void actionPerformed(ActionEvent e)
	{
		String[] filename = initFileOpenChooser();
		textfield[8].setForeground(Color.black);
		textfield[8].setText(filename[0]);
	}
	
	public String[] initFileOpenChooser()
	{
		String[] filenameCombo = new String[2];
		
		try
		{
			JFileChooser fc = new JFileChooser();
			int value = fc.showOpenDialog(this);
			if (value == fc.APPROVE_OPTION)
			{
				filenameCombo[0] = fc.getSelectedFile().getAbsolutePath();
				filenameCombo[1] = fc.getSelectedFile().getName();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return filenameCombo;
	}
}