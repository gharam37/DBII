import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry; 
import java.util.Set;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry; 
import java.util.Set;



public class Table implements Serializable  {
	//should implement serializable 
	//should have an attribute arraylist of pages , a hashtable for type , a string for name 
    
	///should have an insert method called by DbApp .. it called an insert method in Page
	
	//should implement serializable 
	//should have an attribute arraylist of pages , a hashtable for type , a string for name 
    ///should have an insert method called by DbApp .. it called an insert method in Page
	
	String strClusteringKeyColumn; //table primary key
	String strTableName; //table name
	private static final long serialVersionUID = 1L;
	Hashtable<String,Object> htblColNameValue;	
	ArrayList<Page> Pages;
	Hashtable<String,String> htblColNameType; // hashtable of the attributes and their types.. to put inserted in a metadata file
	
	public Table(String strTableName,
			String strClusteringKeyColumn, 
			Hashtable<String,String> htblColNameType){ //to do the exception 
		this.strTableName=strTableName;
		this.strClusteringKeyColumn=strClusteringKeyColumn;
		this.htblColNameType=htblColNameType;
		this.Pages=new ArrayList<Page>();
		
		try {
			MakeMeta(htblColNameType);
			
			System.out.println("Created");
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void MakeMeta ( Hashtable<String,String> htblColNameTypehtblColNameType)throws DBAppException{
		
	 
		/*ObjectOutputStream ObjectOutputStream;
		try {
			ObjectOutputStream = new ObjectOutputStream(new FileOutputStream(
			new File(this.strTableName+"0" + ".ser"))); //intially writing the table as a big fat serializable thing .. make this our first page XD ..  when we merge i'll be put it in the array of pages
			ObjectOutputStream.writeObject(this);   /// instead of .strtablename it will be 0.ser 
			ObjectOutputStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		try {
			FileWriter writer = new FileWriter(this.strTableName+"metadata.csv");
			

			Set<Entry<String, String>> FirstTuple = htblColNameType.entrySet();
			
			
			Iterator <Entry<String, String>> Iterator= FirstTuple.iterator();
			writer.append("Table name"+",");
			writer.append("Column Name"+",");
			writer.append("Column Type"+",");
			writer.append("Key?"+",");
			writer.append("Indexed?"+",");
			writer.append("\n");
			while (Iterator.hasNext()) {
				Entry<String, String> en = Iterator.next();
				writer.append(strTableName+",");
				writer.append(en.getKey()+",");
				writer.append(en.getValue()+",");
				if (strClusteringKeyColumn.equals(en.getKey())) {
					writer.append("True"+",");
				} else {
					writer.append("False"+",");
				}
				writer.append("False"+",");
				
				writer.append("\n");
			}

			// generate whatever data you want

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("meta");
	}
	
	// checks if no pages exist or page is full to create new page and insert in it then add it to ArrayList Pages
	// if page exist and not full it inserts Into the page
	public void insertIntoTable(Hashtable<String, Object> htblColNameVale){
		if(Pages.isEmpty()|| Pages.get(Pages.size()-1).check()){
			File file = new File(this.strTableName+" Table Page "+(Pages.size()+1)+ ".ser");
			Page page = new Page((Pages.size()+1)+"",htblColNameVale.size()*2,file);//doesnt work with (htblColNameval.size()*2)-1??
			Pages.add(page);		
		}
			
		Pages.get(Pages.size()-1).insertIntoPage(htblColNameVale);
		
	}
}
