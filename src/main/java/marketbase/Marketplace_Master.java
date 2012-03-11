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
import java.util.Scanner;

public class Marketplace_Master extends JPanel implements FocusListener{
	SpringLayout layout = new SpringLayout();
    String[] labels = {"Number Of Agents:", "Agent Master Name:", "Purchase Logic Class Name:", "Rating Logic Class Name:", "WishList:", "Trust Model Logic Class:"};
    String[] trust = {"", "BRS", "TRAVOS", "Personalized", "Others"};
    final String[] DEFAULT = {
    		"<For Example: 10>",
            "<For Example: AM10>",
            "<For Example: modelbase.PurchaseLogicCollusion>",
            "<For Example: modelbase.RatingLogicCollusion>",
            "<For Example: 0,4,5,7,9>"			
    };
    JLabel[] label = new JLabel[6];
    JTextField[] textfield = new JTextField[5];
    JComboBox combo;
    
    
    public Marketplace_Master()
    {
        this.setLayout(layout);
        
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
        this.add(combo);
        
        this.setTextField();
        
        layout.putConstraint(SpringLayout.WEST, label[0], 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label[0], 10, SpringLayout.NORTH, this);
                
        layout.putConstraint(SpringLayout.WEST, textfield[0], 101, SpringLayout.EAST, label[0]);
        layout.putConstraint(SpringLayout.NORTH, textfield[0], 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, textfield[0], -10, SpringLayout.EAST, this);
                
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
        layout.putConstraint(SpringLayout.SOUTH, this, 10, SpringLayout.SOUTH, combo);
            
        this.setVisible(false);
    }
    
    public String configuration(String filename) 
    {
		String fileS = "SavedConfiguration\\" + filename;
		boolean success = new File(fileS).mkdirs();
        try
        {
            File file = new File(fileS + "\\AgentMasterConfiguration.ini");
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
            	
            }
            else
            {
                output.println();
            }
            output.print("wishlist=");
            output.println(this.textfield[4].getText());
            output.close();
            fileS = file.getAbsolutePath();
        }
        catch (Exception ex)
        {
            System.out.println("IO Exception occured");    
        }
        this.setTextField();
        return fileS;
    }
    
    public void importConfig(String filename)
	{
		File file = new File(filename);
		String[] key = null;
		int i = 0;
		
		try
		{
			Scanner reader = new Scanner(file);
			
			while (reader.hasNext())
			{
				String data = reader.nextLine();
				key = data.split("=", 0);
				textfield[i].setForeground(Color.black);
				textfield[i].setText(key[1]);
				i++;
			}
			
			reader.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
}

