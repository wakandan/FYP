package simbase;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Marketplace.Marketplace_Main;

public class Main_GUI extends JFrame{
	
	JPanel mainPanel = new JPanel(new GridLayout(1,1));
	JPanel panels[] = {new Marketplace_Main(),new JPanel(),new JPanel()};
	String title[] = {"Marketplace Setup","Model Selection","Simulation Analyzer"};
	
	public Main_GUI(String title)
	{
		super(title);
		createTabs();
		this.add(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		this.pack();
	}
	public void createTabs()
	{
        JTabbedPane tabbedPane = new JTabbedPane();    
        
        for(int i = 0;i < panels.length;i++)
        	tabbedPane.addTab(title[i],panels[i]);
        
        mainPanel.add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	public static void main(String[] args)
	{
		Main_GUI gui = new Main_GUI("Robustness System");
	}

}
