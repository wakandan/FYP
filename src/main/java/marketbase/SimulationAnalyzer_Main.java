package marketbase;

import java.awt.GridLayout;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;

import org.apache.log4j.Logger;

public class SimulationAnalyzer_Main extends JPanel {
	
	private JTextArea simLog = new JTextArea();
	private Marketplace_Table transTable;
	private String columnData[] = {"Buyer","Seller","Product Name","Quantity"};
	
	public SimulationAnalyzer_Main()
	{
		initTable();
		initPanel();
	}
	
	public void initTable()
	{
		transTable = new Marketplace_Table();
		transTable.setInitTable(columnData.length, columnData);
	}
	public void initPanel()
	{
		this.setLayout(new GridLayout(2,1,10,10));
		simLog.setSize(10,10);
		simLog.setEditable(false);
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.add(new JScrollPane(simLog));
		textPanel.setBorder(BorderFactory.createTitledBorder("Log Details"));
		add(textPanel);
		
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.setBorder(BorderFactory.createTitledBorder("Transaction Details"));
		textPanel.add(transTable);
		add(textPanel);
	}
	
	public void setText(String text)
	{
		String newL = System.getProperty("line.separator");
		this.simLog.setText(simLog.getText()+newL+text);
	}
	
	public void readLogFile()
	{
		try
		{
			File debug = new File("debug");
			if(!debug.exists())
				debug = new File("debug.log");
			Scanner readLog = new Scanner(debug);
			String nextLine = "",transaction = "";
			Vector<String> transData = new Vector<String>();
	        int i = 0;
	        System.out.println("Reading Lines");
			while(readLog.hasNextLine())
			{
				nextLine = readLog.nextLine();
				transaction = nextLine.split(" - ")[nextLine.split(" - ").length-1];
				setText(transaction);
				System.out.println(i++);
				if(transaction.contains("(OK)"))
				{
					transData = new Vector<String>();
					transData.addElement(transaction.split(" <-")[0]);
					transData.addElement(transaction.split("-> ")[1].substring(0,transaction.split("-> ")[1].indexOf("(OK")));
					transData.addElement(transaction.split(" <-")[1].split("-> ")[0].substring(0,transaction.split(" <-")[1].split("-> ")[0].indexOf("(x")-1));
					transData.addElement(transaction.split(" <-")[1].split("-> ")[0].substring(transaction.split(" <-")[1].split("-> ")[0].indexOf("(x")+2,
					transaction.split(" <-")[1].split("-> ")[0].length()-1));
					setRowData(transData);
				}
				
			}
			System.out.println("Reading Finished!");
			readLog.close();
		    debug.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setRowData(Vector<String> transData)
	{
			transTable.addRowData(transData);
	}

}
