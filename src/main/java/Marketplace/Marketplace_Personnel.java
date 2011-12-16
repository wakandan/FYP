package Marketplace;
import javax.swing.*;

import java.awt.*;
import Interfaces.MarketEntityInterface;

@SuppressWarnings("serial")
class Marketplace_Personnel extends JPanel implements MarketEntityInterface{
	
	private JTextField[] fields;
	//Marketplace setup parameters for user to set
	private String[] labels = { "No of buyers: ", 
	                    "No of sellers: ", 
	                    "Initial balance: ",
	                    "Distribution Model: ",
	                    "Distribution Mean: ",
	                    "Distribution Deviation: ",
	                    "% of default honest buyers: ", 
	                    "% of default honest sellers: ", 
	                    "Distribution of auto-buyer's rating: ", 
	                    "Dustrubution of buyer/seller entering marketplace: " };
	//Textbox width
	private int[] widths = { 4, 4, 4, 5, 2, 2, 2, 2, 2, 2 };
	
	Marketplace_Personnel() {
		this.setLayout(new GridLayout(labels.length,1));
		this.setBorder(BorderFactory.createTitledBorder("Buyer/Seller"));
		initLabelsandTextFields();
	 }
	

	 public void initLabelsandTextFields()
	 {
		 fields = new JTextField[labels.length];
		 for(int i = 0;i < labels.length;i++)
		 {
			 JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			 JLabel label = new JLabel(labels[i],JLabel.LEFT);
			 fields[i] = new JTextField(widths[i]);
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
