import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.io.File;
import java.io.PrintWriter;

public class MasterConfigPanel extends JPanel implements FocusListener, ActionListener
{
    SpringLayout layout = new SpringLayout();
    String[] labels = {"Number Of Agents:", "Agent Master Name:", "Purchase Logic Class Name:", "Rating Logic Class Name:", "WishList:", "Trust Model Logic Class:"};
    String[] trust = {"", "BRS", "TRAVOS", "Personalized", "Others"};
    JLabel[] label = new JLabel[6];
    JTextField[] textfield = new JTextField[5];
    BRSConfigPanel brsConfig;
    TRAVOSConfigPanel travosConfig;
    PersonalizedConfigPanel personalizedConfig;
    OthersConfigPanel othersConfig;
    JButton next_config;
    JComboBox combo;
    
    
    public MasterConfigPanel()
    {
        this.setLayout(layout);
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        
        for (int i = 0; i< textfield.length; i++)
        {
            label[i] = new JLabel(labels[i]);
            textfield[i] = new JTextField(20);
            this.add(label[i]);
            this.add(textfield[i]);
            textfield[i].addFocusListener(this);   
        }
        
        label[5] = new JLabel(labels[5]);
        this.add(label[5]);
        
        combo = new JComboBox(trust);
        combo.setSelectedIndex(0);
        combo.addActionListener(this);
        this.add(combo);
        
        textfield[0].setText("<For Example: 10>");
        textfield[1].setText("<For Example: AM10>");
        textfield[2].setText("<For Example: modelbase.PurchaseLogicCollusion>");
        textfield[3].setText("<For Example: modelbase.RatingLogicCollusion>");
        textfield[4].setText("<For Example: 0,4,5,7,9>");
        
        next_config = new JButton("Config");
        this.add(next_config);
        
        brsConfig = new BRSConfigPanel();
        TitledBorder brsTitle = BorderFactory.createTitledBorder(blackline, "BRS Trust Model Configuration");
        brsConfig.setBorder(brsTitle);
        
        travosConfig = new TRAVOSConfigPanel();
        TitledBorder travosTitle = BorderFactory.createTitledBorder(blackline, "TRAVOS Trust Model Configuration");
        travosConfig.setBorder(travosTitle);
        
        personalizedConfig = new PersonalizedConfigPanel();
        TitledBorder personalizedTitle = BorderFactory.createTitledBorder(blackline, "Personalized Trust Model Configuration");
        personalizedConfig.setBorder(personalizedTitle);
                
        othersConfig = new OthersConfigPanel();
        TitledBorder othersTitle = BorderFactory.createTitledBorder(blackline, "Others Trust Model Configuration");
        othersConfig.setBorder(othersTitle);
        
        this.add(brsConfig);
        this.add(travosConfig);
        this.add(personalizedConfig);
        this.add(othersConfig);
        
        layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);
                
        layout.putConstraint(SpringLayout.WEST, textfield[0], 95, SpringLayout.EAST, label[0]);
        layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
                
        for (int i = 1; i < textfield.length; i++)
        {
             layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
             layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH, textfield[i-1]);
             
             layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST, textfield[i-1]);
             layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH, textfield[i-1]);
             layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST, textfield[0]);
        }
        
        layout.putConstraint(SpringLayout.WEST, label[5], 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label[5], 5, SpringLayout.SOUTH, textfield[4]);
        
        layout.putConstraint(SpringLayout.WEST, combo, 0, SpringLayout.WEST, textfield[4]);
        layout.putConstraint(SpringLayout.NORTH, combo, 5, SpringLayout.SOUTH, textfield[4]);
        layout.putConstraint(SpringLayout.EAST, combo, 0, SpringLayout.EAST, textfield[0]);
        
        layout.putConstraint(SpringLayout.WEST, brsConfig, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, brsConfig, 5, SpringLayout.SOUTH, combo);
        layout.putConstraint(SpringLayout.EAST, brsConfig, 5, SpringLayout.EAST, combo);
        
        layout.putConstraint(SpringLayout.WEST, travosConfig, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, travosConfig, 5, SpringLayout.SOUTH, combo);
        layout.putConstraint(SpringLayout.EAST, travosConfig, 5, SpringLayout.EAST, combo);
        
        layout.putConstraint(SpringLayout.WEST, personalizedConfig, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, personalizedConfig, 5, SpringLayout.SOUTH, combo);
        layout.putConstraint(SpringLayout.EAST, personalizedConfig, 5, SpringLayout.EAST, combo);
        
        layout.putConstraint(SpringLayout.WEST, othersConfig, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, othersConfig, 5, SpringLayout.SOUTH, combo);
        layout.putConstraint(SpringLayout.EAST, othersConfig, 5, SpringLayout.EAST, combo);
                
        layout.putConstraint(SpringLayout.WEST, next_config, 0, SpringLayout.WEST, textfield[0]);
        layout.putConstraint(SpringLayout.NORTH, next_config, 5, SpringLayout.SOUTH, personalizedConfig);
        layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_config);
        
        this.setVisible(false);
    }
    
    public void configuration()
    {
        try
        {
            File file = new File("AgentMasterConfiguration.ini");
            int i = 0;
            while(file.exists())
            {
                file = new File("AgentMasterConfiguration" + i + ".ini");
                i++;
            }
            
            PrintWriter output = new PrintWriter(file);
            output.print("agentNum=");
            output.println(this.textfield[0].getText());
            output.print("masterName=");
            output.println(this.textfield[1].getText());
            output.print("purchaseLogicClass=");
            output.println(this.textfield[2].getText());
            output.print("ratingLogicClass=");
            output.println(this.textfield[3].getText());
            output.print("trustModelClass=");
            String command = (String) combo.getSelectedItem();
            if (command.equalsIgnoreCase("BRS"))
            {
                output.println("trustmodel.TrustModelBRS");
            }
            else if (command.equalsIgnoreCase("TRAVOS"))
            {
                output.println("trustmodel.TrustModelTRAVOS");
            }
            else if (command.equalsIgnoreCase("Personalized"))
            {
                output.println("trustmodel.TrustModelPersonalized");
            }
            else if (command.equalsIgnoreCase("Others"))
            {
                output.println(othersConfig.textfield.getText());
            }
            else
            {
                output.println();
            }
            output.print("wishlist=");
            output.println(this.textfield[4].getText());
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
    public void actionPerformed(ActionEvent e)
    {
        JComboBox cb = (JComboBox) e.getSource();
        String command = (String) cb.getSelectedItem();
        brsConfig.setVisible(false);
        travosConfig.setVisible(false);
        personalizedConfig.setVisible(false);
        othersConfig.setVisible(false);
        if (command.equalsIgnoreCase("BRS"))
        {
            brsConfig.setVisible(true);
        }
        else if (command.equalsIgnoreCase("TRAVOS"))
        {
            travosConfig.setVisible(true);
        }
        else if (command.equalsIgnoreCase("Personalized"))
        {
            personalizedConfig.setVisible(true);
        }
        else if (command.equalsIgnoreCase("Others"))
        {
            othersConfig.setVisible(true);
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