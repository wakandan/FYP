package interfaces;

//Interface class for File Reader
public interface File_Reader {
	
	public void readFile(String fileName); //Method to read file
	public void writeFile(String fileName); //Method to write file
	public String initFileOpenChooser(); //Method to open dialogue box
	public String initFileSaveChooser(); //Method to save dialogue box

}
