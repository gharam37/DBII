import java.util.Hashtable;


public class Table {
	//should implement serializable 
	//should have an attribute arraylist of pages , a hashtable for type , a string for name 
    
	///should have an insert method called by DbApp .. it called an insert method in Page
	
	//should implement serializable 
	//should have an attribute arraylist of pages , a hashtable for type , a string for name 
    ///should have an insert method called by DbApp .. it called an insert method in Page
	
	String strClusteringKeyColumn; //table primary key
	String strTableName; //table name
	Hashtable<String,String> htblColNameType; // hashtable of the attributes and their types.. to put inserted in a metadata file
	public Table(String strTableName,
			String strClusteringKeyColumn, 
			Hashtable<String,String> htblColNameType){ //to do the exception 
		
		
		MakeMeta(htblColNameType);
		
	}
	
	public void MakeMeta( Hashtable<String,String> htblColNameTypehtblColNameType){
		
		////////////ToDo Write index info into a MetaData File
		
		
	}
}
