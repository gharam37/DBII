import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class DenseIndex {

	LinkedList<Entity> Densetuples = new LinkedList<Entity>(); // Gonna be
																// create in
																// page 200
																// elements also
	// of entities
	File file;
	String ColumnName; //The Column we   cluster with 
	Table table;
	
	
	Hashtable<String,String> htblColNameType;
	public DenseIndex(Table table,String ColumnName){
		this.table=table;
		this.ColumnName=ColumnName;
		
		Initialize();
		

	}
	
	public void Initialize(){
		ArrayList<Page> Pages=this.table.Pages;
		LinkedList<Hashtable<String,Object>> Pagetuples;
		Page p;
		for(int i=0;i<Pages.size();i++){
			p=Pages.get(i);
			Pagetuples =p.tuples;
		   
			//System.out.println(Dense);
			for(int j=0;j<Pagetuples.size();j++){
				if(Pagetuples.get(j).containsKey(ColumnName)){
					Object key= Pagetuples.get(j).get(ColumnName); //Get Value we want to Cluster On
					Entity Entity=new Entity(key,i,j);
				   if(key instanceof String){
					   InsertIntoDense(Entity,true);
					   
				   }
				   else{
					   
					   InsertIntoDense(Entity,false);
				   }
				 
					
					
					
					
				}
				   
					
			
				
				
			
			
			
		}}
	}

	public void InsertIntoDense(Entity New, boolean isString) {
		Entity Entity = null;

		if (isString) {
			String Value = (String) New.Value;
			if (!Densetuples.isEmpty()) {
				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);

					Object CurrentKey = Entity.Value;
					String Current = (String) CurrentKey;

					if (Current.compareTo(Value) > 0 && i == 0) {

						Densetuples.addFirst(New);
						

						break;
					}

					if (Current.compareTo(Value) >= 0) { // Changed to Or equal
						Densetuples.add(i, New);

						break;
					}
					if ((Current.compareTo(Value) < 0)
							&& i == Densetuples.size() - 1) {

						Densetuples.addLast(New);

						break;
					}

					/*
					 * else if(HashCurrentValue.compareTo(KeyValue)==0){ throw
					 * new DBAppException(); ///print already exists
					 * 
					 * }
					 */

				}

			} else {
				Densetuples.add(0, New);

			}

		}

		else {
			// /case int
			int Value = (int) New.Value;

			if (!Densetuples.isEmpty()) {
				// .out.println("Here");
				// .out.println(htblColNameVale); //What empties tuples ?

				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);
					int currentValue = (int) Entity.Value;
					if ((currentValue >= Value) && i == 0) {
						Densetuples.addFirst(New);

						break;
					} else if (currentValue <= Value && i == Densetuples.size() - 1) {

						Densetuples.addLast(New);
						break;
					}
					else if (currentValue >= Value) {
						Densetuples.add(i, New);
						break;

					}

				}
			}

			else {
				Densetuples.add(0, New);
			}
		}
		loadDense();
	}
  
	public void UpdateDense(Entity New, boolean isString) throws DBAppException {
		Entity Entity = null;

		if (isString) { // ///////////Remember to add currentLine
			String Value = (String) New.Value;
			if (!Densetuples.isEmpty()) {
				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);

					Object CurrentKey = Entity.Value;
					int pageNumber = Entity.PageNumber;
					int Elementnumber = Entity.Elementnumber;
					String Current = (String) CurrentKey;

					if (Current.compareTo(Value) == 0 && i == 0
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {
						Densetuples.removeFirst();
						Densetuples.addFirst(New);

						break;
					}

					if (Current.compareTo(Value) == 0
							&& i == Densetuples.size() - 1
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) { // Changed
																		// to Or
																		// equal
						Densetuples.removeLast();
						Densetuples.addLast(New);

						break;
					}
					if ((Current.compareTo(Value) == 0)
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {

						Densetuples.remove(i);
						Densetuples.add(i, New);

						break;
					}

					/*
					 * else if(HashCurrentValue.compareTo(KeyValue)==0){ throw
					 * new DBAppException(); ///print already exists
					 * 
					 * }
					 */

				}

			} else {
				throw new DBAppException("sorry this table is empty insert first");
			}

		}

		else {
			// /case int
			int Value = (int) New.Value;

			if (!Densetuples.isEmpty()) {
				

				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);
					int currentValue = (int) Entity.Value;
					int pageNumber = Entity.PageNumber;
					int Elementnumber = Entity.Elementnumber;
					if ((currentValue > Value) && i == Densetuples.size() - 1
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {
						Densetuples.removeLast();
						Densetuples.addLast(New);

						break;
					} else if (currentValue < Value && i == 0
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {

						Densetuples.removeFirst();
						Densetuples.addFirst(New);

						break;
					}
					if (currentValue <= Value && pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {
						Densetuples.remove(i);
						Densetuples.add(i, New);
						break;

					}

				}
			}

			else {
				throw new DBAppException("sorry this table is empty insert first");
			}
		}

	}

	public void DeleteFromDense(Entity New, boolean isString)
			throws DBAppException {
		Entity Entity = null;

		if (isString) { // ///////////Remember to add currentLine
			String Value = (String) New.Value;
			if (!Densetuples.isEmpty()) {
				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);

					Object CurrentKey = Entity.Value;
					int pageNumber = Entity.PageNumber;
					int Elementnumber = Entity.Elementnumber;
					String Current = (String) CurrentKey;

					if (Current.compareTo(Value) == 0 && i == 0
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {
						Densetuples.removeFirst();

						break;
					}

					if (Current.compareTo(Value) == 0
							&& i == Densetuples.size() - 1
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) { // Changed
																		// to Or
																		// equal
						Densetuples.removeLast();

						break;
					}
					if ((Current.compareTo(Value) == 0)
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {

						Densetuples.remove(i);

						break;
					}

					/*
					 * else if(HashCurrentValue.compareTo(KeyValue)==0){ throw
					 * new DBAppException(); ///print already exists
					 * 
					 * }
					 */

				}

			} else {
				throw new DBAppException("sorry this table is empty insert first");
			}

		}

		else {
			// /case int
			int Value = (int) New.Value;

			if (!Densetuples.isEmpty()) {
				// .out.println("Here");
				// .out.println(htblColNameVale); //What empties tuples ?

				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);
					int currentValue = (int) Entity.Value;
					int pageNumber = Entity.PageNumber;
					int Elementnumber = Entity.Elementnumber;
					if ((currentValue > Value) && i == Densetuples.size() - 1
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {
												Densetuples.removeLast();

						break;
					} else if (currentValue < Value && i == 0
							&& pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {

						Densetuples.removeFirst();						break;
					}
					if (currentValue <= Value && pageNumber == New.PageNumber
							&& Elementnumber == New.Elementnumber) {
						Densetuples.remove(i);
						break;

					}

				}
			}

			else {
				throw new DBAppException("sorry this table is empty insert first");
			}
		}

	}

	public void loadDense() {

		try {
			FileWriter writer = new FileWriter(this.ColumnName  + ".csv");

			for (int i = 0; i < Densetuples.size(); i++) {

				writer.append(Densetuples.get(i).Value + " ");
				writer.append("\n");

				// .out.println(Densetuples.get(i).Value);
				// generate whatever data you want

			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
