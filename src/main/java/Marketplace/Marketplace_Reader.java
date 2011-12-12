package Marketplace;
import java.io.*;
import javax.swing.*;

import Interfaces.MarketEntityInterface;

public class Marketplace_Reader 
{
	
	Marketplace_Main market;
	MarketEntityInterface tempPanel;
	
	public Marketplace_Reader(Marketplace_Main market,String fileName)
	{
		this.market = market;
		readFile(initFileChooser());
	}
	//To read in the file
    public void readFile(String fileName)
    {

        try {
            FileReader frStream = new FileReader(fileName);
            BufferedReader brStream = new BufferedReader(frStream);
            String inputLine;
            int i = 0;

            while ((inputLine=brStream.readLine()) != null)
            {
            	if(inputLine.equalsIgnoreCase("Buyer/Seller"))
            	{
            		tempPanel = market.buySell;
            		i = 0;
            		continue;
            	}
            	else if(inputLine.equalsIgnoreCase("Product/Price"))
            	{
            		tempPanel = market.prodPrice;
            		i = 0;
            		continue;
            	}            
            	
                String values[] = inputLine.split(" : ");
                if(values.length == 2)
                	tempPanel.setParameters(i++,values[1].trim());                
            }

            brStream.close();
        }
        catch (IOException ex) {
            System.out.println("Error in readFile method! " + ex);
        }
    }
    
    public String initFileChooser()
    {
    	String fileName = "";
	    JFileChooser fd = new JFileChooser(".");
	    int returnVal = fd.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    	fileName = fd.getSelectedFile().getPath();
	    
		return fileName;
    }
}
