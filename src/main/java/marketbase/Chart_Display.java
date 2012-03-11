package marketbase;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Chart_Display extends JPanel implements ActionListener{
	
	private CategoryDataset dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	//Label and dropbox
	private String title;
	private JLabel chartLabel;
	private Chart_Table chartTable;
	private Container contentChartPane;
	private JComboBox chartComboBox;
	private Container contentlabelDropBoxPane;
	private ArrayList<String> chartTitles;
	private ChartAnalyzer_Main chart_Main;
	
    public Chart_Display(Chart_Table chartTable,ArrayList<String> chartTitles,int index,ChartAnalyzer_Main chart_Main) {
    	this.title = chartTitles.get(index);
    	this.chartTitles = chartTitles;
    	this.chart_Main = chart_Main;
    	this.setLayout(new BorderLayout(10, 10));
    	this.chartTable = chartTable;
    	initLabelAndDropBox();
    	initChartData();
    }
    
    public void setSelectedIndex(int index)
    {
    	this.chartComboBox.setSelectedIndex(index);
    }
    
    //To create label and drop box for the chart
    public void initLabelAndDropBox()
    {
    	//Container to store label and dropbox
    	contentlabelDropBoxPane = new Container();
    	contentlabelDropBoxPane.setLayout(new FlowLayout());
    	chartLabel = new JLabel("Chart Type: ");
    	chartComboBox = new JComboBox(chartTitles.toArray());
    	chartComboBox.setSelectedIndex(0); //Default selected drop box value
    	chartComboBox.addActionListener(this);
    	//Adding container to the panel
    	contentlabelDropBoxPane.add(chartLabel);
    	contentlabelDropBoxPane.add(chartComboBox);
    	contentlabelDropBoxPane.setSize(this.getWidth(),20);
    	this.add(contentlabelDropBoxPane,BorderLayout.NORTH);
    }
    
    
    //To create chart diagram
    public void initChartData()
    {
    	//Container to store the chart diagram
    	contentChartPane = new Container();
    	contentChartPane.setLayout(new FlowLayout());
        //Creating of chart
    	dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        //this.add(chartPanel, BorderLayout.CENTER);
        //Adding container to the panel
        contentChartPane.add(chartPanel);
        this.add(chartPanel, BorderLayout.CENTER);
    }

    //To create a method to read from a file and display the chart content
    private CategoryDataset createDataset() {
        
    	Vector<String> series = new Vector<String>();
        Vector<String> xAxis = new Vector<String>();
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
    	char x = 65;
    	for(int i = 0;i < chartTable.getRow().size();i++)
    	{
    		x += i;
    		series.addElement(""+x);
    	}
         
    	
         for(Object col : chartTable.getCol())
         {
        	 xAxis.addElement((String)col);
         }
         
         int col = 0;
         int rowIndex = 0;
         for(Vector<Object> row : chartTable.getRow())
         {
        	for(Object value: row)
        		dataset.addValue(Math.abs(Double.parseDouble((String)value)),series.get(rowIndex),xAxis.get(col++));
            rowIndex++;col = 0;
         }
        			 
        return dataset;       
    }
    
    //To create the chart
    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
            this.title,       	   	   // chart title
            "Trust Models",            // domain axis label
            "Mean & Std Dev",          // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

        /* StandardLegend legend = (StandardLegend) chart.getLegend();
        legend.setDisplaySeriesShapes(true);
        legend.setShapeScaleX(1.5);
        legend.setShapeScaleY(1.5);
        legend.setDisplaySeriesLines(true);*/

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        //The range axis
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        //The renderer
/*         LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        
        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );*/
        
        return chart;
    }

    //For action listener when clicking the drop box
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.chartComboBox))
			chart_Main.initPanel(this.chartComboBox.getSelectedIndex());
			
	}
}
