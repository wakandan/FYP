//package Marketplace;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;

public class SimConfigPanel extends JPanel implements FocusListener {
	SpringLayout layout = new SpringLayout();
	String[] labels = {"Credit Per Turn:", "Agent Config Class:", "Agent Config File:", "Product Config File:", "Agent Master Config File:", "Scheduler Config File:"};
        JLabel[] label = new JLabel[6];
        JTextField[] textfield = new JTextField[6];
        JButton next_agent_config;
	
	public SimConfigPanel()
	{
		super();
                this.setLayout(layout);
		
                next_agent_config = new JButton("Config");
                this.add(next_agent_config);
                
		for (int i = 0; i < label.length; i++)
                {
                    label[i] = new JLabel(labels[i]);
                    textfield[i] = new JTextField(20);
                    this.add(label[i]);
                    //this.setText(textfield[i], i);
                    this.add(textfield[i]);
                    textfield[i].addFocusListener(this);
                }
                
                textfield[0].setText("<For Example: 100>");
                textfield[1].setText("<For Example: configbase.AgentConfigSimple>");
                textfield[2].setText("<For Example: generatorbase/TestAgentModelConfig.ini>");
                textfield[3].setText("<For Example: generatorbase/TestProductConfig.ini>");        
                textfield[4].setText("<For Example: configbase/testAgentMasterConfigTruthful.ini>");
                textfield[5].setText("<For Example: simbase/SchedulerConfig.ini>");
          
                
                layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);
                
                layout.putConstraint(SpringLayout.WEST, textfield[0], 123, SpringLayout.EAST, label[0]);
                layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
                
                for (int i = 1; i < label.length; i++)
                {
                    layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
                    layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH, textfield[i-1]);
                    
                    layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST, textfield[i-1]);
                    layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH, textfield[i-1]);
                    layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST, textfield[0]);
                }
                
                layout.putConstraint(SpringLayout.WEST, next_agent_config, 0, SpringLayout.WEST, textfield[0]);
                layout.putConstraint(SpringLayout.NORTH, next_agent_config, 5, SpringLayout.SOUTH, textfield[textfield.length - 1]);
                layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_agent_config);
                
                this.setVisible(true);
	}
        
        public void configuration()
        {
            try
            {
                File file = new File("SimulationConfiguration.ini");
                int i = 0;
                while (file.exists())
                {
                    file = new File("SimulationConfiguration" + i +".ini");
                    i++;
                }
                PrintWriter output = new PrintWriter(file);
                output.print("creditPerTurn=");
                output.println(this.textfield[0].getText());
                output.print("agentConfigClass=");
                output.println(this.textfield[1].getText());
                output.print("agentConfigFile=");
                output.println(this.textfield[2].getText());
                output.print("productConfigFile=");
                output.println(this.textfield[3].getText());
                output.print("agentMasterConfigFile=");
                output.println(this.textfield[4].getText());
                output.print("schedulerConfigFile=");
                output.println(this.textfield[5].getText());
                output.close();
            }
            catch (Exception ex)
            {
                System.out.println("IO Exception occured");
            }
            for (int i = 0; i < textfield.length; i++)
            {
                textfield[i].setText("");
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
