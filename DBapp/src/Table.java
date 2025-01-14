import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import javafx.util.Pair;

public class Table implements Serializable {
	// should implement serializable
	// should have an attribute arraylist of pages , a hashtable for type , a
	// string for name

	// /should have an insert method called by DbApp .. it called an insert
	// method in Page

	// should implement serializable
	// should have an attribute arraylist of pages , a hashtable for type , a
	// string for name
	// /should have an insert method called by DbApp .. it called an insert
	// method in Page

	String strClusteringKeyColumn; // table primary key
	String strTableName; // table name
	private static final long serialVersionUID = 1L;
	ArrayList<DenseIndex> denseIndecies;
	ArrayList<BrinIndex> BrinIndecies = new ArrayList<BrinIndex>();

	// BrinFirst FirstBrins;
	// BrinIndex BrinIndex;
	ArrayList<Page> Pages;
	Hashtable<String, String> htblColNameType;// hashtable of the attributes and
												// their types.. to put inserted
												// in a metadata file

	public Table(String strTableName, String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType) { // to do the exception
		this.strTableName = strTableName;
		this.strClusteringKeyColumn = strClusteringKeyColumn;
		this.htblColNameType = htblColNameType;
		this.Pages = new ArrayList<Page>();

		try {
			MakeMeta(htblColNameType);

			// System.out.println("Created");
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * public void CreatDense(String ColumnName) throws DBAppException{ boolean
	 * foundname=false;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Set<Entry<String, String>> FirstTuple = htblColNameType.entrySet();
	 * 
	 * 
	 * Iterator <Entry<String, String>> Iterator= FirstTuple.iterator();
	 * 
	 * 
	 * while (Iterator.hasNext()) { Entry<String, String> en = Iterator.next();
	 * 
	 * String Key=en.getKey(); if(Key.equals(ColumnName)){ foundname=true; }
	 * 
	 * if(!foundname){ throw new DBAppException(); // } else { for(int
	 * i=0;i<this.Pages.size();i++){ this.Pages.get(i).CreateDense(ColumnName);
	 * } }
	 * 
	 * }
	 * 
	 * 
	 * }
	 */
	public void MakeMeta(
			Hashtable<String, String> htblColNameTypehtblColNameType)
			throws DBAppException {

		/*
		 * ObjectOutputStream ObjectOutputStream; try { ObjectOutputStream = new
		 * ObjectOutputStream(new FileOutputStream( new
		 * File(this.strTableName+"0" + ".ser"))); //intially writing the table
		 * as a big fat serializable thing .. make this our first page XD ..
		 * when we merge i'll be put it in the array of pages
		 * ObjectOutputStream.writeObject(this); /// instead of .strtablename it
		 * will be 0.ser ObjectOutputStream.close();
		 * 
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		try {
			FileWriter writer = new FileWriter(this.strTableName
					+ "metadata.csv");

			Set<Entry<String, String>> FirstTuple = htblColNameType.entrySet();

			Iterator<Entry<String, String>> Iterator = FirstTuple.iterator();
			writer.append("Table name" + ",");
			writer.append("Column Name" + ",");
			writer.append("Column Type" + ",");
			writer.append("Key?" + ",");
			writer.append("Indexed?" + ",");
			writer.append("\n");
			while (Iterator.hasNext()) {
				Entry<String, String> en = Iterator.next();
				writer.append(strTableName + ",");
				writer.append(en.getKey() + ",");
				writer.append(en.getValue() + ",");
				if (strClusteringKeyColumn.equals(en.getKey())) {
					writer.append("True" + ",");
				} else {
					writer.append("False" + ",");
				}
				writer.append("False" + ",");

				writer.append("\n");
			}

			// generate whatever data you want

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("meta");
	}

	// checks if no pages exist or page is full to create new page and insert in
	// it then add it to ArrayList Pages
	// if page exist and not full it inserts Into the page
	public void insertIntoTable(Hashtable<String, Object> htblColNameVale)
			throws DBAppException {

		LocalDateTime now = LocalDateTime.now();

		htblColNameVale.put("Last updated", now);

		if (Pages.isEmpty() || Pages.get(Pages.size() - 1).check()) {
			// System.out.println("made page");
			File file = new File(this.strTableName + " Table Page "
					+ (Pages.size() + 1) + ".class");
			Page page = new Page((Pages.size() + 1), file,
					strClusteringKeyColumn);

			Pages.add(page);
		}

		boolean IsString = false;

		if (htblColNameVale.containsKey(strClusteringKeyColumn)) {
			Object key = htblColNameVale.get(strClusteringKeyColumn);
			if (key instanceof String) {

				// System.out.println("Here"); //So the bug isn't cased by this
				// if
				String primary = (String) key;
				// System.out.println(key);
				/*
				 * Long parseInt = (long) 0; try { String clusterKeyPrimary =
				 * "0"; byte[] infoBin = primary.getBytes("UTF-8"); for(int i =
				 * infoBin.length-1;i>0;i--){
				 * clusterKeyPrimary=infoBin[i]+""+clusterKeyPrimary; }
				 * parseInt= Long.parseLong(clusterKeyPrimary);
				 */

				for (int i = 0; i < Pages.size(); i++) { // /this is disgusting
															// .. im ashamed of
															// u romy
					Page p = Pages.get(i);
					LinkedList<Hashtable<String, Object>> tuples = p.tuples;
					if (!tuples.isEmpty()) {
						Hashtable<String, Object> first = tuples.getFirst();
						Hashtable<String, Object> Last = tuples.getLast();
						String firstValue = (String) first
								.get(strClusteringKeyColumn);
						String SecondValue = (String) Last
								.get(strClusteringKeyColumn);
						int Upper = primary.compareTo(firstValue);

						int Lower = primary.compareTo(SecondValue);
						// System.out.println(Upper);
						// System.out.println(Lower);

						/*
						 * String FirstKey= "0"; String SecondKey="0"; byte[]
						 * ByteFirst = firstValue.getBytes("UTF-8"); byte[]
						 * ByteSecond = SecondValue.getBytes("UTF-8"); for(int j
						 * = ByteFirst.length-1;j>0;j--){
						 * FirstKey=ByteFirst[i]+""+FirstKey;}
						 * 
						 * for(int j = ByteSecond.length-1;j>0;j--){
						 * SecondKey=ByteSecond[j]+""+SecondKey;
						 * 
						 * } long firstParsed=Long.parseLong(FirstKey); long
						 * secondParsed=Long.parseLong(SecondKey);
						 */
						// / this check is for when the value exists between the
						// first two values of two pages
						// indicating that the value must be inserted in the
						// first page of
						if ((Upper > 0 && Lower < 0) || (Upper > 0)
								|| (Lower < 0)) { // ////Fixed the exception

							p.insertIntoPage(htblColNameVale, primary, -1,
									!IsString);

							// p.loadPage(i,this.strTableName);
							if (p.check()) {
								updatePages(i, primary, -1, !IsString);
							}
							LoadAll();
							break;
						}

					}

					else {
						p.insertIntoPage(htblColNameVale, primary, -1,
								!IsString); // Increased attributes
						// p.loadPage(i,this.strTableName);
						if (p.check()) {
							updatePages(i, primary, -1, !IsString);
						}
						LoadAll();
						break;
					}

				}
			}
			//

			else {
				// case numerical

				int key1 = (int) htblColNameVale.get(strClusteringKeyColumn);

				for (int i = 0; i < Pages.size(); i++) { // /this is disgusting
															// .. im ashamed of
															// u romy
					Page p = Pages.get(i);
					LinkedList<Hashtable<String, Object>> tuples = p.tuples;

					if (!tuples.isEmpty()) {
						// System.out.println(tuples.size());
						// System.out.println(tuples.get(0));

						// System.out.println(key1);
						Hashtable<String, Object> first = tuples.getFirst();

						// System.out.println(tuples.getFirst());
						Hashtable<String, Object> Last = Pages.get(i).tuples
								.getLast();
						int firstValue = (int) first
								.get(strClusteringKeyColumn);
						// System.out.println(firstValue+"first"); //it prints
						// our value
						int SecondValue = (int) Last
								.get(strClusteringKeyColumn);
						// System.out.println(SecondValue+"last");

						/*
						 * if(key1>firstValue){
						 * //p.insertIntoPage(htblColNameVale, key1,IsString);
						 * tuples.addLast(htblColNameVale);
						 * 
						 * }
						 * 
						 * else if(key1<SecondValue){
						 * 
						 * tuples.addFirst(htblColNameVale); }
						 */

						if ((key1 > firstValue && key1 < SecondValue)
								|| (key1 < SecondValue) || (key1 > firstValue)) { // missing
																					// case

							p.insertIntoPage(htblColNameVale, "", key1,
									IsString);

							// p.loadPage(i,this.strTableName);
							if (p.check()) {
								updatePages(i, "", key1, IsString);
							}
							LoadAll();
							break;
						}

					}

					else {
						p.insertIntoPage(htblColNameVale, "", key1, IsString);

						if (p.check()) {
							updatePages(i, "", key1, IsString);
						}
						LoadAll();
						break;
					}
				}

			}

		}

		else {
			throw new DBAppException("please add the clustering key");// TO-DO
																		// message
		}

		UpdateBrins();

	}

	public void LoadAll() {
		for (int i = 0; i < Pages.size(); i++) {
			Pages.get(i).loadPage(i, this.strTableName);
		}
	}

	public void updatePages(int startingPage, String key, int strClusterKey,
			boolean flag) {

		for (int i = startingPage; i < Pages.size() - 1; i++) {
			if ((Pages.get(i).check()))
				try {

					Pages.get(i + 1).insertIntoPage(
							(Pages.get(i).tuples.getLast()), key,
							strClusterKey, flag);
					Pages.get(i).tuples.removeLast();

					// Pages.get(i).currentLine--;
					// Pages.get(i+1).currentLine++;

				} catch (DBAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		/*
		 * for(int i = 0;i<Pages.size();i++){
		 * System.out.println(Pages.get(i).tuples.size()+"::://*");
		 * //System.out.println(Pages.get(i).currentLine); }
		 */

	}

	// ///////////////////////////////////////////////update
	// method/////////////////////////////////////////////////////////////////////////

	public void updateTable(Hashtable<String, Object> htblColNameVale,
			String strKey) throws DBAppException {
		// DateTimeFormatter dtf =
		// DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		// System.out.println(dtf.format(now)); //testing if it prints
		// correcting
		htblColNameVale.put("Last updated", now);

		boolean IsString = false;

		if (htblColNameVale.containsKey(strClusteringKeyColumn)) {
			Object key = htblColNameVale.get(strClusteringKeyColumn);
			if (key instanceof String) {

				// System.out.println("Here"); //So the bug isn't cased by this
				// if
				String primary = (String) key;
				// System.out.println(key);
				/*
				 * Long parseInt = (long) 0; try { String clusterKeyPrimary =
				 * "0"; byte[] infoBin = primary.getBytes("UTF-8"); for(int i =
				 * infoBin.length-1;i>0;i--){
				 * clusterKeyPrimary=infoBin[i]+""+clusterKeyPrimary; }
				 * parseInt= Long.parseLong(clusterKeyPrimary);
				 */

				for (int i = 0; i < Pages.size(); i++) { // /this is disgusting
															// .. im ashamed of
															// u romy
					Page p = Pages.get(i);
					LinkedList<Hashtable<String, Object>> tuples = p.tuples;
					if (!tuples.isEmpty()) {
						Hashtable<String, Object> first = tuples.getFirst();
						Hashtable<String, Object> Last = tuples.getLast();
						String firstValue = (String) first
								.get(strClusteringKeyColumn);
						String SecondValue = (String) Last
								.get(strClusteringKeyColumn);
						int Upper = primary.compareTo(firstValue);

						int Lower = primary.compareTo(SecondValue);
						// System.out.println(Upper);
						// System.out.println(Lower);

						/*
						 * String FirstKey= "0"; String SecondKey="0"; byte[]
						 * ByteFirst = firstValue.getBytes("UTF-8"); byte[]
						 * ByteSecond = SecondValue.getBytes("UTF-8"); for(int j
						 * = ByteFirst.length-1;j>0;j--){
						 * FirstKey=ByteFirst[i]+""+FirstKey;}
						 * 
						 * for(int j = ByteSecond.length-1;j>0;j--){
						 * SecondKey=ByteSecond[j]+""+SecondKey;
						 * 
						 * } long firstParsed=Long.parseLong(FirstKey); long
						 * secondParsed=Long.parseLong(SecondKey);
						 */
						// / this check is for when the value exists between the
						// first two values of two pages
						// indicating that the value must be inserted in the
						// first page of
						if ((Upper > 0 && Lower < 0) || (Upper > 0)
								|| (Lower < 0)) { // ////Fixed the exception

							p.updateIntoPage(htblColNameVale, primary, -1,
									!IsString);

							// p.loadPage(i,this.strTableName);

							LoadAll();
							break;
						}

					}

					else {
						throw new DBAppException(
								"Sorry this table is empty insert first");

					}

				}
			}
			//

			else {
				// case numerical

				int key1 = (int) htblColNameVale.get(strClusteringKeyColumn);

				for (int i = 0; i < Pages.size(); i++) { // /this is disgusting
															// .. im ashamed of
															// u romy
					Page p = Pages.get(i);
					LinkedList<Hashtable<String, Object>> tuples = p.tuples;

					if (!tuples.isEmpty()) {
						// System.out.println(tuples.size());
						// System.out.println(tuples.get(0));

						// System.out.println(key1);
						Hashtable<String, Object> first = tuples.getFirst();

						// System.out.println(tuples.getFirst());
						Hashtable<String, Object> Last = Pages.get(i).tuples
								.getLast();
						int firstValue = (int) first
								.get(strClusteringKeyColumn);
						// System.out.println(firstValue+"first"); //it prints
						// our value
						int SecondValue = (int) Last
								.get(strClusteringKeyColumn);
						// System.out.println(SecondValue+"last");

						/*
						 * if(key1>firstValue){
						 * //p.insertIntoPage(htblColNameVale, key1,IsString);
						 * tuples.addLast(htblColNameVale);
						 * 
						 * }
						 * 
						 * else if(key1<SecondValue){
						 * 
						 * tuples.addFirst(htblColNameVale); }
						 */

						if ((key1 > firstValue && key1 < SecondValue)
								|| (key1 < SecondValue) || (key1 > firstValue)) { // missing
																					// case

							p.updateIntoPage(htblColNameVale, "", key1,
									IsString);

							// p.loadPage(i,this.strTableName);

							LoadAll();
							break;
						}

					}

					else {
						throw new DBAppException(
								"Sorry this table is empty insert first");
					}
				}

			}

		}

		else {
			throw new DBAppException("please add the clustering key");// TO-DO
																		// message
		}

		UpdateBrins();
	}

	public void CreateBrinIndex(String ColumnName) throws DBAppException {
		boolean foundname = false;
		Set<Entry<String, String>> FirstTuple = htblColNameType.entrySet();

		Iterator<Entry<String, String>> Iterator = FirstTuple.iterator();

		while (Iterator.hasNext()) {
			Entry<String, String> en = Iterator.next();

			String Key = en.getKey();
			if (Key.equals(ColumnName)) {
				foundname = true;
			}

		}

		if (!foundname) {

			throw new DBAppException("sorry this column is not found"); // }
																		// else
																		// {
																		// for(int
		} else {
			// here
			DenseIndex dense = new DenseIndex(this, ColumnName);
			// this.FirstBrins=new BrinFirst(dense);
			// denseIndecies.add(dense);
			BrinFirst FirstBrin = new BrinFirst(dense);
			BrinIndex BrinIndex = new BrinIndex(FirstBrin);
			this.BrinIndecies.add(BrinIndex);
			UpdateMeta();

		}

	}

	public ArrayList<Hashtable<String, Object>> SearchingFromTable(
			Object[] objarrValues, String[] strarrOperators, String ColumnName) {
		ArrayList<Hashtable<String, Object>> Iterator = null;
		boolean found = false;

		for (int i = 0; i < this.BrinIndecies.size(); i++) {
			if (ColumnName.equals(BrinIndecies.get(i).ColumnName)) {

				found = true;

			}

		}

		if (found) {

			if (objarrValues[0] instanceof Integer) {
				Iterator = SearchInInt(objarrValues, strarrOperators,
						ColumnName);
			} else {
				objarrValues[0] += "";
				if (objarrValues.length == 2)
					objarrValues[1] += "";
				Iterator = SearchInString(objarrValues, strarrOperators,
						ColumnName);
			}

		}
		return Iterator;
	}

	public ArrayList<Hashtable<String, Object>> SearchInInt(
			Object[] objarrValues, String[] strarrOperators, String ColumnName) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		String op1 = strarrOperators[0];

		Object Val1 = objarrValues[0];
		int Brin = -1;
		for (int i = 0; i < BrinIndecies.size(); i++) {
			if (BrinIndecies.get(i).ColumnName.equals(ColumnName))
				Brin = i;
		}
		// if(Brin==-1){
		// return Iterator;
		// }
		if (strarrOperators.length == 2) {
			String op2 = strarrOperators[1];
			Object Val2 = objarrValues[1];
			ArrayList<Hashtable<String, Object>> Iterator1 = new ArrayList<Hashtable<String, Object>>();
			ArrayList<Hashtable<String, Object>> Iterator2 = new ArrayList<Hashtable<String, Object>>();

			if (op1.equals(">=") && op2.equals("<")) {

				Val1 = ((int) Val1) + 1;
				return GreaterThanInt(Math.min((int) Val1, (int) Val2), Brin);

			} else if (op1.equals(">=") && op2.equals("<=")) {
				return GreaterThanOrEqualInt(Math.min((int) Val1, (int) Val2),
						Brin);
			}

			else if (op1.equals(">") && op2.equals("<")) {

				return GreaterThanInt(Math.min((int) Val1, (int) Val2), Brin);
			} else if (op1.equals(">") && op2.equals("<=")) {
				Val2 = ((int) Val2) + 1;
				return GreaterThanInt(Math.min((int) Val1, (int) Val2), Brin);
			} else if (op1.equals("<") && op2.equals(">=")) {
				Val1 = ((int) Val1) + 1;

				return LessThanOrEqualInt(Math.max((int) Val1, (int) Val2),
						Brin);

			} else if (op1.equals("<=") && op2.equals(">=")) {
				return LessThanOrEqualInt(Math.max((int) Val1, (int) Val2),
						Brin);

			}
			else if (op1.equals("<=") && op2.equals(">")) {
				Val2 = ((int)Val2)-1;
				return LessThanOrEqualInt(Math.max((int) Val1, (int) Val2),
						Brin);

			}

			else if (op1.equals("<") && op2.equals(">")) {
				return LessThanInt(Math.max((int) Val1, (int) Val2), Brin);

			} else if (op1.equals("<") && op2.equals(">=")) {
				Val2 = ((int) Val2) + 1;

				return LessThanOrEqualInt(Math.max((int) Val1, (int) Val2),
						Brin);
			}
			//
			else if (op1.equals("<") && op2.equals("<")) {
				if (((int) Val1) < ((int) Val2)) {
					Iterator1 = LessThanInt(Val1, Brin);
					Iterator2 = GreaterThanInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			} else if (op1.equals("<") && op2.equals("<=")) {
				if (((int) Val1) <= ((int) Val2)) {
					Iterator1 = LessThanInt(Val1, Brin);
					Iterator2 = GreaterThanOrEqualInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			}

			else if (op1.equals("<=") && op2.equals("<")) {

				if (((int) Val1) <= ((int) Val2)) {
					Iterator1 = LessThanOrEqualInt(Val1, Brin);
					Iterator2 = GreaterThanInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;
			} else if (op1.equals("<=") && op2.equals("<=")) {
				if (((int) Val1) <= ((int) Val2)) {
					Iterator1 = LessThanOrEqualInt(Val1, Brin);
					Iterator2 = GreaterThanOrEqualInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;
			}
			//
			else if (op1.equals(">") && op2.equals(">")) {
				if (((int) Val1) > ((int) Val2)) {
					Iterator1 = GreaterThanInt(Val1, Brin);
					Iterator2 = LessThanInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			} else if (op1.equals(">") && op2.equals(">=")) {
				if (((int) Val1) >= ((int) Val2)) {
					Iterator1 = GreaterThanInt(Val1, Brin);
					Iterator2 = LessThanOrEqualInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			}

			else if (op1.equals(">=") && op2.equals(">")) {
				if (((int) Val1) >= ((int) Val2)) {
					Iterator1 = GreaterThanOrEqualInt(Val1, Brin);
					Iterator2 = LessThanOrEqualInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			} else if (op1.equals(">=") && op2.equals(">=")) {
				if (((int) Val1) >= ((int) Val2)) {
					Iterator1 = GreaterThanOrEqualInt(Val1, Brin);
					Iterator2 = LessThanOrEqualInt(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;
			}

		} else {

			if (op1.equals(">=")) {

				return GreaterThanOrEqualInt(Val1, Brin);

			} else if (op1.equals(">")) {

				return GreaterThanInt(Val1, Brin);

			} else if (op1.equals("<")) {

				return LessThanInt(Val1, Brin);

			} else if (op1.equals("<=")) {

				return LessThanOrEqualInt(Val1, Brin);

			}

		}

		return Iterator;
	}

	public ArrayList<Hashtable<String, Object>> SearchInString(
			Object[] objarrValues, String[] strarrOperators, String ColumnName) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		String op1 = strarrOperators[0];

		Object Val1 = objarrValues[0];
		int Brin = -1;
		for (int i = 0; i < BrinIndecies.size(); i++) {
			if (BrinIndecies.get(i).ColumnName.equals(ColumnName))
				Brin = i;
		}
		// if(Brin==-1){
		// return Iterator;
		// }
		if (strarrOperators.length == 2) {
			String op2 = strarrOperators[1];
			Object Val2 = objarrValues[1];
			ArrayList<Hashtable<String, Object>> Iterator1 = new ArrayList<Hashtable<String, Object>>();
			ArrayList<Hashtable<String, Object>> Iterator2 = new ArrayList<Hashtable<String, Object>>();
			String min = "";
			String max = "";
			if (op1.equals(">=") && op2.equals("<")) {
				min = minString("" + Val1, "" + Val2);

				return GreaterThanOrEqualString(min, Brin);

			} else if (op1.equals(">=") && op2.equals("<=")) {
				min = minString("" + Val1, "" + Val2);

				return GreaterThanOrEqualString(min, Brin);

			} else if (op1.equals(">") && op2.equals("<")) {

				min = minString("" + Val1, "" + Val2);

				return GreaterThanString(min, Brin);

			} else if (op1.equals(">") && op2.equals("<=")) {
				min = minString("" + Val1, "" + Val2);

				return GreaterThanOrEqualString(min, Brin);

			} else if (op1.equals("<") && op2.equals(">=")) {
				max = maxString("" + Val1, "" + Val2);

				return LessThanOrEqualString(max, Brin);

			} else if (op1.equals("<=") && op2.equals(">=")) {
				max = maxString("" + Val1, "" + Val2);

				return LessThanOrEqualString(max, Brin);

			}
			else if (op1.equals("<=") && op2.equals(">")) {
				max = maxString("" + Val1, "" + Val2);
				return LessThanOrEqualString(max,Brin);

			}else if (op1.equals("<") && op2.equals(">")) {
				max = maxString("" + Val1, "" + Val2);

				return LessThanString(max, Brin);

			} else if (op1.equals("<") && op2.equals(">=")) {
				max = maxString("" + Val1, "" + Val2);

				return LessThanOrEqualString(max, Brin);

			} else if (op1.equals("<") && op2.equals("<")) {
				if (((String) Val1).compareTo(((String) Val2)) < 0) {
					Iterator1 = LessThanString(((String) Val1), Brin);
					Iterator2 = GreaterThanString(((String) Val2), Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			} else if (op1.equals("<") && op2.equals("<=")) {
				if (((String) Val1).compareTo(((String) Val2)) <= 0) {
					Iterator1 = LessThanString(Val1, Brin);
					Iterator2 = GreaterThanOrEqualString(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			}

			else if (op1.equals("<=") && op2.equals("<")) {

				if (((String) Val1).compareTo(((String) Val2)) <= 0) {
					Iterator1 = LessThanOrEqualString(Val1, Brin);
					Iterator2 = GreaterThanString(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;
			} else if (op1.equals("<=") && op2.equals("<=")) {
				if (((String) Val1).compareTo(((String) Val2)) <= 0) {
					Iterator1 = LessThanOrEqualString(Val1, Brin);
					Iterator2 = GreaterThanOrEqualString(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;
			}
			//
			else if (op1.equals(">") && op2.equals(">")) {
				if (((String) Val1).compareTo(((String) Val2)) > 0) {
					Iterator1 = GreaterThanString(Val1, Brin);
					Iterator2 = LessThanString(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			} else if (op1.equals(">") && op2.equals(">=")) {
				if (((String) Val1).compareTo(((String) Val2)) >= 0) {
					Iterator1 = GreaterThanString(Val1, Brin);
					Iterator2 = LessThanOrEqualString(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			}

			else if (op1.equals(">=") && op2.equals(">")) {
				if (((String) Val1).compareTo(((String) Val2)) >= 0) {
					Iterator1 = GreaterThanOrEqualString(Val1, Brin);
					Iterator2 = LessThanOrEqualString(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;

			} else if (op1.equals(">=") && op2.equals(">=")) {
				if (((String) Val1).compareTo(((String) Val2)) >= 0) {
					Iterator1 = GreaterThanOrEqualString(Val1, Brin);
					Iterator2 = LessThanOrEqualString(Val2, Brin);
					for (int i = 0; i < Iterator1.size(); i++) {
						Hashtable<String, Object> hash1 = Iterator1.get(i);
						Object key1 = hash1.get(strClusteringKeyColumn);
						for (int j = 0; j < Iterator2.size(); j++) {
							Hashtable<String, Object> hash2 = Iterator2.get(j);
							Object key2 = hash2.get(strClusteringKeyColumn);
							if (key2.equals(key1)) {
								Iterator.add(hash1);
								break;
							}
						}
					}
				}

				return Iterator;
			}

		} else {

			if (op1.equals(">=")) {

				return GreaterThanOrEqualString(Val1, Brin);

			} else if (op1.equals(">")) {

				return GreaterThanString(Val1, Brin);

			} else if (op1.equals("<")) {

				return LessThanString(Val1, Brin);

			} else if (op1.equals("<=")) {

				return LessThanOrEqualString(Val1, Brin);

			}

		}

		return Iterator;
	}

	public static String minString(String Val1, String Val2) {
		if (Val1.compareTo(Val2) <= 0)
			return Val1;
		else
			return Val2;

	}

	public static String maxString(String Val1, String Val2) {
		if (Val1.compareTo(Val2) > 0)
			return Val1;
		else
			return Val2;

	}

	// ////////////////////////////////////////////////////////////Int Singleton
	// search operations//////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> GreaterThanOrEqualInt(
			Object Val1, int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity First = FirstPair.getKey();
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					First = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}
				if (((int) Val1) >= ((int) First.Value)) {

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;

					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1.get(0);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity First1 = FirstPair1.getKey();
						if (((int) Val1) >= ((int) First1.Value)) {

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);

								if ((int) Val1 >= (int) Entity2.Value) {
									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
								} else
									return Iterator;
							}

						} else
							return Iterator;

					}

				} else
					return Iterator;

			}
		}
		return Iterator;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> GreaterThanInt(Object Val1,
			int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity First = FirstPair.getKey();
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					First = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}
				if (((int) Val1) > ((int) First.Value)) {

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;

					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1.get(0);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity First1 = FirstPair1.getKey();
						if (((int) Val1) > ((int) First1.Value)) {

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);

								if ((int) Val1 > (int) Entity2.Value) {
									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
								} else
									return Iterator;
							}

						} else
							return Iterator;

					}

				} else
					return Iterator;

			}
		}

		return Iterator;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> LessThanInt(Object Val1,
			int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();

		boolean secondNull = false;
		boolean secondNull1 = false;
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity Second = FirstPair.getValue();
				if (Second == null)
					secondNull = true;
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					Second = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}
				if (secondNull || ((int) Val1) < ((int) Second.Value)) {
					secondNull = false;

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;
					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1
							.get(0);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity Second1 = FirstPair1.getValue();
						if (Second1 == null)
							secondNull1 = true;
						if (secondNull1 || ((int) Val1) < ((int) Second1.Value)) {
							secondNull1 = false;

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							boolean finish = false;

							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);
								if ((int) Val1 < (int) Entity2.Value) {

									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
									finish = true;
								}
							}
							if (finish)
								return Iterator;
						}

					}

				}

			}
		}
		return Iterator;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> LessThanOrEqualInt(Object Val1,
			int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		boolean secondNull = false;
		boolean secondNull1 = false;
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity Second = FirstPair.getValue();
				
				if (Second == null)
					secondNull = true;
				
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					Second = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}
				if (secondNull || ((int) Val1) <= ((int) Second.Value)) {
					secondNull = false;

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;
					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1
							.get(0);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity Second1 = FirstPair1.getValue();
						if (Second1 == null)
							secondNull1 = true;
						if (secondNull1
								|| ((int) Val1) <= ((int) Second1.Value)) {
							secondNull1 = false;

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							boolean finish = false;

							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);
								if ((int) Val1 <= (int) Entity2.Value) {

									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
									finish = true;
								}
							}
							if (finish)
								return Iterator;
						}

					}

				}

			}
		}
		return Iterator;
	}

	// ////////////////////////////////////////////////////////////String
	// Singleton search
	// operations//////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> GreaterThanOrEqualString(
			Object Val1, int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity First = FirstPair.getKey();
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					First = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}
				if (((String) Val1).compareTo((String) First.Value) >= 0) {

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;

					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1.get(0);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity First1 = FirstPair1.getKey();
						if (((String) Val1).compareTo((String) First1.Value) >= 0) {

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);

								if (((String) Val1)
										.compareTo((String) Entity2.Value) >= 0) {
									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
								} else
									return Iterator;
							}

						} else
							return Iterator;

					}

				} else
					return Iterator;

			}
		}
		return Iterator;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> GreaterThanString(Object Val1,
			int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity First = FirstPair.getKey();
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					First = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}
				if (((String) Val1).compareTo((String) First.Value) > 0) {

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;

					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1.get(0);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity First1 = FirstPair1.getKey();
						if (((String) Val1).compareTo((String) First1.Value) > 0) {

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);

								if (((String) Val1)
										.compareTo((String) Entity2.Value) > 0) {
									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
								} else
									return Iterator;
							}

						} else
							return Iterator;

					}

				} else
					return Iterator;

			}
		}

		return Iterator;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> LessThanString(Object Val1,
			int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();

		boolean secondNull = false;
		boolean secondNull1 = false;
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity Second = FirstPair.getValue();
				
				if (Second == null)
					secondNull = true;
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					Second = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}
				if (secondNull
						|| ((String) Val1).compareTo((String) Second.Value) < 0) {
					secondNull = false;

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;
					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1
							.get(Entity.PageNumber);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity Second1 = FirstPair1.getValue();
						if (Second1 == null)
							secondNull1 = true;
						if (secondNull1
								|| ((String) Val1)
										.compareTo((String) Second1.Value) < 0) {
							secondNull1 = false;

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							boolean finish = false;

							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);
								if (((String) Val1)
										.compareTo((String) Entity2.Value) < 0) {

									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
									finish = true;
								}
							}
							if (finish)
								return Iterator;
						}

					}

				}

			}
		}
		return Iterator;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Hashtable<String, Object>> LessThanOrEqualString(
			Object Val1, int Brin) {
		ArrayList<Hashtable<String, Object>> Iterator = new ArrayList<Hashtable<String, Object>>();
		boolean secondNull = false;
		boolean secondNull1 = false;
		BrinIndex BrinIndex = this.BrinIndecies.get(Brin);
		ArrayList<LinkedList<Entity>> BrinPages = BrinIndex.BrinPages;
		for (int j = 0; j < BrinPages.size(); j++) {

			LinkedList<Entity> SecondBrinIndexTuples = BrinPages.get(j);
			for (int k = 0; k < SecondBrinIndexTuples.size(); k++) {
				Entity Entity = SecondBrinIndexTuples.get(k);
				Pair<Entity, Entity> FirstPair = (Pair<Entity, Entity>) Entity.Value;
				Entity Second = FirstPair.getValue();
				if (Second == null) {
					secondNull = true;
				}
				if(BrinIndex.FirstBrin.Dense.Densetuples.size()<201){
					Second = ((Pair<Entity, Entity>) (FirstPair.getKey().Value)).getKey();
				}

				if (secondNull
						|| ((String) Val1).compareTo((String) Second.Value) <= 0) {
					secondNull = false;

					BrinFirst BrinFirst = BrinIndex.FirstBrin;
					ArrayList<LinkedList<Entity>> BrinPages1 = BrinFirst.BrinPages;
					LinkedList<Entity> FirstBrinIndexTuples = BrinPages1.get(0);
					for (int r = 0; r < FirstBrinIndexTuples.size(); r++) {

						Entity Entity1 = FirstBrinIndexTuples.get(r);
						Pair<Entity, Entity> FirstPair1 = (Pair<Entity, Entity>) Entity1.Value;
						Entity Second1 = FirstPair1.getValue();
						if (Second1 == null)
							secondNull1 = true;
						if (secondNull1
								|| ((String) Val1)
										.compareTo((String) Second1.Value) <= 0) {
							secondNull1 = false;

							DenseIndex DenseIndex = BrinFirst.Dense;
							LinkedList<Entity> Densetuples = DenseIndex.Densetuples;
							boolean finish = false;

							for (int s = Entity1.PageNumber; s < Densetuples
									.size(); s++) {

								Entity Entity2 = Densetuples.get(s);
								if (((String) Val1)
										.compareTo((String) Entity2.Value) <= 0) {

									Iterator.add(Pages.get(Entity2.PageNumber).tuples
											.get(Entity2.Elementnumber));
									finish = true;
								}
							}
							if (finish)
								return Iterator;
						}

					}

				}

			}
		}
		return Iterator;
	}

	public void DeleteFromTable(Hashtable<String, Object> htblColNameVale)
			throws DBAppException {

		boolean IsString = false;

		if (htblColNameVale.containsKey(strClusteringKeyColumn)) {
			Object key = htblColNameVale.get(strClusteringKeyColumn);
			if (key instanceof String) {

				// System.out.println("Here"); //So the bug isn't cased by this
				// if
				String primary = (String) key;
				// System.out.println(key);
				/*
				 * Long parseInt = (long) 0; try { String clusterKeyPrimary =
				 * "0"; byte[] infoBin = primary.getBytes("UTF-8"); for(int i =
				 * infoBin.length-1;i>0;i--){
				 * clusterKeyPrimary=infoBin[i]+""+clusterKeyPrimary; }
				 * parseInt= Long.parseLong(clusterKeyPrimary);
				 */

				for (int i = 0; i < Pages.size(); i++) { // /this is disgusting
															// .. im ashamed of
															// u romy
					Page p = Pages.get(i);
					// System.out.println(i + this.strTableName);
					LinkedList<Hashtable<String, Object>> tuples = p.tuples;
					if (!tuples.isEmpty()) {
						Hashtable<String, Object> first = tuples.getFirst();
						Hashtable<String, Object> Last = tuples.getLast();
						String firstValue = (String) first
								.get(strClusteringKeyColumn);
						String SecondValue = (String) Last
								.get(strClusteringKeyColumn);
						int Upper = primary.compareTo(firstValue);

						int Lower = primary.compareTo(SecondValue);
						// System.out.println(Upper);
						// System.out.println(Lower);

						/*
						 * String FirstKey= "0"; String SecondKey="0"; byte[]
						 * ByteFirst = firstValue.getBytes("UTF-8"); byte[]
						 * ByteSecond = SecondValue.getBytes("UTF-8"); for(int j
						 * = ByteFirst.length-1;j>0;j--){
						 * FirstKey=ByteFirst[i]+""+FirstKey;}
						 * 
						 * for(int j = ByteSecond.length-1;j>0;j--){
						 * SecondKey=ByteSecond[j]+""+SecondKey;
						 * 
						 * } long firstParsed=Long.parseLong(FirstKey); long
						 * secondParsed=Long.parseLong(SecondKey);
						 */
						// / this check is for when the value exists between the
						// first two values of two pages
						// indicating that the value must be inserted in the
						// first page of
						if ((Upper > 0 && Lower < 0) || (Upper > 0)
								|| (Lower < 0)) { // ////Fixed the exception
							// System.out.println("Entered to delete from string");
							Pages.get(i).DeleteFromPage(htblColNameVale,
									primary, -1, !IsString);

							// p.loadPage(i,this.strTableName);

							updatePagesD(i, !IsString);
							;
							// System.out.println("1");

							LoadAll();

						}

					}

					else {
						Pages.get(i).DeleteFromPage(htblColNameVale, primary,
								-1, !IsString); // Increased attributes
						// p.loadPage(i,this.strTableName);

						updatePagesD(i, !IsString);
						;
						System.out.println("2");

						LoadAll();

					}

				}
			}
			//

			else {
				// case numerical

				int key1 = (int) htblColNameVale.get(strClusteringKeyColumn);

				for (int i = 0; i < Pages.size(); i++) { // /this is disgusting
															// .. im ashamed of
															// u romy
					Page p = Pages.get(i);
					LinkedList<Hashtable<String, Object>> tuples = p.tuples;

					if (!tuples.isEmpty()) {
						// System.out.println(tuples.size());
						// System.out.println(tuples.get(0));

						// System.out.println(key1);
						Hashtable<String, Object> first = tuples.getFirst();

						// System.out.println(tuples.getFirst());
						Hashtable<String, Object> Last = Pages.get(i).tuples
								.getLast();
						int firstValue = (int) first
								.get(strClusteringKeyColumn);
						// System.out.println(firstValue+"first"); //it prints
						// our value
						int SecondValue = (int) Last
								.get(strClusteringKeyColumn);
						// System.out.println(SecondValue+"last");

						/*
						 * if(key1>firstValue){
						 * //p.insertIntoPage(htblColNameVale, key1,IsString);
						 * tuples.addLast(htblColNameVale);
						 * 
						 * }
						 * 
						 * else if(key1<SecondValue){
						 * 
						 * tuples.addFirst(htblColNameVale); }
						 */

						if ((key1 > firstValue && key1 < SecondValue)
								|| (key1 < SecondValue) || (key1 > firstValue)) { // missing
																					// case

							p.DeleteFromPage(htblColNameVale, "", key1,
									IsString);

							// p.loadPage(i,this.strTableName);
							if (!p.check()) {
								updatePagesD(i, IsString);
								;
								System.out.println("3");

							}
							LoadAll();

						}

					}

					else {
						p.DeleteFromPage(htblColNameVale, "", key1, IsString);

						if (!p.check()) {
							updatePagesD(i, IsString);
							;
							System.out.println("4");

						}
						LoadAll();

					}
				}

			}

		}

		else {
			throw new DBAppException("please add the clustering key");// TO-DO
																		// message
		}
		UpdateBrins();
	}

	public void UpdateBrins() {
		ArrayList<String> KeyNames = new ArrayList<String>();
		if (this.BrinIndecies != null) {
			// System.out.println(BrinIndecies.size()+"Here");
			// System.out.println("Updating brins");
			for (int i = 0; i < this.BrinIndecies.size(); i++) {
				KeyNames.add(this.BrinIndecies.get(i).ColumnName);
				// System.out.println(this.BrinIndecies.get(i).ColumnName);

			}
			this.BrinIndecies = new ArrayList<BrinIndex>();
			for (int i = 0; i < KeyNames.size(); i++) {
				try {
					CreateBrinIndex(KeyNames.get(i));
					// System.out.println(this.BrinIndecies.size());
				} catch (DBAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void updatePagesD(int startingPage, boolean flag) { // Update On
																// delete

		if (Pages.get(Pages.size() - 1).IsEmpty()) {

			Pages.get(Pages.size() - 1).file.delete();

			Pages.remove(Pages.get(Pages.size() - 1));

		}

		for (int i = startingPage; i < Pages.size() - 1; i++) {
			// System.out.println(Pages.get(i).tuples.size()+
			// "Current tuple size");
			if (!(Pages.get(i).tuples.size() == 3))
				try {
					Set<Entry<String, Object>> FirstTuple = Pages.get(i + 1).tuples
							.getFirst().entrySet();

					Iterator<Entry<String, Object>> Iterator = FirstTuple
							.iterator();
					Object HashValue = null;

					while (Iterator.hasNext()) {
						Entry<String, Object> en = Iterator.next();

						if (strClusteringKeyColumn.equals((String) en.getKey())) {
							// System.out.println((String)en.getKey());
							HashValue = en.getValue();// / got the value of the
														// primary key of the
														// wanted to insert
														// value
							// System.out.println(HashValue);

						}
					}
					if (flag) {
						// System.out.println("Here to update");
						Hashtable<String, Object> hash = Pages.get(i + 1).tuples
								.getFirst();
						Pages.get(i + 1).tuples.removeFirst();

						Pages.get(i).insertIntoPage(hash, (String) HashValue,
								-1, flag);

					} else {
						// System.out.println("Here to update");
						Hashtable<String, Object> hash = Pages.get(i + 1).tuples
								.getFirst();
						Pages.get(i + 1).tuples.removeFirst();
						Pages.get(i).insertIntoPage(hash, "", (int) HashValue,
								flag);

					}

					// Pages.get(i).currentLine--;
					// Pages.get(i+1).currentLine++;

				} catch (DBAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		/*
		 * for(int i = 0;i<Pages.size();i++){
		 * System.out.println(Pages.get(i).tuples.size()+"::://*");
		 * //System.out.println(Pages.get(i).currentLine); }
		 */

	}

	public void UpdateMeta() {

		try {
			FileWriter writer = new FileWriter(this.strTableName
					+ "metadata.csv");

			Set<Entry<String, String>> FirstTuple = htblColNameType.entrySet();

			Iterator<Entry<String, String>> Iterator = FirstTuple.iterator();
			writer.append("Table name" + ",");
			writer.append("Column Name" + ",");
			writer.append("Column Type" + ",");
			writer.append("Key?" + ",");
			writer.append("Indexed?" + ",");
			writer.append("\n");
			int brincount = 0;
			while (Iterator.hasNext()) {
				Entry<String, String> en = Iterator.next();
				writer.append(strTableName + ",");
				writer.append(en.getKey() + ",");
				writer.append(en.getValue() + ",");
				if (strClusteringKeyColumn.equals(en.getKey())) {

					writer.append("True" + ",");
				} else {
					writer.append("False" + ",");
				}
				boolean indexed = false;
				if (brincount < BrinIndecies.size()) {
					for (int i = 0; i < BrinIndecies.size(); i++) {
						BrinIndex I = BrinIndecies.get(i);

						if (I.ColumnName.equals(en.getKey())) {

							writer.append("True" + ",");
							brincount++;
							indexed = true;
						}
					}

				} else if (!indexed) {

					writer.append("False" + ",");

				}
				indexed = false;
				writer.append("\n");

			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
