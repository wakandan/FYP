package marketbase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import interfaces.File_Reader;

//Class to retrieve the table information
public class Chart_TableReader extends AbstractTableModel implements File_Reader {

	private Vector <Vector <Object>> data; //Row of records
	private Vector <String> colName; //Column title
	
    /*
     * 1) Read row vector(2D) and column(columnNames) vector 
     * 2) Remember to create getter method(return methods) to return both vectors
     * 3) in the createTable method, call the return method of both vectors e.g. getColumnNames()
     *    from the file reader class. 
     * */
	
	Chart_TableReader()
	{
		data = new Vector <Vector <Object>> ();
		colName = new Vector <String> ();
	}

	//Get method for row
	public Vector <Vector<Object>> getRow()
	{
		return data;
	}
	
	//Get method for column
	public Vector <String> getColName()
	{
		return colName;
	}
	
	//Read from file
	public void readFile(String fileName) {
		// TODO Auto-generated method stub

		try {
            FileReader frStream = new FileReader(fileName);
            BufferedReader brStream = new BufferedReader(frStream);
            String inputLine;
            int i = 0;
            Vector<Object> currentRow = new Vector<Object>();
            while ((inputLine=brStream.readLine()) != null)
            {   
                //Ignore the file comment
            	if(inputLine.startsWith("#"))
                	continue;
            	
            	//Extract the column name
            	if(inputLine.startsWith("$"))
            	{
            		StringTokenizer st1 = new StringTokenizer(inputLine.substring(1), " ");
           
            		while(st1.hasMoreTokens())
            			colName.addElement(st1.nextToken());
            	}
            	else
                //Extract data and put into the row records
            	{
            		StringTokenizer st1 = new StringTokenizer(inputLine, " ");
                    currentRow = new Vector<Object>();
                    while(st1.hasMoreTokens())
                	    currentRow.addElement(st1.nextToken());
                    data.addElement(currentRow);
            	}

            }

            brStream.close();
        }
        catch (IOException ex) {
            System.out.println("Error in readFile method! " + ex);
        }
		
	}

	 //Get the number of row
	 public int getRowCount() 
	 {
		    return data.size();
	 }

	 //Get the number of column
	 public int getColumnCount()
	 {
	    return colName.size();
	 }
  
	//Not necessary
	public void writeFile(String fileName) {
		// TODO Auto-generated method stub
		
	}

	//Not necessary
	public String initFileOpenChooser() {
		// TODO Auto-generated method stub
		return null;
	}

	//Not necessary
	public String initFileSaveChooser() {
		// TODO Auto-generated method stub
		return null;
	}

	//Not necessary
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
