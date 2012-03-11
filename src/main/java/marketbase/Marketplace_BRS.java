package marketbase;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class Marketplace_BRS extends JPanel implements FocusListener
{
    SpringLayout layout = new SpringLayout();
    JLabel label;
    final String DEFAULT = "<For Example: 0.1>";
    JTextField textfield;
    
    public Marketplace_BRS()
    {
        this.setLayout(layout);
        
        label = new JLabel("Quantile");
        textfield = new JTextField(20);
        
        this.add(label);
        this.add(textfield);
        
        textfield.addFocusListener(this);
        
        setTextField();
        
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, textfield, 167, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textfield, 5, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, textfield, -10, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, textfield);
        
        this.setVisible(false);
    }
    
    public String configuration(String filename) 
    {
		String fileS = "SavedConfiguration\\" + filename;
		boolean success = new File(fileS).mkdirs();
        try
        {
            File file = new File(fileS + "\\BRSTrustModelConfiguration.ini");
            PrintWriter output = new PrintWriter(file);
            output.print("quantile=");
            output.println(this.textfield.getText());
            output.close();
            fileS = file.getCanonicalPath();
        }
        catch (Exception ex)
        {
            System.out.println("IO Exception occured");    
        }
        this.setTextField();
        return fileS;
    }
    
    public void focusGained(FocusEvent e) {
		textfield.setForeground(Color.black);
		if (textfield.getText().equalsIgnoreCase(DEFAULT))
		{
			textfield.setText("");
		}
	}

	public void focusLost(FocusEvent e) {}
	
	public void setTextField()
	{
		Color color = Color.gray;
		textfield.setForeground(color);
		textfield.setText(DEFAULT);
		textfield.setToolTipText(DEFAULT);	
	}

    
       
}