package Marketplace;
import javax.swing.*;

import java.awt.*;

public class Marketplace_Main extends JPanel {
	
	Marketplace_Personnel buySell = new Marketplace_Personnel();
	Marketplace_Goods prodPrice = new Marketplace_Goods();
	Marketplace_Controls marketControls = new Marketplace_Controls(this);
	
	public Marketplace_Main()
	{
		this.setLayout(new GridLayout(3,1));
		add(buySell);
		add(prodPrice);
		add(marketControls);
	}
	
}
