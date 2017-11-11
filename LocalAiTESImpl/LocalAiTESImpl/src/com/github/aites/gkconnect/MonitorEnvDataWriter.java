package com.github.aites.gkconnect;

import java.sql.SQLException;

import com.github.aites.monitor.EnvData;

import LocalPropertyConnect.*;
public class MonitorEnvDataWriter extends DBConnector{
	private String date;
	private String clientName;
	private String deviceName; 
	private String deviceData;
	public MonitorEnvDataWriter(String date, String clientName, String deviceName, String deviceData){
		this.date = date;
		this.clientName = clientName;
		this.deviceName = deviceName;
		this.deviceData = deviceData;
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		super.ps.setString(1, date);
		super.ps.setString(2, clientName);
		super.ps.setString(3, deviceName);
		super.ps.setString(4, deviceData);
		super.ps.execute();
	}

	@Override
	public String setQuery() {
		String query = "insert into localmonitering(collectdate,clientname,devicename,devicedata) values(?,?,?,?)";
		return query;
	}

}
