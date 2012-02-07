package marketbase;

import java.awt.GridLayout;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;

import org.apache.log4j.Logger;

import core.MyEvent;
import core.MyEventListener;

public class SimulationAnalyzer_Main extends JPanel implements MyEventListener {
	
	private JTextArea simLog = new JTextArea();
	private Marketplace_Table transTable;
	private String columnData[] = {"Buyer","Seller","Product Name","Quantity"};
	private Marketplace_Table transBalTable;
	private String columnBalData[] = {"Buyer", "Balance"};
	
	public SimulationAnalyzer_Main()
	{
		initTable();
		initPanel();
	}
	
	public void initTable()
	{
		transTable = new Marketplace_Table();
		transTable.setInitTable(columnData.length, columnData);
		transBalTable = new Marketplace_Table();
		transBalTable.setInitTable(this.columnBalData.length, this.columnBalData);
	}
	
	public void initPanel()
	{
		this.setLayout(new GridLayout(3,1,10,10));
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
		
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.setBorder(BorderFactory.createTitledBorder("Balance Details"));
		textPanel.add(transBalTable);
		add(textPanel);
	}
	
//	public void setText(String text)
//	{
//		String newL = System.getProperty("line.separator");
//		this.simLog.setText(simLog.getText()+newL+text);
//	}
	
	public void readLogFile()
	{
		try
		{
			Scanner readLog = new Scanner(simLog.getText());
			String nextLine = "",transaction = "";
			Vector<String> transData = new Vector<String>(),balData = new Vector<String>();
	        int i = 0;
			while(readLog.hasNextLine())
			{
				nextLine = readLog.nextLine();
				if(nextLine.startsWith("Balance"))
				{
					balData = new Vector<String>();
					balData = splitLine(nextLine);
					balData.removeElementAt(0);
					setRowData(balData,'B');
				}
				else
				{
					transaction = nextLine.split(" - ")[nextLine.split(" - ").length-1];
					//setText(transaction);
					if(transaction.contains("(OK)"))
					{
						transData = new Vector<String>();
						transData.addElement(transaction.split(" <-")[0]);
						transData.addElement(transaction.split("-> ")[1].substring(0,transaction.split("-> ")[1].indexOf("(OK")));
						transData.addElement(transaction.split(" <-")[1].split("-> ")[0].substring(0,transaction.split(" <-")[1].split("-> ")[0].indexOf("(x")-1));
						transData.addElement(transaction.split(" <-")[1].split("-> ")[0].substring(transaction.split(" <-")[1].split("-> ")[0].indexOf("(x")+2,
						transaction.split(" <-")[1].split("-> ")[0].length()-1));
						setRowData(transData,'A');
					}
				}
				
			}
			System.out.println("Reading Finished!");
			readLog.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Vector<String> splitLine(String line)
	{
		Vector<String> listofWords = new Vector<String>();
		String word = "";
		
		for(char curChar : line.toCharArray())
		{
			
			if(curChar!=' ' && curChar!=':')
				word += curChar;
			else if(curChar == ' ')
			{
				if(!word.isEmpty())
					listofWords.addElement(word);
				word = "";
			}
		}
		if(!word.isEmpty())
			listofWords.addElement(word);
		return listofWords;
	}
	
	public void setRowData(Vector<String> transData,char type)
	{
		if(type=='A')
			transTable.addRowData(transData);
		else
			transBalTable.addRowData(transData);
	}

	public void onRecvMyEvent(MyEvent event) {
		simLog.append(event.text + "\n");
		simLog.setCaretPosition(simLog.getDocument().getLength());
	}

}
