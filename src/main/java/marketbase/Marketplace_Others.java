package marketbase;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class Marketplace_Others extends JPanel implements FocusListener
{
    SpringLayout layout = new SpringLayout();
    JLabel label;
    final String DEFAULT = "<For Example: trustmodel.TrustModelTRAVOS>";
    JTextField textfield;
    
    public Marketplace_Others()
    {
        this.setLayout(layout);
        
        label = new JLabel("Others Trust Model File Location:");
        textfield = new JTextField(20);
        this.add(label);
        this.add(textfield);
        
        textfield.addFocusListener(this);
        
        this.setTextField();
        
        layout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 10, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, textfield, 24, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textfield, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, textfield, -10, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, textfield);
        
        this.setVisible(false);
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
       
