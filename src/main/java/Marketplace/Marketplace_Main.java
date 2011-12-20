package Marketplace;
import javax.swing.*;

import java.awt.*;

//Main interface for marketplace configuration
public class Marketplace_Main extends JPanel {
	
	Marketplace_Controls marketControls = new Marketplace_Controls(this);
	JPanel listOfPanels[] = {new Marketplace_Personnel(),new Marketplace_Goods(),new Marketplace_Distribution()};
	
	public Marketplace_Main()
	{
		this.setLayout(new GridLayout(listOfPanels.length+1, 1));
		for(int i = 0; i < listOfPanels.length; i++)
			add(listOfPanels[i]);
		add(marketControls);
	}
}
