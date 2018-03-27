package com.github.aites.framework.dbcomponent;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.github.aites.framework.globalknowledge.DBConnector;

public class BringColumn extends DBConnector{

    String colName;
    String colType;
    boolean IsNullable;
    DBTable dbTable;
    String query = "";
    ArrayList<DBProperty> propertyList;
    
    public BringColumn( String query){
        
        this.query = query;
        this.propertyList = new ArrayList<DBProperty>();
    }
    @Override
    public String setQuery() {
       
        return query;
    }

    @Override
    public void executeSetting() throws SQLException {
        rs = ps.executeQuery();
        
         ResultSetMetaData rsMetaData = rs.getMetaData();
          
          int columnCount = rsMetaData.getColumnCount();
         // System.out.println("Column count: "+columnCount+"\n");
          
          for(int column=1; column<=columnCount; column++){
          //   System.out.println("Column name: "+rsMetaData.getColumnName(column));
             colName = rsMetaData.getColumnName(column);
          //   System.out.println("Column type name: "+rsMetaData.getColumnTypeName(column));
             colType=rsMetaData.getColumnTypeName(column);
             
            
           //  System.out.println("Is Nullable: "+rsMetaData.isNullable(column));
             if(rsMetaData.isNullable(column) == 1){
                 IsNullable = true;
             }
             else{
                 IsNullable = false;
             }
             
             propertyList.add(new DBProperty(colName,colType,IsNullable));
            // System.out.println("Is Auto Increment: "+rsMetaData.isAutoIncrement(column) +"\n");
             
          }
    }
    public ResultSet getRS(){
        return rs;
    }
    public ArrayList<DBProperty> getPropertys(){
        return propertyList;
    }
}
