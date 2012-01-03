package marketbase;
import interfaces.MarketEntityInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.*;


@SuppressWarnings("serial")
//Class to handle the products configuration
class Marketplace_Goods extends JPanel implements MarketEntityInterface{

	private JTextField[] fields;
	//Parameters for user to set
	private JLabel[] labels = { new JLabel("No of products type: "), 
								new JLabel("No of products: "),
								new JLabel("Max price: "), 
								new JLabel("Min price: "), 
								new JLabel("No of categories: "), 
			                    //"Duration: "
	                    	  };
	
	//Textbox width
	private int[] widths = { 20, 20, 20, 20, 20 };
	
	//For .ini file comparison
	private String[] fieldName = { "numTypes",
            					   "numProducts",
            					   "priceMax",
            					   "priceMin",
            					   "numCategories"};
	
	//Constructor
	Marketplace_Goods() {
		this.setBorder(BorderFactory.createTitledBorder("Product/Price"));
		initLabelsandTextFields();
	  }
	
	//Overwrite the interface class method
	 public void initLabelsandTextFields()
	 {
		 JPanel labelPanel = new JPanel();
		 JPanel textPanel = new JPanel();
		 BoxLayout labLayout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
		 labelPanel.setLayout(labLayout);
		 BoxLayout textLayout = new BoxLayout(textPanel, BoxLayout.Y_AXIS);
		 textPanel.setLayout(textLayout);
		 fields = new JTextField[labels.length];
		 for(int i=0; i<labels.length; i++)
		 {
			 fields[i] = new JTextField(widths[i]);
			 fields[i].setName(fieldName[i]);
			 if(fields[i].getName().equalsIgnoreCase("numTypes"))
			 {
				 fields[i].setToolTipText("This field indicates the number of product types in the market simulation. " +
				 		"Please key in value from X to X"); //Need to find out the parameters meaning and ranges of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("numProducts"))
			 {
				 fields[i].setToolTipText("This field indicates the number of products in the market simulation." +
				 		"Please key in value from X to X"); //Need to find out the range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("priceMax"))
			 {
				 fields[i].setToolTipText("This field indicates the maximum price of a product. " +
				 		"Please key in value from X to X"); //Need to find out the range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("priceMin"))
			 {
				 fields[i].setToolTipText("This field indicates the minimum price of a product. " +
				 		"Please key in value from X to X"); //Need to find out the range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("numCategories"))
			 {
				 fields[i].setToolTipText("This field indicates. " +
				 		"Please key in value from X to X"); //Need to find out the parameters meaning and range of values
			 }
			 labels[i].setLabelFor(fields[i]);
			 labels[i].setAlignmentX(Component.LEFT_ALIGNMENT);
			 fields[i].setAlignmentX(Component.LEFT_ALIGNMENT);
		     labelPanel.add(labels[i]);
			 textPanel.add(fields[i]);
		 }
		 labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		 textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		 add(labelPanel);
		 add(textPanel);
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

	public void setCompSize(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		((FlowLayout)this.getLayout()).setHgap(width/3 - getComponent(0).getWidth());
	}
}
