package DBComponent;

public class DBForeignKey {
	private String fkTableName;
    private String fkColumnName;
    private int fkSequence;
    
    public DBForeignKey(String fkTableName, String fkColumnName, int fkSequence){
    	this.fkColumnName = fkColumnName;
    	this.fkTableName = fkTableName;
    	this.fkSequence =fkSequence;
    }
    
    public String getFkColumnName(){
    	return fkColumnName;
    }
    public String getFkTableName(){
    	return fkTableName;
    }
    public int getFkSequence(){
    	return fkSequence;
    }
}
