/*import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.util.Pair;

public class BrinFirst {
	String ColumnName;

	//LinkedList<Entity> FirstBrintuples ; // entity of Pair object , Dense page number
	ArrayList<LinkedList<Entity>> BrinPages;
	
	DenseTable Dense;
	int currentLine;
	public BrinFirst(DenseTable Dense){
		this.Dense=Dense;
		this.ColumnName=Dense.ColumnName;
		//this.FirstBrintuples = new LinkedList<Entity>();
		this.BrinPages=new ArrayList<LinkedList<Entity>>();
		CreateFirstBrin();
		
	}
	public void CreateFirstBrin(){
		for(int i=0;i<Dense.denses.size()-1;i++){ //remember to return it to size
		  InsertIntoArray();
			DenseIndex current=this.Dense.denses.get(i);
			System.out.println(current);
			Entity First=(Entity)current.Densetuples.getFirst();
			Entity Last=(Entity)current.Densetuples.getLast();
			Pair<Entity,Entity> Entities=new Pair<Entity,Entity> (First,Last);
			Entity toInsert=new Entity(Entities,i,-1);
			this.BrinPages.get(this.BrinPages.size()-1).add(toInsert);
			 
			 currentLine++;
			
			
			 
		}
		LoadAll();
	}
	public void InsertIntoArray(){
		if(this.BrinPages.isEmpty() || this.check()){
			 LinkedList<Entity> FirstBrintuples = new LinkedList<Entity>();
			 this.BrinPages.add( FirstBrintuples);
			this.currentLine=0;
			
		}
	}
	public boolean check(){
	       return this.currentLine>10;
	    }
	public void loadFirst(int Index, String columnName) {

		try {
			FileWriter writer = new FileWriter(columnName + Index +"FirstBrin"+ ".csv");
        
			for (int i = 0; i < this.BrinPages.get(Index).size(); i++) {

				writer.append(this.BrinPages.get(Index).get(i).Value+ " ");
				writer.append("\n");

				// System.out.println(Densetuples.get(i).Value);
				// generate whatever data you want

			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void LoadAll(){
		for(int i=0;i<this.BrinPages.size();i++){
			//System.out.println("Entered to load");
			loadFirst(i, ColumnName);
		}
	}

}
*/