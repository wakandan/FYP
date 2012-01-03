package marketbase;
import interfaces.MarketEntityInterface;

import javax.swing.*;

import java.awt.*;

import layouts.SpringUtilities;


@SuppressWarnings("serial")
class Marketplace_Personnel extends JPanel implements MarketEntityInterface{
	
	private JTextField[] fields;
	//Parameters for user to set
	private JLabel[] labels = { new JLabel("No of buyers: "), 
								new JLabel("No of sellers: "), 
								new JLabel("Initial balance: "),
			                    //"% of default honest buyers: ", 
			                    //"% of default honest sellers: ", 
			                    //"Distribution of auto-buyer's rating: ", 
			                    //"Distribution of buyer/seller entering marketplace: " 
			                    };
	
	//Textbox width
	private int[] widths = { 20, 20,20 };
	private Point[] points = {new Point(20,40),
						      new Point(50,40),
						      new Point(20,50),
						      new Point(50,50),
						      new Point(20,60),
						      new Point(50,60)
			                 };
	
	//For .ini file comparison
	private String[] fieldName = { "buyerNum",
			                       "sellerNum",
			                       "initialBalance"};

	//Constructor
	Marketplace_Personnel() {
		this.setBorder(BorderFactory.createTitledBorder("Buyer/Seller"));
		initLabelsandTextFields();
	 }
	
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
			 if(fields[i].getName().equalsIgnoreCase("buyerNum"))
			 {
				 fields[i].setToolTipText("This field indicates the no. of buyer for the market simulation. " +
				 		"Please key in value from X to X"); //Need to find out the range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("sellerNum"))
			 {
				 fields[i].setToolTipText("This field indicates the no. of seller for the market simulation. " +
				 		"Please key in value from X to X"); //Need to find out the range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("initialBalance"))
			 {
				 fields[i].setToolTipText("This field indicates the initial amount of the moneys the buyers have. " +
				 		"Please key in value from X to X"); //Need to find out the range of values
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
