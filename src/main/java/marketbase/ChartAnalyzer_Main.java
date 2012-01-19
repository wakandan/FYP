package marketbase;

import java.awt.GridLayout;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;

import org.jfree.ui.RefineryUtilities;

//For Chart Analyzer GUI
public class ChartAnalyzer_Main extends JPanel{

	private Chart_Display chart; //For chart display 
	private Chart_Table table; //For chart table display
	
	public ChartAnalyzer_Main()
	{
		initTable();
		initChart();
		initPanel();
	}
	
	//To display the chart
	public void initChart()
	{
		chart = new Chart_Display(table);
	}
	
	public void initTable()
	{
		table = new Chart_Table();
		//table.setOpaque(true);
	}
	
	//To segment the panel into 2 sections
	public void initPanel()
	{ 
		//Overview of the main layout
		this.setLayout(new GridLayout(2,1,10,10));
		
		//Segment the panel for chart
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.setBorder(BorderFactory.createTitledBorder("Chart"));
		textPanel.add(chart);
		add(textPanel);
		
		//Segment the panel for table
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1,0));
		textPanel.setBorder(BorderFactory.createTitledBorder("Table"));
		textPanel.add(table);
		add(textPanel);
	}
}
