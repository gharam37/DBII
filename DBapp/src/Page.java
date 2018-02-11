import java.io.Serializable;

public class Page implements Serializable {
	
	//should implement serializable ///
	String No;
	String[][] tuples;
	
	int currentLine = 0;//the line to add data too
	
	public Page(String pageNo, int columnNumber) {
		this.No = pageNo;
		tuples = new String[columnNumber][200];// 200 max no of tuples
}
}
