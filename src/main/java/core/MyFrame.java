package core;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import configbase.SimConfig;

import simbase.Sim;

/**
 *
 */

/**
 * @author akai
 * 
 */
class MyFrame extends JFrame implements ActionListener {
	public JPanel				statusPanel;
	public JButton				configImpBtn;
	public JButton				runBtn;
	public JButton				configBtn;
	public JButton				dbChooseBtn;
	public Sim					sim;
	public SimConfig			simConfig;
	public String				configFileName;
	public MyDB					mydb;
	public String				dbFile;
	public String				dbInitDir;

	public static final String	BTN_STR_CFG_IMP		= "Import Config";
	public static final String	BTN_STR_CFG			= "Config";
	public static final String	BTN_STR_RUN			= "Run";
	public static final String	BTN_STR_DB_CHOOSE	= "Select DB";

	public MyFrame() {
		super();
		statusPanel = new MyPanel();
		this.setBounds(100, 100, 700, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(statusPanel);
		configImpBtn = new JButton(BTN_STR_CFG_IMP);
		runBtn = new JButton(BTN_STR_RUN);
		configBtn = new JButton(BTN_STR_CFG);
		dbChooseBtn = new JButton(BTN_STR_DB_CHOOSE);
		contentPane.add(configImpBtn);
		contentPane.add(configBtn);
		contentPane.add(dbChooseBtn);
		contentPane.add(runBtn);		
		initVars();
	}

	public void initVars() {
		configImpBtn.addActionListener(this);
		dbChooseBtn.addActionListener(this);
		runBtn.addActionListener(this);
	}

	public void configImpBtnClicked() {
		importConfigFile();
	}

	public void dbChooseBtnClicked() {
		dbFile = selectFile();
		dbInitDir = "D:/doc/workspace/sim/src/main/resources/sql";
		mydb = new MyDB(dbFile, dbInitDir);
	}

	public void simRunBtnClicked() throws Exception {
		Thread simRunThread = new Thread(){
			public void run() {
				try {
					mydb = new MyDB(dbFile, dbInitDir);
					sim = new Sim();
					sim.registerEventListeners((MyEventListener) statusPanel);
					simConfig = new SimConfig();
					simConfig.readConfig(configFileName);
					sim.setSimConfig(simConfig);
					sim.setDb(mydb.conn);
					sim.run();
				} catch (Exception e) {
					System.out.println("Simulation run failed");
				}
			};
		};
		simRunThread.start();
	}

	/* (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equalsIgnoreCase(BTN_STR_CFG_IMP)) {
			configImpBtnClicked();
		} else if (actionCommand.equalsIgnoreCase(BTN_STR_CFG)) {

		} else if (actionCommand.equalsIgnoreCase(BTN_STR_RUN)) {
			try {
				simRunBtnClicked();
			} catch (Exception exception) {
				System.out.println("Sim run failed");
			}
		} else if (actionCommand.equalsIgnoreCase(BTN_STR_DB_CHOOSE)) {
			dbChooseBtnClicked();
		}
	}

	public void importConfigFile() {
		configFileName = selectFile();
		System.out.println("Imported config file: " + configFileName);
	}

	/* Open file chooser dialog and return the filename chosen */
	public String selectFile() {
		String filename = "";
		JFileChooser fc = new JFileChooser();
		int value = fc.showOpenDialog(this);
		if (value == JFileChooser.APPROVE_OPTION)
			filename = fc.getSelectedFile().getAbsolutePath();
		return filename;

	}

}
