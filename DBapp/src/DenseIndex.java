import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;

public class DenseIndex {
	String ColumnName;

	LinkedList<Entity> Densetuples = new LinkedList<Entity>(); // Gonna be
																// create in
																// page 200
																// elements also
	// of entities
	File file;
	public DenseIndex(String ColumnName) {

		this.ColumnName = ColumnName;

		// this.Densetuples = new LinkedList<Entity>();

		// Tuples will contain entities

	}

	public void InsertIntoDense(Entity New, boolean isString) {
		Entity Entity = null;

		if (isString) { // ///////////Remember to add currentLine
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
					if ((currentValue > Value) && i == Densetuples.size() - 1) {
						Densetuples.addFirst(New);

						break;
					} else if (currentValue < Value && i == 0) {

						Densetuples.addLast(New);
						break;
					}
					if (currentValue <= Value) {
						Densetuples.add(i, New);
						break;

					}

				}
			}

			else {
				Densetuples.add(0, New);
			}
		}
       //System.out.println(this);
	}
    public boolean check(){
    	return this.Densetuples.size()>=10;
    	
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
				throw new DBAppException();
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
				throw new DBAppException();
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
				throw new DBAppException();
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
				throw new DBAppException();
			}
		}

	}

	public void loadDense(int Index, String columnName) {

		try {
			FileWriter writer = new FileWriter(columnName + Index + ".csv");

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
