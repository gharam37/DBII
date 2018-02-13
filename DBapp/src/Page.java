import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class Page implements Serializable {

    //should implement serializable ///
    int No;
    LinkedList<Hashtable<String,String>> tuples;
    File file;
     public static int currentLine = 0;//the line to add data too

    //constructor 
    public Page(int pageNo, int columnNumber,File file) {
        this.No = pageNo;
        this.tuples = new LinkedList();// 200 max no of tuples
        this.file = file;
}
    //check for page if full or not 
    //if full it calls WritePage 
    @SuppressWarnings("static-access")
	public boolean check(){
       return this.currentLine==200;
    }
    // insert method for pages 
    public void insertIntoPage(Hashtable<String, Object> htblColNameVale){
        	int column = 0;
        	String key="";
        	Enumeration<String> names = htblColNameVale.keys();
        	   while(names.hasMoreElements()) {
        	       key = (String) names.nextElement();
        	       
            	this.tuples[column][currentLine] = key;
            	column++;
            	this.tuples[column][currentLine]= (String) htblColNameVale.get(key).toString();
            	column++;
            	
            }   
        	   ObjectOutputStream ObjectOutputStream;
       		try {
       			ObjectOutputStream = new ObjectOutputStream(
       					new FileOutputStream(
       							this.file));
       			//intially writing the page as a big fat serializable thing .. make this our page XD 
       			ObjectOutputStream.writeObject(this); // page already made
       			ObjectOutputStream.close();
       			
       		} catch (FileNotFoundException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		} catch (IOException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
       		
        currentLine++;
        }
    
    	
   }
    



