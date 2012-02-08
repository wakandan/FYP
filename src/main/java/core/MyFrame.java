package core;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import simbase.Sim;

/**
 *
 */

/**
 * @author akai
 * 
 */
class MyFrame extends JFrame implements ActionListener {
	public MyPanel				statusPanel;
	public JButton				configImpBtn;
	public JButton				runBtn;
	public JButton				configBtn;
	public JButton				dbChooseBtn;
	public Sim					sim;
	public String				configFileName;

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
		contentPane.add(runBtn);
		initVars();
	}

	public void initVars() {
		configImpBtn.addActionListener(this);
		dbChooseBtn.addActionListener(this);
		sim = new Sim();
	}

	public void configImpBtnClicked() {
		importConfigFile();
	}

	public void dbChooseBtnClicked() {}

	/* (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println(actionCommand);
		if (actionCommand.equalsIgnoreCase(BTN_STR_CFG_IMP)) {
			configImpBtnClicked();
		} else if (actionCommand.equalsIgnoreCase(BTN_STR_CFG)) {

		} else if (actionCommand.equalsIgnoreCase(BTN_STR_RUN)) {

		} else if (actionCommand.equalsIgnoreCase(BTN_STR_DB_CHOOSE)) {
			dbChooseBtnClicked();
		}
	}

	public void importConfigFile() {
		try {
			JFileChooser fc = new JFileChooser();
			int value = fc.showOpenDialog(this);
			if (value == JFileChooser.APPROVE_OPTION)
				configFileName = fc.getSelectedFile().getAbsolutePath();
		} catch (Exception e) {}
		System.out.println("Imported config file: " + configFileName);
	}
}
