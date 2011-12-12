package Marketplace;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import Interfaces.MarketEntityInterface;

public class Marketplace_Listener implements ActionListener {

	Marketplace_Controls marketControls;
	
	public Marketplace_Listener(Marketplace_Controls marketControls)
	{
		this.marketControls = marketControls;
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equalsIgnoreCase("Import Configuration"))
		{
			new Marketplace_Reader(marketControls.market,"Files/marketplace_parameter.txt");
		}
		else if(e.getActionCommand().equalsIgnoreCase("Save Configuration"))
		{
			//do something
		}
		else if(e.getActionCommand().equalsIgnoreCase("Reset"))
		{
			for(int i = 0;i <marketControls.market.getComponentCount();i++)
				if(marketControls.market.getComponent(i) instanceof MarketEntityInterface)
					((MarketEntityInterface)marketControls.market.getComponent(i)).resetText();
		}
	}

}
