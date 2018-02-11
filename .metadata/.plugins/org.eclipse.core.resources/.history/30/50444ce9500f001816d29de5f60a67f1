import java.util.Hashtable;


public class DbApp {
	
	public static void createTable(String strTableName, //to do Create Table
			String strClusteringKeyColumn,
			Hashtable<String,String> htblColNameType )
			throws DBAppException {
		new Table(strTableName,strClusteringKeyColumn,htblColNameType );
		
		
		
	}
	public static void main(String[]args){
		String strTableName = "Student";
		@SuppressWarnings("rawtypes")
		Hashtable htblColNameType = new Hashtable( );
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("gpa", "java.lang.double");
		
		try {
			
			createTable( strTableName, "id", htblColNameType );
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
