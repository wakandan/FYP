import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class OthersConfigPanel extends JPanel implements FocusListener
{
    SpringLayout layout = new SpringLayout();
    JTextField textfield;
    JButton next_config;
    
    public OthersConfigPanel()
    {
        this.setLayout(layout);
        
        textfield = new JTextField(20);
        
        this.add(textfield);
        textfield.setText("<For Example: trustmodel.TrustModelTRAVOS>");
        textfield.addFocusListener(this);
        
        next_config = new JButton("Add");
        this.add(next_config);
        
        layout.putConstraint(SpringLayout.WEST, textfield, 197, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, textfield, 5, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, next_config, 0, SpringLayout.WEST, textfield);
        layout.putConstraint(SpringLayout.NORTH, next_config, 5, SpringLayout.SOUTH, textfield);
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_config);
        
        this.setVisible(false);
    }
    
    @Override
    public void focusGained(FocusEvent e)
    {
        textfield.setText("");
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {
        
    }
}
       
