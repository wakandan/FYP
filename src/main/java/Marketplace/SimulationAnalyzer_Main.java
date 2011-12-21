package Marketplace;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import simbase.Transaction;


public class SimulationAnalyzer_Main extends JPanel {
	
	private JTextArea simLog = new JTextArea();
	private Marketplace_Table transTable;
	private String columnData[] = {"Buyer","Seller","Product Name","Quantity","Price"};
	
	public SimulationAnalyzer_Main()
	{
		initTable();
		initPanel();
	}
	
	public void initTable()
	{
		transTable = new Marketplace_Table();
		transTable.setInitTable(5, columnData);
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
	
	public void setRowData(Transaction transData)
	{
		transTable.addRowData(transData);
	}

}
