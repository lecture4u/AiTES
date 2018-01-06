package RuleSet;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBComponent.BringColumn;
import DBComponent.DBForeignKey;

import DBComponent.DBTable;
import LocalPropertyConnect.DBConnector;



public class DBSchemaLoader {
	private String catalog = null;
	private String schemaPattern = null;
	private String tableNamePattern = null;
	private String[] types = null;
	private DBConnector db;
	private ArrayList<DBTable> dbTableList = new ArrayList<DBTable>();
	public DBSchemaLoader( DBConnector db){
		this.db = db;
	}
	public void loadSchemaFromDB(){
		try{
		    db.dbConnect();
         
            Connection cn = db.getConnection();
         
            DatabaseMetaData databaseMetaData = cn.getMetaData();
            String primaryKey = "";
	        ResultSet myRs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
	          
	          
	        while(myRs.next()){
	              catalog = myRs.getString("TABLE_CAT");
	              schemaPattern = myRs.getString("TABLE_SCHEM");
	              tableNamePattern = myRs.getString("TABLE_NAME");	             	             	           
	              try(ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(catalog, schemaPattern,tableNamePattern)){
	                  while(primaryKeys.next()){
	       
	                  primaryKey = primaryKeys.getString("COLUMN_NAME");
	                  }
	              }	       
	              DBTable dt = new DBTable(tableNamePattern, primaryKey);
	           
	              ResultSet columsRs = databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, null);
	              String query = "select";
	              while(columsRs.next()){
	                  
	                    query = query + " "+columsRs.getString("COLUMN_NAME")+",";
	               }   
	              query = query.substring(0,query.length()-1) +" from "+tableNamePattern;	              
	             
	              db = new BringColumn(query);
	              db.dbConnect();
	      
	              dt.setPropertys(((BringColumn)db).getPropertys());      
	              dbTableList.add(dt);
	              
	              ResultSet fkRs = databaseMetaData.getExportedKeys(cn.getCatalog(), null, tableNamePattern);
	              if(fkRs!= null){
	            	  while (fkRs.next()) {
		                  String fkTableName = fkRs.getString("FKTABLE_NAME");
		                  String fkColumnName = fkRs.getString("FKCOLUMN_NAME");
		                  int fkSequence = fkRs.getInt("KEY_SEQ");
		                  DBForeignKey fk = new DBForeignKey(fkTableName,fkColumnName,fkSequence);
		                  dt.addFK(fk);
		              }
	              }
	             	              	     
	          }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<DBTable> getDBTableList(){
		return dbTableList;
	}
}
