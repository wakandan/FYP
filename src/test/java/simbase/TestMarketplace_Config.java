package simbase;

import marketplace.Marketplace_Config;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TestMarketplace_Config
{
    
    @Test
    public void test()
    {
	    Marketplace_Config config = new Marketplace_Config();
	    config.validate();
	    JFrame frame = new JFrame("Testing");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(config);
	    //frame.setSize(1000, 1000);
	    frame.pack();
	    frame.setVisible(true);
	    frame.setResizable(false);
	    while (true)
	    {
	    }
    }
}
