/*
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;


public class DenseTable {
	String ColumnName; //The Column we   cluster with 
	
	
	ArrayList<DenseIndex> denses;
	Table table;
	
	
	Hashtable<String,String> htblColNameType;
	public DenseTable(Table table,String ColumnName){
		this.table=table;
		this.ColumnName=ColumnName;
		this.denses=new ArrayList<DenseIndex>();
		Initialize();
		

	}
	
		
	
	public void Initialize(){
		ArrayList<Page> Pages=this.table.Pages;
		
		for(int i=0;i<Pages.size();i++){
			Page p=Pages.get(i);
			LinkedList<Hashtable<String,Object>> Pagetuples=p.tuples;
		   
			//System.out.println(Dense);
			for(int j=0;j<Pagetuples.size();j++){
				if(Pagetuples.get(j).containsKey(ColumnName)){
					Object key= Pagetuples.get(j).get(ColumnName); //Get Value we want to Cluster On
				
					
					InsertIntoDenseTable(key,i,j);
					
					
					
				}
				   
					
			
				
				
			
			
			
		}}
	}
	
	public void LoadAll(){
		for(int i=0;i<denses.size();i++){
			
			denses.get(i).loadDense(i, ColumnName);
		}
	}
	public void InsertIntoDenseTable(Object keyValue,int PageNum,int EntityNum){
		boolean IsString=false;
		if(keyValue instanceof String){
			String primary=(String)keyValue;
		
				
				for(int i=0;i<denses.size();i++){  
					DenseIndex d=denses.get(i);
					
			
					LinkedList<Entity> Densetuples=d.Densetuples;
				 if(!Densetuples.isEmpty()){
					Entity first=Densetuples.getFirst();
					Entity Last=Densetuples.getLast();
					String firstValue= (String)first.Value;
					String SecondValue= (String)Last.Value;
					int Upper=primary.compareTo(firstValue);
					
					
					int Lower=primary.compareTo(SecondValue);
			
					
					if ((Upper >= 0 && Lower <= 0) || (Upper >= 0)
							|| (Lower <= 0)){
						Entity Entity=new Entity(keyValue, PageNum,EntityNum);
						d.InsertIntoDense(Entity,!IsString);
						
						if(d.check()){
							 updateDenseIndex(i,!IsString);
						 }						
						
						LoadAll();
						break;
					}
				}
					
				 
				
				 
				
				 else{
						Entity Entity=new Entity(keyValue, PageNum,EntityNum);

					 d.InsertIntoDense(Entity, !IsString); 
					 if(d.check()){
						 updateDenseIndex(i,!IsString);
					 }
						LoadAll();
						break;
				 }
					
				}
			}
				
		
		else{
			//case numerical
			
			
			int key1=(int)keyValue;
			
			for(int i=0;i<denses.size();i++){  ///this is disgusting .. im ashamed of u romy
				DenseIndex d=denses.get(i);
				
				LinkedList<Entity> tuples=d.Densetuples;
				Entity Entity=new Entity(keyValue, PageNum,EntityNum);
				//.out.println(key1+"Wanna insert");
				
			
			 if(!tuples.isEmpty()){
			 
				Entity first=tuples.getFirst();
				
			   
				Entity Last=tuples.getLast();
				int firstValue= (int)first.Value;
			
				int SecondValue= (int)Last.Value;
	
                
                 
              
                	
                if(((key1>=firstValue) && (key1<=SecondValue) ||(key1<=SecondValue)||(key1>=firstValue))){ //missing case
				 Entity=new Entity(keyValue, PageNum,EntityNum);
			


				 d.InsertIntoDense(Entity,IsString);
				 
				 if(d.check()){
					 updateDenseIndex(i,IsString);
				 }
				
				
					LoadAll();
					break;
				}
			
		
			

		}
			 
			 else{
			   Entity=new Entity(keyValue, PageNum,EntityNum);

				 d.InsertIntoDense(Entity,IsString);
				 
				 if(d.check()){
					 updateDenseIndex(i,IsString);
				 }
				 
				 
				 
				
				
				
				 LoadAll();
				 break;
			 }
	}
			
		
		
  }
		
	
	
	
	}
	
	public void UpdateDenseTable(Object keyValue,int PageNum,int EntityNum) throws DBAppException{
		boolean IsString=false;
		if(keyValue instanceof String){
			String primary=(String)keyValue;
		
				
				for(int i=0;i<denses.size();i++){  ///this is disgusting .. im ashamed of u romy
					DenseIndex d=denses.get(i);
					LinkedList<Entity> Densetuples=d.Densetuples;
				 if(!Densetuples.isEmpty()){
					Entity first=Densetuples.getFirst();
					Entity Last=Densetuples.getLast();
					String firstValue= (String)first.Value;
					String SecondValue= (String)Last.Value;
					int Upper=primary.compareTo(firstValue);
					
					
					int Lower=primary.compareTo(SecondValue);
			
					
					if ((Upper >= 0 && Lower <= 0) || (Upper >= 0)
							|| (Lower <= 0)){
						Entity Entity=new Entity(keyValue, PageNum,EntityNum);
						d.UpdateDense(Entity,!IsString);
						
                        
						//p.loadPage(i,this.strTableName);
						
						LoadAll();
						break;
					}
				}
					
				 
				
				 
				
				 else{
						Entity Entity=new Entity(keyValue, PageNum,EntityNum);

					 d.UpdateDense(Entity, !IsString); 
					
						LoadAll();
						break;
				 }
					
				}
			}
				
		
		else{
			//case numerical
			
			
			int key1=(int)keyValue;
			
			for(int i=0;i<denses.size();i++){  
				DenseIndex d=denses.get(i);
				LinkedList<Entity> tuples=d.Densetuples;
				Entity Entity=new Entity(keyValue, PageNum,EntityNum);
				
				
			
			 if(!tuples.isEmpty()){
			
				Entity first=tuples.getFirst();
				
			   
				Entity Last=tuples.getLast();
				int firstValue= (int)first.Value;
				
				int SecondValue= (int)Last.Value;
	
				
                
                	
              
                
                
                 
              
                	
                if(((key1>=firstValue) && (key1<=SecondValue) ||(key1<=SecondValue)||(key1>=firstValue))){ //missing case
				 Entity=new Entity(keyValue, PageNum,EntityNum);
			


				 d.UpdateDense(Entity,IsString);
				
				
				
					LoadAll();
					break;
				}
			
		
			

		}
			 
			 else{
			   Entity=new Entity(keyValue, PageNum,EntityNum);

				 d.UpdateDense(Entity,IsString);
				
				 
				 
				 
				
				
				
				 LoadAll();
				 break;
			 }
	}
			
		
		
  }
		
	
	
	
	}
	
	public void DeleteFromDenseTable(Entity Entity) throws DBAppException{
		boolean IsString=false;
		if(Entity.Value instanceof String){
			String primary=(String)Entity.Value;
		
				
				for(int i=0;i<denses.size();i++){  ///this is disgusting .. im ashamed of u romy
					DenseIndex d=denses.get(i);
					LinkedList<Entity> Densetuples=d.Densetuples;
				 if(!Densetuples.isEmpty()){
					Entity first=Densetuples.getFirst();
					Entity Last=Densetuples.getLast();
					String firstValue= (String)first.Value;
					String SecondValue= (String)Last.Value;
					int Upper=primary.compareTo(firstValue);
					
					
					int Lower=primary.compareTo(SecondValue);
			
					
					if ((Upper >= 0 && Lower <= 0) || (Upper >= 0)
							|| (Lower < 0)){
						
						d.DeleteFromDense(Entity,!IsString);
						
                        
						//p.loadPage(i,this.strTableName);
						
						LoadAll();
						break;
					}
				}
					
				 
				
				 
				
				 else{
						//Entity Entity=new Entity(keyValue, PageNum,EntityNum);

					 d.DeleteFromDense(Entity, !IsString); 
					
						LoadAll();
						break;
				 }
					
				}
			}
				
		
		else{
			//case numerical
			
			
			int key1=(int)Entity.Value;
			
			for(int i=0;i<denses.size();i++){  ///this is disgusting .. im ashamed of u romy
				DenseIndex d=denses.get(i);
				LinkedList<Entity> tuples=d.Densetuples;
		
			
			 if(!tuples.isEmpty()){
			  //.out.println(tuples.size());
			   //.out.println(tuples);
				Entity first=tuples.getFirst();
				
			   
				Entity Last=tuples.getLast();
				int firstValue= (int)first.Value;
		
				int SecondValue= (int)Last.Value;
				
				
                
                	
               
                
                
                 
              
                	
                if(((key1>=firstValue) && (key1<=SecondValue) ||(key1<=SecondValue)||(key1>=firstValue))){ //missing case
			


				 d.DeleteFromDense(Entity,IsString);
				
				
				
					LoadAll();
					break;
				}
			
		
			

		}
			 
			 else{

				 d.DeleteFromDense(Entity,IsString);
				
				 
				 
				 
				
				
				
				 LoadAll();
				 break;
			 }
	}
			
		
		
  }
		
	
	
	
	}
	public void updateDenseIndex(int startingPage, boolean flag) {

		for (int i = startingPage; i < denses.size() - 1; i++) {
			if ((denses.get(i).check())){
				

					denses.get(i + 1).InsertIntoDense(denses.get(i).Densetuples.getLast(), flag);
					denses.get(i).Densetuples.removeLast();

					
			}
				
		}
		}
	
}
*/