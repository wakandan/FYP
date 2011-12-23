package simbase;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Marketplace.Marketplace_Main;
import Marketplace.SimulationAnalyzer_Main;

public class Main_GUI extends JFrame{
	
	JPanel mainPanel = new JPanel(new GridLayout(1,1));
	JPanel panels[] = {new Marketplace_Main(),new JPanel(),new SimulationAnalyzer_Main()};
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
        {
        	tabbedPane.addTab(title[i],panels[i]);
        	if(panels[i] instanceof Marketplace_Main)
        		((Marketplace_Main)panels[i]).passSimAnalyzer(this);
        }
        
        mainPanel.add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	public JPanel getPanels(int index)
	{
		return panels[index];
	}
	
	public int getPanelCount()
	{
		return panels.length;
	}
	
	public static void main(String[] args)
	{
		Main_GUI gui = new Main_GUI("Robustness System");
	}

}
