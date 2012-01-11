import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class SchedConfigPanel extends JPanel implements FocusListener
{
    SpringLayout layout = new SpringLayout();
    String[] labels = {"Maximum Time Step:", "Warm Up Period:"};
    JLabel[] label = new JLabel[2];
    JTextField[] textfield = new JTextField[2];
    JButton next_master_config;
    
    public SchedConfigPanel()
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
        textfield[0].setText("<For Example: 100>");
        textfield[1].setText("<For Example: 5>");
        
        next_master_config = new JButton("Config");
        this.add(next_master_config);
        
        layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);
                
        layout.putConstraint(SpringLayout.WEST, textfield[0], 95, SpringLayout.EAST, label[0]);
        layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
                
        for (int i = 1; i < label.length; i++)
        {
             layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
             layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH, textfield[i-1]);
                    
             layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST, textfield[i-1]);
             layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH, textfield[i-1]);
             layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST, textfield[0]);
        }
                
        layout.putConstraint(SpringLayout.WEST, next_master_config, 0, SpringLayout.WEST, textfield[0]);
        layout.putConstraint(SpringLayout.NORTH, next_master_config, 5, SpringLayout.SOUTH, textfield[textfield.length - 1]);
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_master_config);
        
        this.setVisible(false);
    }
    
    public void configuration()
    {
        try
        {
            File file = new File("SchedulerConfiguration.ini");
            int i = 0;
            while (file.exists())
            {
                file = new File("SchedulerConfigraion" + i + ".ini");
                i++;
            }
            
            PrintWriter output = new PrintWriter(file);
            output.print("maxTimeStep=");
            output.println(this.textfield[0].getText());
            output.print("warmupPeriod=");
            output.println(this.textfield[1].getText());
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