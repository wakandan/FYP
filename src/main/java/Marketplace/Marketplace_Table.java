package Marketplace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import simbase.Transaction;


public class Marketplace_Table extends JPanel {
	private int cols = 0;
	private JTable table;
	private Vector<Vector> rowData = new Vector<Vector>();
	private Vector<String> columnNames = new Vector<String>();
	
	public void setInitTable(int columns,String columnData[])
	{
		this.cols = columns;
		initTable(columnData);
	}
	
	private void initTable(String columnData[])
	{
		for(int i = 0;i < cols;i++)
			columnNames.addElement(columnData[i]);
		DefaultTableModel model = new DefaultTableModel(rowData,columnNames);
		table = new JTable(model);
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.yellow);
		table.setEnabled(false);
		add(new JScrollPane(table));
	}
	
	public void addRowData(Transaction transData)
	{
	   Vector<String> data = new Vector<String>();
	   removeAll();
	   data.addElement(transData.getBuyer().getName());
	   data.addElement(transData.getSeller().getName());
	   data.addElement(transData.getProd().getName());
	   data.addElement(Integer.toString(transData.getQuantity()));
	   data.addElement(Double.toString(transData.getPrice()));
	   
	   rowData.addElement(data);
	   DefaultTableModel model = new DefaultTableModel(rowData,columnNames);
	   table = new JTable(model);
	   table.setEnabled(false);
	   JTableHeader header = table.getTableHeader();
	   header.setBackground(Color.yellow);;
	   add(new JScrollPane(table));
	}
}
