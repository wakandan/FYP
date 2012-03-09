package marketbase;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Marketplace_Simulation extends JPanel implements FocusListener, ActionListener{
	SpringLayout layout = new SpringLayout();
	String[] labels = {"Cerdit per turn:", "Agent Config Class Name: "};
	final String[] DEFAULT = {
							"<For Example:100>",
							"<For Example: configbase.AgentConfigSimple>"
							 };
	JLabel[] label = new JLabel[2];
	JTextField[] textfield = new JTextField[2];
	Marketplace_Personnel agentConfig;
	Marketplace_Goods productConfig;
	Marketplace_Schedule schedConfig;
	Marketplace_Master masterConfig;
	Marketplace_BRS brsConfig;
	Marketplace_TRAVOS travosConfig;
	Marketplace_Personalized personalizedConfig;
	Marketplace_Others othersConfig;
	
	public Marketplace_Simulation()
	{
		this.setLayout(layout);
		
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(labels[i]);
			textfield[i] = new JTextField(20);
			this.add(label[i]);
			// this.setText(textfield[i], i);
			this.add(textfield[i]);
			textfield[i].addFocusListener(this);
		}
		
		this.setTextField();
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);

		agentConfig = new Marketplace_Personnel();
		TitledBorder agentTitle = BorderFactory
				.createTitledBorder(blackline, "Agent Configuration");
		agentConfig.setBorder(agentTitle);

		productConfig = new Marketplace_Goods();
		TitledBorder productTitle = BorderFactory.createTitledBorder(blackline,
				"Product Configuration");
		productConfig.setBorder(productTitle);

		schedConfig = new Marketplace_Schedule();
		TitledBorder schedTitle = BorderFactory.createTitledBorder(blackline,
				"Scheduler Configuration");
		schedConfig.setBorder(schedTitle);
		
		masterConfig = new Marketplace_Master();
		TitledBorder masTitle = BorderFactory.createTitledBorder(blackline,
				"Agent Master Configuration");
		masterConfig.setBorder(masTitle);
		masterConfig.combo.addActionListener(this);
		
		brsConfig = new Marketplace_BRS();
		TitledBorder brsTitle = BorderFactory.createTitledBorder(blackline,
				"BRS Trust Model Configuration");
		brsConfig.setBorder(brsTitle);
		
		travosConfig = new Marketplace_TRAVOS();
		TitledBorder travosTitle = BorderFactory.createTitledBorder(blackline,
				"TRAVOS Trust Model Configuration");
		travosConfig.setBorder(travosTitle);
		
		personalizedConfig = new Marketplace_Personalized();
		TitledBorder personTitle = BorderFactory.createTitledBorder(blackline,
				"Personalized Trust Model Configuration");
		personalizedConfig.setBorder(personTitle);
		
		othersConfig = new Marketplace_Others();
		TitledBorder othersTitle = BorderFactory.createTitledBorder(blackline,
				"Others Trust Model Configuration");
		othersConfig.setBorder(travosTitle);
		
		this.add(agentConfig);
		this.add(productConfig);
		this.add(schedConfig);
		this.add(masterConfig);
		this.add(brsConfig);
		this.add(travosConfig);
		this.add(personalizedConfig);
		this.add(othersConfig);
		
		layout.putConstraint(SpringLayout.WEST, label[0], 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, textfield[0], 138, SpringLayout.EAST, label[0]);
		layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, this, 25, SpringLayout.EAST, textfield[0]);
		
		layout.putConstraint(SpringLayout.WEST, label[1], 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label[1], 5, SpringLayout.SOUTH, textfield[0]);
		
		layout.putConstraint(SpringLayout.WEST, textfield[1], 0, SpringLayout.WEST, textfield[0]);
		layout.putConstraint(SpringLayout.NORTH, textfield[1], 5, SpringLayout.SOUTH, textfield[0]);
		layout.putConstraint(SpringLayout.EAST, textfield[1], 0, SpringLayout.EAST, textfield[0]);
		
		
		
		layout.putConstraint(SpringLayout.WEST, agentConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, agentConfig, 10, SpringLayout.SOUTH, textfield[1]);
		layout.putConstraint(SpringLayout.EAST, agentConfig, -10, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.WEST, productConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, productConfig, 10, SpringLayout.SOUTH, agentConfig);
		layout.putConstraint(SpringLayout.EAST, productConfig, 0, SpringLayout.EAST, agentConfig);

		layout.putConstraint(SpringLayout.WEST, schedConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, schedConfig, 10, SpringLayout.SOUTH, productConfig);
		layout.putConstraint(SpringLayout.EAST, schedConfig, 0, SpringLayout.EAST, agentConfig);
		
		layout.putConstraint(SpringLayout.SOUTH, this, 10, SpringLayout.SOUTH, schedConfig);
		
		layout.putConstraint(SpringLayout.WEST, masterConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, masterConfig, 10, SpringLayout.SOUTH, textfield[1]);
		layout.putConstraint(SpringLayout.EAST, masterConfig, 0, SpringLayout.EAST, agentConfig);
		
		layout.putConstraint(SpringLayout.WEST, brsConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, brsConfig, 10, SpringLayout.SOUTH, masterConfig);
		layout.putConstraint(SpringLayout.EAST, brsConfig, 0, SpringLayout.EAST, masterConfig);
		
		layout.putConstraint(SpringLayout.WEST, travosConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, travosConfig, 10, SpringLayout.SOUTH, masterConfig);
		layout.putConstraint(SpringLayout.EAST, travosConfig, 0, SpringLayout.EAST, masterConfig);
		
		layout.putConstraint(SpringLayout.WEST, personalizedConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, personalizedConfig, 10, SpringLayout.SOUTH, masterConfig);
		layout.putConstraint(SpringLayout.EAST, personalizedConfig, 0, SpringLayout.EAST, masterConfig);
		
		layout.putConstraint(SpringLayout.WEST, othersConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, othersConfig, 10, SpringLayout.SOUTH, masterConfig);
		layout.putConstraint(SpringLayout.EAST, othersConfig, 0, SpringLayout.EAST, masterConfig);
		
		this.setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e)
	{ 
		if (e.getSource().equals(masterConfig.combo))
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
	}
	
	public void focusGained(FocusEvent e) {
		for (int i = 0; i < textfield.length; i++) {
			if (e.getSource() == textfield[i]) {
				textfield[i].setForeground(Color.black);
				if (textfield[i].getText().equalsIgnoreCase(DEFAULT[i]))
				{
					textfield[i].setText("");
				}
			}
		}
	}

	public void focusLost(FocusEvent e) {}
	
	public void setTextField()
	{
		Color color = Color.gray;
		for (int i = 0; i < textfield.length; i++)
		{
			textfield[i].setForeground(color);
			textfield[i].setText(DEFAULT[i]);
			textfield[i].setToolTipText(DEFAULT[i]);
		}	
	}
	
	public String[] configuration(String filename)
	{
		String fileS = "SavedConfiguration\\" + filename;
		String[] key = new String[5];
		boolean success = new File(fileS).mkdirs();
		try {
			File file = new File(fileS + "\\SimulationConfiguration.ini");
			PrintWriter output = new PrintWriter(file);
			output.print("creditPerTurn=");
			output.println(this.textfield[0].getText());
			output.print("agentConfigClass=");
			output.println(this.textfield[1].getText());
			output.print("agentConfigFile=");
			key[0] = agentConfig.configuration(filename);
			output.println(key[0]);
			output.print("productConfigFile=");
			key[1] = productConfig.configuration(filename);
			output.println(key[1]);
			output.print("agentMasterConfigFile=");
			key[2] = masterConfig.configuration(filename);
			output.println(key[2]);
			output.print("schedulerConfigFile=");
			key[3] = schedConfig.configuration(filename);
			output.println(key[3]);
			output.close();
			key[4] = file.getAbsolutePath();
		} catch (Exception ex) {
			System.out.println("IO Exception occured");
		}
		this.setTextField();
		return key;	
	}
	
	public void importConfig(String[] configFile)
	{
		File file = new File(configFile[0]);
		String key = null;
		int i = 0;
		try
		{
			Scanner input = new Scanner(file);
			while (input.hasNext())
			{
				key = input.nextLine();
				String[] partKey = key.split("=", 0);
				if (i < 2)
				{
					textfield[i].setForeground(Color.black);
					textfield[i].setText(partKey[1]);
				}
				else
				{
					agentConfig.importConfig(configFile[1]);
					productConfig.importConfig(configFile[2]);
					schedConfig.importConfig(configFile[3]);
					masterConfig.importConfig(configFile[4]);
				}
				i++;
			}
		}
		catch (Exception e)
		{
			System.out.println("File Not Found!!!");
		}
	}
}




/*
package marketbase;

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

public class Marketplace_Master extends JPanel implements FocusListener, ActionListener{
	SpringLayout layout = new SpringLayout();
    String[] labels = {"Number Of Agents:", "Agent Master Name:", "Purchase Logic Class Name:", "Rating Logic Class Name:", "WishList:", "Trust Model Logic Class:"};
    String[] trust = {"", "BRS", "TRAVOS", "Personalized", "Others"};
    JLabel[] label = new JLabel[6];
    JTextField[] textfield = new JTextField[5];
    Marketplace_BRS brsConfig;
    Marketplace_TRAVOS travosConfig;
    Marketplace_Personalized personalizedConfig;
    Marketplace_Others othersConfig;
    JButton next_config;
    JComboBox combo;
    
    
    public Marketplace_Master()
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
        
        brsConfig = new Marketplace_BRS();
        TitledBorder brsTitle = BorderFactory.createTitledBorder(blackline, "BRS Trust Model Configuration");
        brsConfig.setBorder(brsTitle);
        
        travosConfig = new Marketplace_TRAVOS();
        TitledBorder travosTitle = BorderFactory.createTitledBorder(blackline, "TRAVOS Trust Model Configuration");
        travosConfig.setBorder(travosTitle);
        
        personalizedConfig = new Marketplace_Personalized();
        TitledBorder personalizedTitle = BorderFactory.createTitledBorder(blackline, "Personalized Trust Model Configuration");
        personalizedConfig.setBorder(personalizedTitle);
                
        othersConfig = new Marketplace_Others();
        TitledBorder othersTitle = BorderFactory.createTitledBorder(blackline, "Others Trust Model Configuration");
        othersConfig.setBorder(othersTitle);
        
        this.add(brsConfig);
        this.add(travosConfig);
        this.add(personalizedConfig);
        this.add(othersConfig);
        
        layout.putConstraint(SpringLayout.WEST, label[0], 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label[0], 10, SpringLayout.NORTH, this);
                
        layout.putConstraint(SpringLayout.WEST, textfield[0], 95, SpringLayout.EAST, label[0]);
        layout.putConstraint(SpringLayout.NORTH, textfield[0], 10, SpringLayout.NORTH, this);
                
        for (int i = 1; i < textfield.length; i++)
        {
             layout.putConstraint(SpringLayout.WEST, label[i], 10, SpringLayout.WEST, this);
             layout.putConstraint(SpringLayout.NORTH, label[i], 10, SpringLayout.SOUTH, textfield[i-1]);
             
             layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST, textfield[i-1]);
             layout.putConstraint(SpringLayout.NORTH, textfield[i], 10, SpringLayout.SOUTH, textfield[i-1]);
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
        //layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, next_config);
        
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
    
    public void focusLost(FocusEvent e)
    {
        
    }

}*/
