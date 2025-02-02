import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.util.Pair;

public class BrinFirst {
	String ColumnName;

	//LinkedList<Entity> FirstBrintuples ; // entity of Pair object , Dense page number
	ArrayList<LinkedList<Entity>> BrinPages;
	
	DenseIndex Dense;
	int currentLine;
	public BrinFirst(DenseIndex Dense){
		this.Dense=Dense;
		this.ColumnName=Dense.ColumnName;
		//this.FirstBrintuples = new LinkedList<Entity>();
		this.BrinPages=new ArrayList<LinkedList<Entity>>();
		CreateFirstBrin();
		
	}
	public void CreateFirstBrin(){
		LinkedList<Entity> Densetuples =this.Dense.Densetuples;
		int i=0;
		for( i=0;i<Densetuples.size()-199;i+=200){
			Entity First=Densetuples.get(i);
			Entity Last=Densetuples.get(i+199);
			Pair<Entity,Entity> EntityPair=new Pair<Entity,Entity>(First,Last);
			Entity Value = new Entity(EntityPair,i,-1);
			InsertIntoBrinPage(Value);
			
			
			
		}
	    if(i<Densetuples.size()){
	    	Entity First=Densetuples.get(i);
			Entity Last=null;
			Pair<Entity,Entity> EntityPair=new Pair<Entity,Entity>(First,Last);
			Entity Value = new Entity(EntityPair,i,-1);
			InsertIntoBrinPage(Value);
			
	    }
	
			 
		
	
	}
	public void InsertIntoBrinPage(Entity value) {
		if(this.BrinPages.isEmpty() || this.check()){
			 LinkedList<Entity> FirstBrintuples = new LinkedList<Entity>();
			 this.BrinPages.add( FirstBrintuples);
			this.currentLine=0;
			
			
		}
		this.BrinPages.get(this.BrinPages.size()-1).addLast(value);
		currentLine++;
		LoadAll();
		//System.out.println(BrinPages.size());
	}
	
	public boolean check(){
	       return this.currentLine>14;
	    }
	public void loadFirst(int Index, String columnName) {

		try {
			FileWriter writer = new FileWriter(columnName +"First Brin"+ Index + ".csv");
        
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
			
			loadFirst(i, ColumnName);
		}
	}

}
