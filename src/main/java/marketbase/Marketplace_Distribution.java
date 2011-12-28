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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//Class to handle the distribution configuration
public class Marketplace_Distribution extends JPanel implements MarketEntityInterface{

	private JTextField[] fields;
	//Parameters for user to set 
	private JLabel[] labels = { new JLabel("Mean: "),
								new JLabel("Variance: "),
								new JLabel("Distribution: "),
								new JLabel("Distribution Mean: "),
								new JLabel("Distribution Deviation: ")};
	//Textbox width
	private int[] widths = { 20, 20, 20, 20, 20};
	
	//For .ini file comparison
	private String[] fieldName = { "mean",
								   "variance",
								   "distribution",
								   "distributionMean",
								   "distributionDeviation"};
	
	//Constructor
	Marketplace_Distribution() {
		this.setBorder(BorderFactory.createTitledBorder("Distribution"));
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
			 if(fields[i].getName().equalsIgnoreCase("mean"))
			 {
				 fields[i].setToolTipText("This field indicates. " +
				 		"Please key in value from X to X"); //Need to find out the parameters meaning and range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("variance"))
			 {
				 fields[i].setToolTipText("This field indicates." +
				 		"Please key in value from X to X"); //Need to find out the parameters meaning and range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("distribution"))
			 {
				 fields[i].setToolTipText("This field indicates. " +
				 		"Please key in value from X to X"); //Need to find out the parameters meaning and range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("distributionMean"))
			 {
				 fields[i].setToolTipText("This field indicates. " +
				 		"Please key in value from X to X"); //Need to find out the parameters meaning and range of values
			 }
			 if(fields[i].getName().equalsIgnoreCase("distributionDeviation"))
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
		((FlowLayout)this.getLayout()).setHgap(width/5 - getComponent(0).getWidth());
	}
}
