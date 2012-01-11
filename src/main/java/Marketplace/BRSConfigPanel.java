import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class BRSConfigPanel extends JPanel implements FocusListener, ActionListener
{
    SpringLayout layout = new SpringLayout();
    JLabel label;
    JTextField textfield;
    JButton next_config;
    
    public BRSConfigPanel()
    {
        this.setLayout(layout);
        
        label = new JLabel("Quantile");
        textfield = new JTextField(20);
        
        this.add(label);
        this.add(textfield);
        textfield.setText("<For Example: 0.1>");
        textfield.addFocusListener(this);
        
        
        next_config = new JButton("Config");
        this.add(next_config);
        next_config.addActionListener(this);
        
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, textfield, 145, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textfield, 5, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, next_config, 0, SpringLayout.WEST, textfield);
        layout.putConstraint(SpringLayout.NORTH, next_config, 5, SpringLayout.SOUTH, textfield);
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_config);
        
        this.setVisible(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            File file = new File("BRSTrustModelConfiguration.ini");
            int i = 0;
            while(file.exists())
            {
                file = new File("BRSTrustModelConfiguration" + i + ".ini");
                i++;
            }
            
            PrintWriter output = new PrintWriter(file);
            output.print("quantile=");
            output.println(this.textfield.getText());
            output.close();
        }
        catch (Exception ex)
        {
            System.out.println("IO Exception occured");    
        }
        this.textfield.setText("");
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