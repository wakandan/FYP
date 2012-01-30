package interfaces;

import javax.swing.JPanel;

//GUI Interface Class
public abstract interface MarketEntityInterface {
	
	public void initLabelsandTextFields();
	public void resetText();
	public void setResetTextField(JPanel tempPanel);
	public void setParameters(int i,String text);
	public void setCompSize(int x,int y,int width,int height);
}
