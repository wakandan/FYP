package Marketplace;

import javax.swing.*;
import java.awt.*;

public class Marketplace_Controls extends JPanel 
{
	JButton marketBtns[] = new JButton[3];
	Marketplace_Main market;
	String title[] = {"Import Configuration","Save Configuration","Reset","Next Tab"};
	
	public Marketplace_Controls(Marketplace_Main market)
	{
		this.market = market;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		initButtons();
	}
	
	public void initButtons()
	{
		for(int i = 0;i < marketBtns.length;i++)
		{
			marketBtns[i] = new JButton(title[i]);
			marketBtns[i].addActionListener(new Marketplace_Listener(this));
			this.add(marketBtns[i]);
		}
	}
	
	public int getButtonLength()
	{
		return this.marketBtns.length;
	}
}
