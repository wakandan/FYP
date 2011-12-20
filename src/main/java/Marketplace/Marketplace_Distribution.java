package Marketplace;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Interfaces.MarketEntityInterface;

//Class to handle the distribution configuration
public class Marketplace_Distribution extends JPanel implements MarketEntityInterface{

	private JTextField[] fields;
	//Parameters for user to set 
	private String[] labels = { "Mean",
			                    "Variance",
			                    "Distribution",
			                    "Distribution Mean",
			                    "Distribution Deviation"};
	//Textbox width
	private int[] widths = { 4, 4, 14, 6, 8};
	
	//For .ini file comparison
	private String[] fieldName = { "mean",
								   "variance",
								   "distribution",
								   "distributionMean",
								   "distributionDeviation"};
	
	//Constructor
	Marketplace_Distribution() {
		this.setLayout(new GridLayout(labels.length,1));
		this.setBorder(BorderFactory.createTitledBorder("Distribution"));
		initLabelsandTextFields();
	}
	
	//Overwrite the interface class method
	public void initLabelsandTextFields()
	{
		//To create textfield based on label
		fields = new JTextField[labels.length];
		for(int i=0; i<labels.length; i++)
		{
			 JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			 JLabel label = new JLabel(labels[i],JLabel.LEFT);
			 fields[i] = new JTextField(widths[i]);
			 fields[i].setName(fieldName[i]);
			 label.setLabelFor(fields[i]);
			 tempPanel.add(label);
			 tempPanel.add(fields[i]);
			 add(tempPanel);
		 }
	 }
	 
	 public void resetText()
	 {
		 for(int i = 0;i < this.getComponentCount();i++)
			 if(this.getComponent(i) instanceof JPanel)
				 setResetTextField(((JPanel)this.getComponent(i)));
	 }
	
	public void setResetTextField(JPanel tempPanel)
	{
		 for(int c = 0;c < tempPanel.getComponentCount();c++)
			 if(tempPanel.getComponent(c) instanceof JTextField)
				 ((JTextField)tempPanel.getComponent(c)).setText("");
	}	

	public void setParameters(int i,String text)
	{
		if(i < 0 || i >= fields.length)
			return;
		
		fields[i].setText(text);
	}
}
