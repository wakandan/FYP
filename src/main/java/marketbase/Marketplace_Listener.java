package marketbase;

import interfaces.MarketEntityInterface;

import java.awt.event.*;
import javax.swing.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import simbase.Sim;
import configbase.DBConfig;
import configbase.SimConfig;
import core.MyDB;
import core.MyEventListener;

//Class to handle the button listener
public class Marketplace_Listener implements ActionListener {

	Marketplace_Controls marketControls;
	DBConfig dbConfig;
	public MyDB					mydb;
	public String				dbFile;
	public String				dbInitDir;
	
	public Marketplace_Listener(Marketplace_Controls marketControls)
	{
		this.marketControls = marketControls;
	}
	
	public void setUpRun() {
		dbFile = "C:/Users/Sky/FYP2/FYP/testMydb.sqlite";
		dbInitDir = "C:/Users/Sky/FYP2/FYP/src/main/resources/sql";
		mydb = new MyDB(dbFile, dbInitDir);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equalsIgnoreCase("Import Configuration"))
		{
			new Marketplace_Reader(marketControls.market,'R');
		}
		else if(e.getActionCommand().equalsIgnoreCase("Save Configuration"))
		{
			new Marketplace_Reader(marketControls.market,'W');
		}
		else if(e.getActionCommand().equalsIgnoreCase("Reset"))
		{
			for(int i = 0;i <marketControls.market.getComponentCount();i++)
				if(marketControls.market.getComponent(i) instanceof MarketEntityInterface)
					((MarketEntityInterface)marketControls.market.getComponent(i)).resetText();
		}
		else if(e.getActionCommand().equalsIgnoreCase("Run"))
		{
			//Change tab to simulation analyzer upon run button clicked
			Thread tabChange = new Thread()
			{
			  public void run()
			  {
				  marketControls.market.main.changeTab(2);
			  }
			};
			Thread simRunner = new Thread()
			{
				public void run()
				{
					SimConfig simConfig = new SimConfig();
					setUpRun();
					try {
						PropertyConfigurator.configure("src/main/resources/log4j.properties");
						simConfig.readConfig("src/test/resources/simbase/TestSimConfigBRS_TRAVOS.ini");
						Sim sim = new Sim();
						sim.setSimConfig(simConfig);
						sim.setDb(mydb.conn);
						sim.registerEventListeners((MyEventListener) marketControls.outputReader);
						sim.run();
						marketControls.outputReader.readLogFile();
						JOptionPane.showMessageDialog(marketControls.market,"Simulation Successfully Completed");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			};
			tabChange.start();
			simRunner.start();
		}
	}

}
