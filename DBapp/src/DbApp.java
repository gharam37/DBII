import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;


public class DbApp {
	
	static ArrayList<Table> Tables = new ArrayList<Table>();
	
	public static void createTable(String strTableName, //to do Create Table
			String strClusteringKeyColumn,
			Hashtable<String,String> htblColNameType )
			
			throws DBAppException {
		
		Table Table= new Table(strTableName,strClusteringKeyColumn,htblColNameType );
		Tables.add(Table);
	}
	// inserts into table if table name inserted exists
	public static void insertIntoTable(String strTableName,Hashtable<String,Object> htblColNameVale){
		boolean Flag = true;
		for(int i = 0; i<Tables.size();i++){
			if((((Table)Tables.get(i)).strTableName).equals(strTableName)){
				((Table)Tables.get(i)).insertIntoTable(htblColNameVale);
				Flag = false;
				break;
			}
		}
		if(Flag){
			System.out.println("Table Name not found");
		}
	}
			
	
	@SuppressWarnings("unchecked")
	public static void main(String[]args){
		/*String strTableName = "Student";
		@SuppressWarnings("rawtypes")
		Hashtable htblColNameType = new Hashtable( );
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("gpa", "java.lang.double");
		Hashtable<String,Object> htblColNameValue = new Hashtable<String,Object>( );
		try {
			
			createTable( strTableName, "id", htblColNameType );
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		htblColNameValue.put("id", new Integer( 2343432 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 453455 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("name", new String("Dalia Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.25 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		
		htblColNameValue.put("id", new Integer( 23498 ));
		htblColNameValue.put("name", new String("John Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.5 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		
		htblColNameValue.put("id", new Integer( 78452 ));
		htblColNameValue.put("name", new String("Zaky Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.88 ) );
		insertIntoTable( strTableName , htblColNameValue );*/
		
		String a="ala";
		String b="bna";
		try {
			byte[] infoBin = a.getBytes("UTF-8");
			byte[] infoBin1 = b.getBytes("UTF-8");
			for(int i=0;i<infoBin.length;i++){
				System.out.print(infoBin[i]+"  ");
			}
			System.out.println("");
			for(int i=0;i<infoBin1.length;i++){
				System.out.print(infoBin1[i]+"  ");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}

