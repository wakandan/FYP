package marketbase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;

import org.jfree.ui.RefineryUtilities;

//For Chart Analyzer GUI
public class ChartAnalyzer_Main extends JPanel{

	private ArrayList<Chart_Display> charts = new ArrayList<Chart_Display>(); //For chart display 
	private ArrayList<Chart_Table> tables = new ArrayList<Chart_Table>(); //For chart table display
	private ArrayList<Vector<Vector<Object>>> chartData;
	private ArrayList<Vector<Object>> chartCols;
	private ArrayList<String> chartTitles;
	
	public void setChartData(ArrayList <Vector<Vector<Object>>> chartData,ArrayList<Vector<Object>> chartCols,ArrayList<String> chartTitles)
	{
		this.chartTitles = chartTitles;
		this.chartData = chartData;
		this.chartCols = chartCols;
		initChartAndTable();
		initPanel(0);
	}
	
	//To display the chart
	public void initChartAndTable()
	{
		int index = 0;
		for(Vector<Vector<Object>> dataSet : chartData)
		{
			Chart_Table table = new Chart_Table(dataSet,chartCols.get(index));
			Chart_Display chart = new Chart_Display(table,chartTitles,index++,this);
			tables.add(table);
			charts.add(chart);
		}
	}
	
	//To segment the panel into 2 sections
	public void initPanel(int index)
	{ 
		this.removeAll();
		//Overview of the main layout
		this.setLayout(new GridLayout(2,1,10,10));
		
		charts.get(index).setSelectedIndex(index);
		//Segment the panel for chart
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.setBorder(BorderFactory.createTitledBorder("Chart"));
		textPanel.add(charts.get(index));
		this.add(textPanel);
		
		//Segment the panel for table
		JPanel textPanel1 = new JPanel();
		textPanel1.setLayout(new GridLayout(1,0));
		textPanel1.setBorder(BorderFactory.createTitledBorder("Table"));
		textPanel1.add(tables.get(index));
		this.add(textPanel1);
		
		if(this.getComponentCount()==4)
		{
			this.remove(0);
			this.remove(0);
		}
		
	}

}
