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
	//Need to think of dynamic reading of chart and title
	private String [] chartType = {"Reputation Metric", "Honest-Dishonest Metric"};
	//Label and dropbox
	private JLabel chartLabel;
	private Chart_Table chartTable;
	private JComboBox chartComboBox;
	private Container contentlabelDropBoxPane, contentChartPane;
	
	
    public Chart_Display(Chart_Table chartTable) {
    	this.setLayout(new BorderLayout(10, 10));
    	this.chartTable = chartTable;
    	initLabelAndDropBox();
    	initChartData();
    }
    
    //To create label and drop box for the chart
    public void initLabelAndDropBox()
    {
    	//Container to store label and dropbox
    	contentlabelDropBoxPane = new Container();
    	contentlabelDropBoxPane.setLayout(new FlowLayout());
    	chartLabel = new JLabel("Chart Type: ");
    	chartComboBox = new JComboBox(chartType);
    	chartComboBox.setSelectedIndex(0); //Default selected drop box value
    	chartComboBox.addActionListener(this);
    	//Adding container to the panel
    	contentlabelDropBoxPane.add(chartLabel);
    	contentlabelDropBoxPane.add(chartComboBox);
    	this.add(contentlabelDropBoxPane, BorderLayout.NORTH);
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
    	
    	for(Vector<Object> row : chartTable.getTable().getRow())
    	{
    		for(Object value : row)
    		{
    		    series.addElement((String) value);
    		    break;
    		}
        }
         
         for(int i = 1;i < chartTable.getTable().getColName().size();i++)
        	 xAxis.addElement(chartTable.getTable().getColName().get(i));
         
         int rowIndex = 0,col = 0;
         for(Vector<Object> row : chartTable.getTable().getRow())
         {
        	 col = 0;
        	 for(int i = 0;i < row.size();i++)
        	 {
        		 if(i==0)
        			 continue;
        		 dataset.addValue(Double.parseDouble((String)row.get(i)),series.get(rowIndex),xAxis.get(col++));
        	 } 
        	rowIndex++;
         }
         

        //Create the dataset
        

//        dataset.addValue(1.0, series1, type1);
//        dataset.addValue(4.0, series1, type2);
//        dataset.addValue(3.0, series1, type3);
//        dataset.addValue(5.0, series1, type4);
//        dataset.addValue(5.0, series1, type5);
//        dataset.addValue(7.0, series1, type6);
//        dataset.addValue(7.0, series1, type7);
//        dataset.addValue(8.0, series1, type8);
//
//        dataset.addValue(5.0, series2, type1);
//        dataset.addValue(7.0, series2, type2);
//        dataset.addValue(6.0, series2, type3);
//        dataset.addValue(8.0, series2, type4);
//        dataset.addValue(4.0, series2, type5);
//        dataset.addValue(4.0, series2, type6);
//        dataset.addValue(2.0, series2, type7);
//        dataset.addValue(1.0, series2, type8);
//
//        dataset.addValue(4.0, series3, type1);
//        dataset.addValue(3.0, series3, type2);
//        dataset.addValue(2.0, series3, type3);
//        dataset.addValue(3.0, series3, type4);
//        dataset.addValue(6.0, series3, type5);
//        dataset.addValue(3.0, series3, type6);
//        dataset.addValue(4.0, series3, type7);
//        dataset.addValue(3.0, series3, type8);

        /*
         * for(Vector row : rowOfVectors )
         *    for(int i = 0;i < row.size(); i++)
         *      dataset.addValue(row.getElement(i),i,type+"i");
         */
        return dataset;       
    }
    
    //To create the chart
    private JFreeChart createChart(CategoryDataset dataset) {
        
        JFreeChart chart = ChartFactory.createLineChart(
            "Title",       // chart title
            "Type",                    // domain axis label
            "Value",                   // range axis label
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
         LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

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
        );
        
        return chart;
    }

    //For action listener when clicking the drop box
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
