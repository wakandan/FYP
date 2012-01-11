import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class ProductConfigPanel extends JPanel implements FocusListener
{
    SpringLayout layout = new SpringLayout();
    
    String[] labels = {"Number Of Type:", "Number Of Product:", "Maximum Price:", "Minimum Price:", "Number Of Categories:", "Quantity Assignment Threadshold:", "Distribution Class Name:", "Distribution Config Class Name:", "Distribution Config File Name:"}; 
    JLabel[] label = new JLabel[9];
    JTextField[] textfield = new JTextField[9];
    JButton next_sched_config;
    
    public ProductConfigPanel()
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
        
        textfield[0].setText("<For Example: 50>");
        textfield[1].setText("<For Example: 10000>");
        textfield[2].setText("<For Example: 1000>");
        textfield[3].setText("<For Example: 1>");
        textfield[4].setText("<For Example: 15>");
        textfield[5].setText("<For Example: 10>");
        textfield[6].setText("<For Example: generatorbase.NormalDistribution>");
        textfield[7].setText("<For Example: configbase.NormalDistributionConfig");
        textfield[8].setText("<For Example: src/test/resources/configbase/testNormalDistributionConfig.ini");   
        
        next_sched_config = new JButton("Config");
        this.add(next_sched_config);
        
        layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);
                
        layout.putConstraint(SpringLayout.WEST, textfield[0], 120, SpringLayout.EAST, label[0]);
        layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
                
        for (int i = 1; i < label.length; i++)
        {
             layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
             layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH, textfield[i-1]);
                    
             layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST, textfield[i-1]);
             layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH, textfield[i-1]);
             layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST, textfield[0]);
        }
                
        layout.putConstraint(SpringLayout.WEST, next_sched_config, 0, SpringLayout.WEST, textfield[0]);
        layout.putConstraint(SpringLayout.NORTH, next_sched_config, 5, SpringLayout.SOUTH, textfield[textfield.length - 1]);
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_sched_config);
        
        this.setVisible(false);
    }
    
    public void configuration()
    {
        try
        {
            File file = new File("ProductConfiguration.ini");
            int i = 0;
            while (file.exists())
            {
                file = new File("ProductConfiguration" + i + ".ini");
                i++;
            }
            
            PrintWriter output = new PrintWriter(file);
            output.print("numTypes=");
            output.println(this.textfield[0].getText());
            output.print("numProducts=");
            output.println(this.textfield[1].getText());
            output.print("priceMax=");
            output.println(this.textfield[2].getText());
            output.print("priceMin=");
            output.println(this.textfield[3].getText());
            output.print("numCategories=");
            output.println(this.textfield[4].getText());
            output.print("quantityAssignmentThreadholds=");
            output.println(this.textfield[5].getText());
            output.print("distributionClassName=");
            output.println(this.textfield[6].getText());
            output.print("distributionConfigClassName=");
            output.println(this.textfield[7].getText());
            output.print("distributionConfigFile=");
            output.println(this.textfield[8].getText());      
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