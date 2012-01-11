//package Marketplace;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class Marketplace_Config extends JPanel implements ActionListener {
        SpringLayout layout = new SpringLayout();
	SimConfigPanel simConfig;
	AgentConfigPanel agentConfig;
        ProductConfigPanel productConfig;
        SchedConfigPanel schedConfig;
        MasterConfigPanel masConfig;
        
	public Marketplace_Config()
	{
            this.setLayout(layout);
            Border blackline = BorderFactory.createLineBorder(Color.BLACK);
            
            simConfig = new SimConfigPanel();
            TitledBorder simTitle = BorderFactory.createTitledBorder(blackline, "Simulation Configuration");
            simConfig.setBorder(simTitle);
            simConfig.next_agent_config.addActionListener(this);
            
            agentConfig = new AgentConfigPanel();
            TitledBorder agentTitle = BorderFactory.createTitledBorder(blackline, "Agent Configuration");
            agentConfig.setBorder(agentTitle);
            agentConfig.next_product_config.addActionListener(this);
            
            
            productConfig = new ProductConfigPanel();
            TitledBorder productTitle = BorderFactory.createTitledBorder(blackline, "Product Configuration");
            productConfig.setBorder(productTitle);
            productConfig.next_sched_config.addActionListener(this);
            
            schedConfig = new SchedConfigPanel();
            TitledBorder schedTitle = BorderFactory.createTitledBorder(blackline, "Scheduler Configuration");
            schedConfig.setBorder(schedTitle);
            schedConfig.next_master_config.addActionListener(this);
            
            masConfig = new MasterConfigPanel();
            TitledBorder masTitle = BorderFactory.createTitledBorder(blackline, "Agent Master Configuration");
            masConfig.setBorder(masTitle);
            masConfig.next_config.addActionListener(this);
            
            
            this.add(simConfig);
            this.add(agentConfig);
            this.add(productConfig);
            this.add(schedConfig);
            this.add(masConfig);
            
            layout.putConstraint(SpringLayout.WEST, simConfig, 10, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, simConfig, 10, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.EAST, simConfig, 500, SpringLayout.WEST, this);
            
            layout.putConstraint(SpringLayout.WEST, agentConfig, 10, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, agentConfig, 10, SpringLayout.SOUTH, simConfig);
            layout.putConstraint(SpringLayout.EAST, agentConfig, 0, SpringLayout.EAST, simConfig);
            
            layout.putConstraint(SpringLayout.WEST, productConfig, 10, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, productConfig, 10, SpringLayout.SOUTH, agentConfig);
            layout.putConstraint(SpringLayout.EAST, productConfig, 0, SpringLayout.EAST, simConfig);
            
            layout.putConstraint(SpringLayout.WEST, schedConfig, 10, SpringLayout.EAST, simConfig);
            layout.putConstraint(SpringLayout.NORTH, schedConfig, 10, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.EAST, this, 10, SpringLayout.EAST, schedConfig);
            
            layout.putConstraint(SpringLayout.WEST, masConfig, 0, SpringLayout.WEST, schedConfig);
            layout.putConstraint(SpringLayout.NORTH, masConfig, 10, SpringLayout.SOUTH, schedConfig);
            layout.putConstraint(SpringLayout.EAST, masConfig, 0, SpringLayout.EAST, schedConfig);
            
            this.setVisible(true);
            this.setPreferredSize(new Dimension(1000, 670));
	}
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == simConfig.next_agent_config)
            {
                simConfig.configuration();
                agentConfig.setVisible(true);
            }
            else if (e.getSource() == agentConfig.next_product_config)
            {
                agentConfig.configuration();
                productConfig.setVisible(true);
            }
            else if (e.getSource() == productConfig.next_sched_config)
            {
                productConfig.configuration();
                schedConfig.setVisible(true);
            }
            else if (e.getSource() == schedConfig.next_master_config)
            {
                schedConfig.configuration();
                masConfig.setVisible(true);
            }
            else if (e.getSource() == masConfig.next_config)
            {
                masConfig.configuration();
            }
            
            
        }
}
