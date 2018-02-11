import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Page implements Serializable {

    //should implement serializable ///
    String No;
    String[][] tuples;
    File file;
     public static int currentLine = 0;//the line to add data too

    //constructor 
    public Page(String pageNo, int columnNumber,File file) {
        this.No = pageNo;
        this.tuples = new String[columnNumber][200];// 200 max no of tuples
        this.file = file;
}
    //check for page if full or not
    public boolean check(){
        if(this.currentLine==200){
        	this.WritePage();
        	return true;
        }
        return false;
    }
    // insert method for pages 
    public void insert(Hashtable<String,String> input){
        	int column = 0;
        	String key="";
        	Enumeration<String> names = input.keys();
        	   while(names.hasMoreElements()) {
        	       key = (String) names.nextElement();
        	       
            	this.tuples[column][currentLine] = key;
            	column++;
            	this.tuples[column][currentLine]= input.get(key);
            	column++;
            }   
        currentLine++;
        }
    
    	public void WritePage(){
    		ObjectOutputStream ObjectOutputStream;
    		try {
    			ObjectOutputStream = new ObjectOutputStream(
    					new FileOutputStream(
    							this.file)); //intially writing the page as a big fat serializable thing .. make this our page XD 
    			ObjectOutputStream.writeObject(this);   // page already made
    			ObjectOutputStream.close();
    			
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		
    	
    		}
    		
    	}
    


