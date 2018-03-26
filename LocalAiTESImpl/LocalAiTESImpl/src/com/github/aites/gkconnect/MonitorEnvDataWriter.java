package com.github.aites.gkconnect;

import java.sql.SQLException;

import com.github.aites.monitor.EnvData;

import LocalPropertyConnect.*;
public class MonitorEnvDataWriter extends DBConnector{
	private String date;
	private String clientName;
 
	private String envData;
	private String mresult;
	
	private String position;
	private String temperture;
	public MonitorEnvDataWriter(String date, String clientName,String envData, String mreult,String position, String temperture){
		this.date = date;
		this.clientName = clientName;

		this.envData = envData;
		this.mresult = mreult; 
		
		this.position = position;
		this.temperture = temperture;
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		super.ps.setString(1, date);
		super.ps.setString(2, clientName);
		super.ps.setString(3, envData);
		super.ps.setString(4, mresult);
		super.ps.setString(5, position);
		super.ps.setString(6, temperture);
		super.ps.execute();
	}

	@Override
	public String setQuery() {
		String query = "insert into shlocalmonitor(shlocalmonitor_collectdate,shlocalmonitor_clientname,shlocalmonitor_envdata,shlocalmonitor_mresult,shlocalmonitor_position,shlocalmonitor_temperture) values(?,?,?,?,?,?)";
		return query;
	}

}
