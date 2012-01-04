package marketbase;

import interfaces.MarketEntityInterface;

import java.awt.event.*;
import javax.swing.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import simbase.Sim;
import configbase.DBConfig;
import configbase.SimConfig;



//Class to handle the button listener
public class Marketplace_Listener implements ActionListener {

	Marketplace_Controls marketControls;
	DBConfig dbConfig;
	
	public Marketplace_Listener(Marketplace_Controls marketControls)
	{
		this.marketControls = marketControls;
	}
	
	public void setUpRun() {
		dbConfig = new DBConfig(null);
		dbConfig.addDdlFile("src/main/resources/sql/Products.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Agents.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Inventories.ddl");		
		dbConfig.addDdlFile("src/main/resources/sql/Executions.ddl");	
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
			SimConfig simConfig = new SimConfig();
			setUpRun();
			try {
				PropertyConfigurator.configure("src/main/resources/log4j.properties");
				simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
				Sim sim = new Sim();
				sim.setSimConfig(simConfig);
				sim.setDb(dbConfig.setUpDb());
				sim.run();
				Logger.shutdown();
				marketControls.outputReader.readLogFile();
				JOptionPane.showMessageDialog(marketControls.market,"Simulation Successfully Completed. Check Simulation Analyzer page to see data");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
