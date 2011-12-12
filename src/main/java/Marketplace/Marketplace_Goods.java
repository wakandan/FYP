package Marketplace;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;

import Interfaces.MarketEntityInterface;

@SuppressWarnings("serial")
class Marketplace_Goods extends JPanel implements MarketEntityInterface{

	private JTextField[] fields;
	//Marketplace setup parameters for user to set
	private String[] labels = { "Pool of items: ", 
	                    "Price range of items: ", 
	                    "Distribution of product: ", 
	                    "Distribution of price: ", 
	                    "Duration: "};
	//Textbox width
	private int[] widths = { 4, 2, 2, 2, 2 };
	
	Marketplace_Goods() {
		this.setLayout(new GridLayout(labels.length,1));
		this.setBorder(BorderFactory.createTitledBorder("Product/Price"));
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
