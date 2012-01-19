package marketbase;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Chart_Table extends JPanel{
	
	private boolean DEBUG = false;
	//Need to file the column name and data from file and display dynamic
    //private String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};
	//private Object[][] data = {
	//		{"Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false)} };
	private Chart_TableReader tableReader;
	
	//Table attribute
	private JTable table;
	private JScrollPane scrollPane;
	private Container tablePane;

    public Chart_Table() {

    	this.setLayout(new GridLayout(1, 0));
    	createTable();
    }
    
    public void createTable()
    {
    	tableReader = new Chart_TableReader();
    	tableReader.readFile("testingReadFile.txt");
    	//System.out.println(tableReader.getColName());
    	//System.out.println(tableReader.getRow());
    	DefaultTableModel model = new DefaultTableModel(tableReader.getRow(), tableReader.getColName());
    	//table = new JTable(tableReader.getRow(), tableReader.getColName());
    	table = new JTable(model);
    	table.setPreferredScrollableViewportSize(getPreferredSize());
        table.setFillsViewportHeight(true);
        
        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);
        
        //Adding container to panel
        //tablePane.add(table);
        //tablePane.add(scrollPane);
        //this.add(tablePane);
        //this.add(table);
        this.add(scrollPane);
        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }
    }
    
    public Chart_TableReader getTable()
    {
    	return tableReader;
    }

    public void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
