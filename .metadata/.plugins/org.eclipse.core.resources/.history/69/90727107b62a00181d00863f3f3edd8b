import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.util.Pair;


public class BrinIndex {
	String ColumnName;

	//LinkedList<Entity> FirstBrintuples ; // entity of Pair object , Dense page number
	ArrayList<LinkedList<Entity>> BrinPages;
	
	BrinFirst FirstBrin;
	int currentLine;
	public BrinIndex(BrinFirst FirstBrin){
		this.FirstBrin=FirstBrin;
		this.ColumnName=FirstBrin.ColumnName;
		//this.FirstBrintuples = new LinkedList<Entity>();
		this.BrinPages=new ArrayList<LinkedList<Entity>>();
		CreateBrinIndex();
		
	}
	public void CreateBrinIndex(){
		ArrayList<LinkedList<Entity>> BrinPages=this.FirstBrin.BrinPages;
		int i=0;
		
	   for(int j=0;j<BrinPages.size();j++)
	   {
		   LinkedList<Entity> FirstBrintuples=BrinPages.get(j);
		for( i=0;i<FirstBrintuples.size()-1;i+=2){
			Entity First=FirstBrintuples.get(i);
			Pair<Entity,Entity>  FirstPair=(Pair<Entity, Entity>) First.Value;
			
			Entity Last=FirstBrintuples.get(i+1);
			
			Pair<Entity,Entity>  LastPair=(Pair<Entity,Entity>) Last.Value;
			//System.out.println(LastPair);
            
			Entity FirstOfFirst=(Entity)FirstPair.getKey();
			Entity LastOfLast=(Entity)LastPair.getValue();
			Pair<Entity,Entity> EntityPair=new Pair<Entity,Entity>(FirstOfFirst, LastOfLast);
			Entity Value = new Entity(EntityPair,i,-1);
			InsertIntoBrinPage(Value);
			
			
			
		}
	   }
	    if(i<BrinPages.get(BrinPages.size()-1).size()){
	    	Entity First=BrinPages.get(BrinPages.size()-1).get(i);
			Pair<Entity,Entity>  FirstPair=(Pair<Entity, Entity>) First.Value;	
			Entity FirstOfFirst=(Entity)FirstPair.getKey();
			Pair<Entity,Entity> EntityPair=new Pair<Entity,Entity>(FirstOfFirst,null);
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
	       return this.currentLine>3;
	    }
	public void loadFirst(int Index, String columnName) {

		try {
			FileWriter writer = new FileWriter(columnName + Index +"Brin Index"+ ".csv");
        
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
