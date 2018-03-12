package com.github.aites.gkconnect;

import java.sql.SQLException;

import com.github.aites.monitor.EnvData;

import LocalPropertyConnect.*;
public class MonitorEnvDataWriter extends DBConnector{
	private String date;
	private String clientName;
 
	private String envData;
	private String mresult;
	public MonitorEnvDataWriter(String date, String clientName,String envData, String mreult){
		this.date = date;
		this.clientName = clientName;

		this.envData = envData;
		this.mresult = mreult;
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		super.ps.setString(1, date);
		super.ps.setString(2, clientName);
		super.ps.setString(3, envData);
		super.ps.setString(4, mresult);
		super.ps.execute();
	}

	@Override
	public String setQuery() {
		String query = "insert into shlocalmonitor(shlocalmonitor_collectdate,shlocalmonitor_clientname,shlocalmonitor_envdata,shlocalmonitor_mresult) values(?,?,?,?)";
		return query;
	}

}
