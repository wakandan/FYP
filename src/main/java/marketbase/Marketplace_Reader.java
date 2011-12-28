package marketbase;
import interfaces.MarketEntityInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.io.*;
import javax.swing.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


//Class to read to import and export file
public class Marketplace_Reader 
{
	Marketplace_Main market;
	JPanel tempPanel;
	
	public Marketplace_Reader(Marketplace_Main market,char mode)
	{  
		this.market = market;
		market.main.setVisible(false);
		if(mode=='R')
			readFile(initFileChooser());
		else if(mode=='W')
			writeFile(initFileChooser());		
	}
	
	//To read in the ini file format
    public void readFile(String fileName)
    {

        try {
            FileReader frStream = new FileReader(fileName);
            BufferedReader brStream = new BufferedReader(frStream);
            String inputLine;
            int i = 0;

            while ((inputLine=brStream.readLine()) != null)
            {
            	
                String values[] = inputLine.split("=");
                if(values.length == 2)
                	setFieldForMain(values);             
            }

            brStream.close();
        }
        catch (IOException ex) {
            System.out.println("Error in readFile method! " + ex);
        }
    }
    
    //To write the ini file format
    public void writeFile(String fileName)
    {
    	if(fileName == null)
    		return;
    	else if(fileName.isEmpty())
    		return;
    	try
    	{
	    	PrintWriter outFile = new PrintWriter(fileName);
	    	outFile.write(getFieldPanel());
	    	outFile.flush();
	    	outFile.close();
        }
        catch (Exception ex) {
            System.out.println("Error in writeFile method! " + ex.getMessage());
        }
    	
    }
    
    //Open dialogue box
    public String initFileChooser() {
    	
    	String fileName = "";
    	try
    	{
        	Display display = new Display();
    	    final Shell shell = new Shell(display);
    	    shell.open();
        	FileDialog dlg = new FileDialog(shell, SWT.OPEN);
        	fileName = dlg.open();
    	    display.dispose();
    	    market.main.setVisible(true);
    	    
    	}
    	catch (Exception e)
    	{
    		
    	}
    	
    	return fileName;
    }
    
    public void setFieldForMain(String[] values)
    {
    	for(int i = 0;i < market.getComponentCount(); i++)
    		if(market.getComponent(i) instanceof JPanel)
    			traversePanels((JPanel) market.getComponent(i),values);
    }
    
    public void traversePanels(JPanel panel,String[] values)
    {
    	for(int i = 0;i < panel.getComponentCount(); i++)
    		if(panel.getComponent(i) instanceof JPanel)
    		{	
    			setTextFieldForPanel((JPanel) panel.getComponent(i),values);
    			traversePanels((JPanel) panel.getComponent(i),values);
    		}
    }
    
    public void setTextFieldForPanel(JPanel temp, String[] values)
    {
    	for(int i = 0;i < temp.getComponentCount(); i++)
    		if(temp.getComponent(i) instanceof JTextField)
    			if(((JTextField)temp.getComponent(i)).getName().equalsIgnoreCase(values[0]))
    				((JTextField)temp.getComponent(i)).setText(values[1]);
    }
    
    public String getFieldPanel()
    {
    	String output = "";
    	String newL = System.getProperty("line.separator");
    	
    	for(int i = 0;i < market.getComponentCount(); i++)
    		if(market.getComponent(i) instanceof JPanel)
    			output += traverseFieldPanels((JPanel) market.getComponent(i),"") + newL;
    	
    	return output;
    }
    
    public String traverseFieldPanels(JPanel panel,String output)
    {
    	String newL = System.getProperty("line.separator");
    	for(int i = 0;i < panel.getComponentCount(); i++)
    		if(panel.getComponent(i) instanceof JPanel)
    		{	
    			output += getTextForEachField((JPanel) panel.getComponent(i)) + newL;
    			traverseFieldPanels((JPanel) panel.getComponent(i),output);
    		}
    	
    	return output;
    }
    
    public String getTextForEachField(JPanel temp)
    {
    	String output = "";
    	String newL = System.getProperty("line.separator");
    	for(int i = 0;i < temp.getComponentCount(); i++)
    		if(temp.getComponent(i) instanceof JTextField)
    			if(!((JTextField)temp.getComponent(i)).getText().isEmpty())
    				output += ((JTextField)temp.getComponent(i)).getName()+"="+
    				((JTextField)temp.getComponent(i)).getText() + newL;
    	
    	return output;
    }
       
}
