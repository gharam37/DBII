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
	public static void insertIntoTable(String strTableName,Hashtable<String,Object> htblColNameVale) throws DBAppException{
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
	public static void main(String[]args) throws DBAppException, InterruptedException{
		/*String strTableName = "Student";
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
		}*/

		/*htblColNameValue.put("id", new Integer( 2343432 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		insertIntoTable( strTableName , htblColNameValue );
		//Thread.sleep(10);
		htblColNameValue.clear( );*/
		/*Hashtable<String,Object> htblColName1 = new Hashtable<String,Object>( );

		htblColName1.put("id", new Integer( 453455 ));
		htblColName1.put("name", new String("Ahmed Mohammed" ) );
		htblColName1.put("gpa", new Double( 0.95 ) );
		insertIntoTable( strTableName , htblColName1 );
		//Thread.sleep(10000);
		Hashtable<String,Object> htblColName4 = new Hashtable<String,Object>( );

		htblColName4.put("id", new Integer( 5674567 ));
		htblColName4.put("name", new String("Dalia Noor" ) );
		htblColName4.put("gpa", new Double( 1.25 ) );
		insertIntoTable( strTableName , htblColName4 );
		//Thread.sleep(100000);
		
		Hashtable<String,Object> htblColName2 = new Hashtable<String,Object>( );
		htblColName2.put("id", new Integer( 23498 ));
		htblColName2.put("name", new String("John Noor" ) );
		htblColName2.put("gpa", new Double( 1.5 ) );
		//Thread.sleep(1000000);
		insertIntoTable( strTableName , htblColName2 );
		
		Hashtable<String,Object> htblColName3 = new Hashtable<String,Object>( );

		htblColName3.put("id", new Integer( 78452 ));
		htblColName3.put("name", new String("Zaky Noor" ) );
		htblColName3.put("gpa", new Double( 0.88 ) );
		insertIntoTable( strTableName , htblColName3);*/
		
		/*String a="ala";
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
		}*/
		
		
		
		
		
		
		
		
		
		
		
		String strTableName = "Person";
		@SuppressWarnings("rawtypes")
		Hashtable htblColNameType = new Hashtable( );
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("Social Security number", "java.lang.Integer");
		htblColNameType.put("age", "java.lang.double");

		try {
			
			createTable( strTableName, "name", htblColNameType );
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Hashtable<String,Object> htblColName2 = new Hashtable<String,Object>( );
		htblColName2.put("Social Security number", new Integer( 23498 ));
		htblColName2.put("name", new String("Cenq David" ) );
		htblColName2.put("age", new Integer( 30 ) );
		//Thread.sleep(1000000);
		insertIntoTable( strTableName , htblColName2 );
	
		Hashtable<String,Object> htblColName1 = new Hashtable<String,Object>( );
		htblColName1.put("name", new String("Ahmed Mohammed" ) );
		htblColName1.put("Social Security number", new Integer( 453455 ));
		htblColName1.put("age", new Integer( 20 ) );
		insertIntoTable( strTableName , htblColName1 );
		
		Hashtable<String,Object> htblColName4 = new Hashtable<String,Object>( );

		htblColName4.put("name", new String("Bassem Mahmoud" ) );
		htblColName4.put("Social Security number", new Integer( 453455 ));
		htblColName4.put("age", new Integer( 30 ) );
		insertIntoTable( strTableName , htblColName4 );
		//Thread.sleep(100000);
		
		Hashtable<String,Object> htblColName3 = new Hashtable<String,Object>( );

		htblColName3.put("Social Security number", new Integer( 78452 ));
		htblColName3.put("name", new String("Darine Noor" ) );
		htblColName3.put("age", new Integer( 50 ) );
		insertIntoTable( strTableName , htblColName3);
		
		
		
		
		
		
		/*String a="ala";
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
			e.printStackTrace();*/
		}
		
		
	}



