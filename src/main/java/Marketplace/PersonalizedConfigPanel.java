import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class PersonalizedConfigPanel extends JPanel implements FocusListener, ActionListener
{
    SpringLayout layout = new SpringLayout();
    String[] labels = {"Epsilon:", "Gamma:", "Forgetting:", "Time Window:"};
    JLabel[] label = new JLabel[4];
    JTextField[] textfield = new JTextField[4];
    JButton next_config;
    
    public PersonalizedConfigPanel()
    {
        this.setLayout(layout);
        
        for (int i = 0; i< label.length; i++)
        {
            label[i] = new JLabel(labels[i]);
            textfield[i] = new JTextField(20);
            this.add(label[i]);
            this.add(textfield[i]);
            textfield[i].addFocusListener(this);
        }
        
        textfield[0].setText("<For Example: 0.4>");
        textfield[1].setText("<For Example: 0.5>");
        textfield[2].setText("<For Example: 0.5>");
        textfield[3].setText("<For Example: 5>");
        
        next_config = new JButton("Config");
        this.add(next_config);
        next_config.addActionListener(this);
       
        layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);
                
        layout.putConstraint(SpringLayout.WEST, textfield[0], 148, SpringLayout.EAST, label[0]);
        layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
                
        for (int i = 1; i < label.length; i++)
        {
             layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
             layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH, textfield[i-1]);
                    
             layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST, textfield[i-1]);
             layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH, textfield[i-1]);
             layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST, textfield[0]);
        }
                
        layout.putConstraint(SpringLayout.WEST, next_config, 0, SpringLayout.WEST, textfield[0]);
        layout.putConstraint(SpringLayout.NORTH, next_config, 5, SpringLayout.SOUTH, textfield[textfield.length - 1]);
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_config);
        
        this.setVisible(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            File file = new File("PersonalizedTrustModelConfiguration.ini");
            int i = 0;
            while(file.exists())
            {
                file = new File("PersonalizedTrustModelConfiguration" + i + ".ini");
                i++;
            }
            
            PrintWriter output = new PrintWriter(file);
            output.print("epsilon=");
            output.println(this.textfield[0].getText());
            output.print("gamme=");
            output.println(this.textfield[1].getText());
            output.print("forgetting=");
            output.println(this.textfield[2].getText());
            output.print("timeWindow=");
            output.println(this.textfield[3].getText());
            output.close();
        }
        catch (Exception ex)
        {
            System.out.println("IO Exception occured");    
        }
        for (int i = 0; i < textfield.length; i++)
        {
            this.textfield[i].setText("");
        }
    }
    
    @Override
    public void focusGained(FocusEvent e)
    {
        for (int i = 0; i < textfield.length; i++)
            {
                if (e.getSource() == textfield[i])
                {
                    textfield[i].setText("");
                }
            }
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {
        
    }
}