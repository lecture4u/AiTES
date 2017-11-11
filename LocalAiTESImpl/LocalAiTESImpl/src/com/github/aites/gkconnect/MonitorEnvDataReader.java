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
		rs = ps.executeQuery();
	}

	@Override
	public String setQuery() {
		String query = "SELECT collectdate,clientname,devicename,devicedata from localmonitering where clientName = "+clientName+" and collectDate= "+collectDate+" ORDER BY id desc";
		return query;
	}

}
