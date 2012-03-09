package marketbase;

import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;

import org.apache.log4j.Logger;

import core.MyEvent;
import core.MyEventListener;

public class SimulationAnalyzer_Main extends JPanel implements MyEventListener {
	
	private JTextArea simLog = new JTextArea();
	//For transaction details
	private Marketplace_Table transTable;
	private String columnData[] = {"Buyer","Seller","Product Name","Quantity"};
	//For balance details
	private Marketplace_Table transBalTable;
	private String columnBalData[] = {"Buyer", "Balance"};
	//For rating details
	private Marketplace_Table transRatingTable;
	private ChartAnalyzer_Main chartMain;
	private String columnRatingData[] = {"Seller", "Rating"};
	private ArrayList<Vector<Vector<Object>>> charts = new ArrayList<Vector<Vector<Object>>>(); 
	private ArrayList<Vector<Object>> chartCols = new ArrayList<Vector<Object>>();
	private ArrayList<String> chartTitles = new ArrayList<String>();
    private Vector<Object> col = new Vector<Object>();
    private Vector<Vector <Object>> data = new Vector<Vector <Object>>();
    private boolean isResult = false;
    
	public SimulationAnalyzer_Main()
	{
		initTable();
		initPanel();
	}
	
	public void initTable()
	{
		transTable = new Marketplace_Table();
		transTable.setInitTable(columnData.length, this.columnData);
		transBalTable = new Marketplace_Table();
		transBalTable.setInitTable(this.columnBalData.length, this.columnBalData);
		transRatingTable = new Marketplace_Table();
		transRatingTable.setInitTable(columnRatingData.length, this.columnRatingData);
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
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(1,2));
		
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.setBorder(BorderFactory.createTitledBorder("Balance Details"));
		textPanel.add(transBalTable);
		tablePanel.add(textPanel);
		
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.setBorder(BorderFactory.createTitledBorder("Rating Details"));
		textPanel.add(transRatingTable);
		tablePanel.add(textPanel);
		
		add(tablePanel);
	}
	
	public void setChartAnalyzer(ChartAnalyzer_Main chartMain)
	{
		this.chartMain = chartMain;
	}
	
	public void readLogFile()
	{
		try
		{
			Scanner readLog = new Scanner(simLog.getText());
			
			//Attributes to read
			String nextLine = "",transaction = "";
			Vector<String> transData = new Vector<String>(); //Use for storing transaction info
			Vector<String> balData = new Vector<String>(); //Use for storing of balance info
			Vector<String> ratingData = new Vector<String>(); //Use for storing of rating info
			int i = 0;
			while(readLog.hasNextLine())
			{
				nextLine = readLog.nextLine();
				
				if(isResult)
					processResults(nextLine);
				else
				{
					if(nextLine.startsWith("Rating"))
					{
						ratingData = new Vector<String>();
						ratingData = splitLine(nextLine);
						ratingData.removeElementAt(0);
						ratingData.removeElementAt(0);
						setRowData(ratingData,'C');
					}
					else if(nextLine.startsWith("Balance"))
					{
						balData = new Vector<String>();
						balData = splitLine(nextLine);
						balData.removeElementAt(0);
						setRowData(balData,'B');
					}
					else if(nextLine.contains("Simulation result"))
					{
						isResult = true;
					}
					else
					{
						transaction = nextLine.split(" - ")[nextLine.split(" - ").length-1];
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
				
			}
			System.out.println("Reading Finished!");
			readLog.close();
			if(!charts.isEmpty())
			   chartMain.setChartData(charts, chartCols,chartTitles);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void processResults(String nextLine)
	{
		if(nextLine.contains("*** #"))
		{
			charts.add(data);
			data = new Vector<Vector <Object>>();
			col = new Vector<Object>();
			isResult = false;
			return;
		}
		
		if(nextLine.startsWith("*** "))
		{
			chartTitles.add(nextLine.substring(nextLine.indexOf("***")+3,nextLine.lastIndexOf("***")).trim());
			if(!data.isEmpty())
			{
				charts.add(data);
				data = new Vector<Vector <Object>>();
				col = new Vector<Object>();
			}
		}
		else
		{
			if(col.isEmpty())
			{
				col.addAll(processChartColumns(nextLine));
				chartCols.add(processChartColumns(nextLine));
			}
			data.add(processChartData(nextLine));
		}
	}
	
	public Vector<Object> processChartData(String line)
	{
		if(line.split("[,]").length < 1)
			return null;
		
		String[] columns = line.split(",");
		Vector<Object> row = new Vector<Object>();
		
		for(String column : columns)
			row.add(column.split(":")[1].trim());
		
		return row;
	}
	
	public Vector<Object> processChartColumns(String line)
	{
		if(line.split("[,]").length < 1)
			return null;
		
		String[] columns = line.split(",");
		Vector<Object> col = new Vector<Object>();
		
		for(String column : columns)
			col.add(column.split(":")[0].trim());
		
		return col;
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
	
	public void setText(String text)
	{
		simLog.append(text + "\n");
		simLog.setCaretPosition(simLog.getDocument().getLength());
	}
	
	public ArrayList<Vector<Vector<Object>>> getCharts()
	{
		return charts;
	}
	
	public ArrayList<Vector<Object>> getChartColumns()
	{
		return chartCols;
	}
	
	public void setRowData(Vector<String> transData, char type)
	{
		if(type=='A')
			transTable.addRowData(transData);
		else if(type=='B')
			transBalTable.addRowData(transData);
		else
			transRatingTable.addRowData(transData);
	}

	public void onRecvMyEvent(MyEvent event) {
		simLog.append(event.text + "\n");
		simLog.setCaretPosition(simLog.getDocument().getLength());
	}

}
