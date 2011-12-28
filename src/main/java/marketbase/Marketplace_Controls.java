package marketbase;

import javax.swing.*;
import java.awt.*;

//Class containing all the buttons for GUI
public class Marketplace_Controls extends JPanel 
{
	Marketplace_Main market;
	SimulationAnalyzer_Main outputReader;
	//Button configurations
	JButton marketBtns[] = new JButton[4];
	String title[] = {"Import Configuration","Save Configuration","Reset","Run"};
	
	//For marketplace congfiguration
	public Marketplace_Controls(Marketplace_Main market)
	{
		this.market = market;
		initButtons();
	}
	
	//For simulation analyzer
	public void setSimAnalyzer(SimulationAnalyzer_Main outputReader)
	{
		this.outputReader = outputReader;
	}
	
	public void setPanelSize(int x,int y,int width,int height)
	{
		this.setSize(width,height);
		width = (width/marketBtns.length) - 20;
		height = 50;
		x = 10;
	    y = 10;
		for(int i = 0;i < marketBtns.length;i++)
		{
			marketBtns[i].setBounds(x,y,width,height);
			x += width + 10;
		}
	}
	
	//Initalise buttons
	public void initButtons()
	{
		for(int i = 0;i < marketBtns.length;i++)
		{
			marketBtns[i] = new JButton(title[i]);
			marketBtns[i].addActionListener(new Marketplace_Listener(this));
			this.add(marketBtns[i]);
		}
	}
	//Method to return the number of buttons
	public int getButtonLength()
	{
		return this.marketBtns.length;
	}
}
