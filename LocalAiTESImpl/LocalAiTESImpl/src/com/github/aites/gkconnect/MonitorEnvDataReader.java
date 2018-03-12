package com.github.aites.gkconnect;

import java.sql.SQLException;

import LocalPropertyConnect.DBConnector;

public class MonitorEnvDataReader extends DBConnector{
	private String collectDate;
	private String clientName;
	public MonitorEnvDataReader(String collectDate, String clientName){
		this.collectDate = collectDate;
		this.clientName = clientName;
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		double dataSum = 0;
		double count =0 ;
		rs = ps.executeQuery();
		
		 while(rs.next()){			   			    	 
	          String device_data = rs.getString("devicedata");
	          double devicedata = Double.parseDouble(device_data);
	          dataSum += devicedata;
	          count +=1;
		 }	
	}

	@Override
	public String setQuery() {
		String query = "SELECT collectdate,clientname,devicename,devicedata from localmonitering where clientName = '"+clientName+"' and collectDate= '"+collectDate+"'";
		return query;
	}

}
