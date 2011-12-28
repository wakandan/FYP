package marketbase;
import interfaces.MarketEntityInterface;
import interfaces.MarketTabPanels;

import javax.swing.*;

import simbase.Main_GUI;
import java.awt.*;

//Main interface for marketplace configuration
public class Marketplace_Main extends JPanel implements MarketTabPanels {
	
	Marketplace_Controls marketControls = new Marketplace_Controls(this);
	JPanel listOfPanels[] = {new Marketplace_Personnel(),new Marketplace_Goods(),new Marketplace_Distribution()};
	Main_GUI main;
	public Marketplace_Main(Main_GUI main)
	{
		this.setLayout(new GridLayout(listOfPanels.length+1, 1));
		for(int i = 0; i < listOfPanels.length; i++)
			add(listOfPanels[i]);
		add(marketControls);
		this.main = main;
	}
	
	public void setPanelSize(int width,int height)
	{
		try
		{
			this.setSize(width, height);
			height = height/(listOfPanels.length+1) - 20;
			width = width - 20;
			int x = 10,y = 10; 
			for(int i = 0;i < listOfPanels.length;i++)
			{
				if(listOfPanels[i] instanceof MarketEntityInterface)
					((MarketEntityInterface) listOfPanels[i]).setCompSize(x,y,width,height);
				else
					listOfPanels[i].setBounds(x,y,width,height);
				y += height + 10;
			}
			marketControls.setBounds(x, y, width,height);
			marketControls.setPanelSize(x, y, width,height);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void passSimAnalyzer(Main_GUI main)
	{
		for(int i = 0;i < main.getPanelCount(); i++)
			if(main.getPanels(i) instanceof SimulationAnalyzer_Main)
			{
				marketControls.setSimAnalyzer((SimulationAnalyzer_Main) main.getPanels(i));
				break;
			}
	}
}
