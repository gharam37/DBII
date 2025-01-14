//package Task1;

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
	/*public static void CreateDense(Table table,String ColumnName) throws DBAppException {
		table.CreatDense(ColumnName);
		
	}*/
	public static void updateTable(String strTableName,String strKey, Hashtable<String,Object> htblColNameVale) throws DBAppException{
		boolean Flag = true;
		for(int i = 0; i<Tables.size();i++){
			if((((Table)Tables.get(i)).strTableName).equals(strTableName)){
				((Table)Tables.get(i)).updateTable(htblColNameVale,strKey);
				
				Flag = false;
				break;
			}
		}
		if(Flag){
			System.out.println("Table Name not found");
		}
	}
	public static void CreateBrinIndex(String strTableName,String ColumnName) throws DBAppException{
		//table.CreateDenseTable(ColumnName);
		boolean Flag = true;
		for(int i = 0; i<Tables.size();i++){
			if((((Table)Tables.get(i)).strTableName).equals(strTableName)){
				((Table)Tables.get(i)).CreateBrinIndex(ColumnName);
				
				Flag = false;
				break;
			}
		}
		if(Flag){
			System.out.println("Table Name not found");
		}
		
	}
	
	public static void deleteFromTable(String strTableName,Hashtable<String,Object> htblColNameValue)throws DBAppException{
		boolean Flag = true;
		for(int i = 0; i<Tables.size();i++){
			if((((Table)Tables.get(i)).strTableName).equals(strTableName)){
				((Table)Tables.get(i)).DeleteFromTable(htblColNameValue);
				
				Flag = false;
				break;
			}
		}
		if(Flag){
			System.out.println("Table Name not found");
		}
	}
	
	public static ArrayList<Hashtable<String, Object>> selectFromTable(String strTableName, String strColumnName, Object[] objarrValues, String[] strarrOperators) throws DBAppException{
		
		boolean Flag = true;
		for(int i = 0; i<Tables.size();i++){
			if((((Table)Tables.get(i)).strTableName).equals(strTableName)){
				return ((Table)Tables.get(i)).SearchingFromTable(objarrValues, strarrOperators, strColumnName);
				
			}
		}
		if(Flag){
			System.out.println("Table Name not found");
		}
		return new ArrayList<Hashtable<String, Object>>();
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[]args) throws DBAppException, InterruptedException{
		
		
		
		
		String strTableName1 = "Person";
		@SuppressWarnings("rawtypes")
		Hashtable htblColNameType1 = new Hashtable( );
		htblColNameType1.put("name", "java.lang.String");
		htblColNameType1.put("address", "java.lang.String");
		htblColNameType1.put("Social Security number", "java.lang.Integer");
		htblColNameType1.put("age", "java.lang.double");

		try {
			
			createTable( strTableName1, "name", htblColNameType1 );
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Hashtable<String,Object> htblColName20 = new Hashtable<String,Object>( );
		htblColName20.put("Social Security number", new Integer( 23498 ));
		htblColName20.put("name", new String("Cenq David" ) );
		htblColName20.put("address", new String("hamdy st" ) );
		htblColName20.put("age", new Integer( 30 ) );
		//Thread.sleep(1000000);
		insertIntoTable( strTableName1 , htblColName20 );
	
		Hashtable<String,Object> htblColName10 = new Hashtable<String,Object>( );
		htblColName10.put("Social Security number", new Integer( 453455 ));
		htblColName10.put("address", new String("hamdy st2" ) );
		htblColName10.put("name", new String("Ahmed Mohammed" ) );
		
		htblColName10.put("age", new Integer( 50 ) );
		insertIntoTable( strTableName1 , htblColName10 );
		
		Hashtable<String,Object> htblColName40 = new Hashtable<String,Object>( );
		htblColName40.put("Social Security number", new Integer( 453455 ));
		htblColName40.put("name", new String("Bassem Mahmoud" ) );
		htblColName40.put("address", new String("nasr st" ) );
		htblColName40.put("age", new Integer( 30 ) );
		insertIntoTable( strTableName1 , htblColName40 );
		//Thread.sleep(100000);
		Hashtable<String,Object> htblColName50 = new Hashtable<String,Object>( );
		htblColName50.put("Social Security number", new Integer( 453455 ));
		htblColName50.put("name", new String("Bassem Ahmed" ) );
		htblColName50.put("address", new String("m7md st" ) );
		htblColName50.put("age", new Integer( 30 ) );
		insertIntoTable( strTableName1 , htblColName50 );
		Hashtable<String,Object> htblColName30 = new Hashtable<String,Object>( );

		htblColName30.put("Social Security number", new Integer( 78452 ));
		htblColName30.put("name", new String("Darine Noor" ) );
		htblColName30.put("address", new String("bakr st" ) );
		htblColName30.put("age", new Integer( 50 ) );
		insertIntoTable( strTableName1 , htblColName30);
		
		Hashtable<String,Object> htblColName60 = new Hashtable<String,Object>( );

		htblColName60.put("Social Security number", new Integer( 78452 ));
		htblColName60.put("name", new String("Darine Omar" ) );
		htblColName60.put("address", new String("zaky st" ) );
		htblColName60.put("age", new Integer( 50 ) );
		insertIntoTable( strTableName1 , htblColName60);
		
		Hashtable<String,Object> htblColName70 = new Hashtable<String,Object>( );

		htblColName70.put("Social Security number", new Integer( 78452 ));
		htblColName70.put("name", new String("Darine othman" ) );
		htblColName70.put("address", new String("zaky st1" ) );
		htblColName70.put("age", new Integer( 50 ) );
		insertIntoTable( strTableName1 , htblColName70);
		
		Hashtable<String,Object> htblColName80 = new Hashtable<String,Object>( );

		htblColName80.put("Social Security number", new Integer( 123456789 ));
		htblColName80.put("name", new String("Darine othman" ) );
		htblColName80.put("address", new String("zaky st2" ) );
		htblColName80.put("age", new Integer( 123456789 ) );
		
		Hashtable<String,Object> htblColName100 = new Hashtable<String,Object>( );

		htblColName100.put("Social Security number", new Integer( 2937 ));
		htblColName100.put("name", new String("Darine ozhman" ) );
		htblColName100.put("address", new String("zaky st5" ) );
		htblColName100.put("age", new Integer( 1234 ) );
		insertIntoTable( strTableName1, htblColName100);
		
		Hashtable<String,Object> htblColName90 = new Hashtable<String,Object>( );
		htblColName90.put("Social Security number", new Integer( 67891189 ));
		htblColName90.put("address", new String("Bakry st" ) );
		htblColName90.put("name", new String("Cenq David one" ) );
		htblColName90.put("age", new Integer( 2048 ) );
		 insertIntoTable( strTableName1 , htblColName90);
		for(int i =0;i<200;i++){
	        Hashtable<String,Object> htblColName4 = new Hashtable<String,Object>( );
	        htblColName4.put("Social Security number", new Integer( i ));
	        htblColName4.put("address", new String("Adham st" )+i );
			htblColName4.put("name", new String("name" +i) );
			htblColName4.put("age", new Integer( i%100 ) );
	        insertIntoTable( strTableName1 , htblColName4 );
	        //System.out.println(i);
	        }
	
		CreateBrinIndex(strTableName1,"age");
		
		CreateBrinIndex(strTableName1,"address");
		
		try        
		{
		    Thread.sleep(10000);
		    deleteFromTable( strTableName1,htblColName20);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
		
		
		Hashtable<String,Object> htblColName900 = new Hashtable<String,Object>( );
		htblColName900.put("Social Security number", new Integer(19000));
		htblColName900.put("address", new String("Nasr St" ) );
		htblColName900.put("name", new String("Gharam Zakaria" ) );
		htblColName900.put("age", new Integer( 20 ) );
		
		Hashtable<String,Object> htblColName901 = new Hashtable<String,Object>( );
		htblColName901.put("Social Security number", new Integer(19000));
		htblColName901.put("address", new String("zah nasr St" ) );
		htblColName901.put("name", new String("Gharam Zakaria" ) );
		htblColName901.put("age", new Integer( 20 ) );
		try        
		{
		    Thread.sleep(10000);
		    insertIntoTable( strTableName1,htblColName900);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
		
		try        
		{
		    Thread.sleep(10000);
		    updateTable( strTableName1,"Gharam Zakaria",htblColName901);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
		
		Object[]objarrValues = new Object[2];
		objarrValues[0] = new Integer(55);
		objarrValues[1] = new Integer(45);
		String[] strarrOperators = new String[2];
		strarrOperators[0] = ">="; 
		strarrOperators[1] = ">=";
		ArrayList<Hashtable<String, Object>> resultSet =  selectFromTable(strTableName1, "age",
		objarrValues, strarrOperators );
		for(int i = 0; i<resultSet.size();i++){
			System.out.println(resultSet.get(i));
		}
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
		}
		Hashtable<String,Object> htblColNameValueGharamWantsToChange = new Hashtable<String,Object>( );

		htblColNameValueGharamWantsToChange.put("id", new Integer( 2343432 ));
		htblColNameValueGharamWantsToChange.put("name", new String("Ahmed Noor" ) );
		htblColNameValueGharamWantsToChange.put("gpa", new Double( 0.95 ) );
		insertIntoTable( strTableName , htblColNameValueGharamWantsToChange );
		//Thread.sleep(10);
		htblColNameValueGharamWantsToChange.clear( );
		
		Hashtable<String,Object> htblColName1 = new Hashtable<String,Object>( );

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
		insertIntoTable( strTableName , htblColName3);
		
		Hashtable<String,Object> htblColName5 = new Hashtable<String,Object>( );

		htblColName5.put("id", new Integer( 78452 ));
		htblColName5.put("name", new String("Zaky balabizohahhaha" ) );
		htblColName5.put("gpa", new Double( 123413244.29292929292 ) );
		updateTable( strTableName,"", htblColName5);
		
		
		Hashtable<String,Object> htblColName6 = new Hashtable<String,Object>( );

		htblColName6.put("id", new Integer( 453455 ));
		htblColName6.put("name", new String("Ahmed Mohammed abdullah khaled bassim aly mohamed" ) );
		htblColName6.put("gpa", new Double( 199799.199799 ) );
		updateTable( strTableName ,"", htblColName6 );
		deleteFromTable( strTableName,htblColName4);
		deleteFromTable( strTableName,htblColName3); */
		
		
	
		
		
		
		

		
		}
		
		
	}



