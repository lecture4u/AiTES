package com.github.aites.gkconnect;

import java.sql.SQLException;
import java.util.ArrayList;

import com.github.aites.log.LogWritter;

import LocalPropertyConnect.DBConnector;

public class MonitorEnvDataReader extends DBConnector{
	private String collectDate;
	private String mresult;
	
	private String position;
	private String temperture;
	LogWritter log = LogWritter.getInstance();
	private ArrayList<String> monitorInfo = new ArrayList<String>();
	public MonitorEnvDataReader(){
	}
	@Override
	public void executeSetting() throws SQLException {
	
		log.logInput("Read current monitoring data");
		rs = ps.executeQuery();
		
		while(rs.next()){		
			collectDate = rs.getString("shlocalmonitor_collectdate");
			monitorInfo.add(collectDate);
			mresult = rs.getString("shlocalmonitor_mresult");
			monitorInfo.add(mresult);
			position = rs.getString("shlocalmonitor_position");
			monitorInfo.add(position);
			temperture = rs.getString("shlocalmonitor_temperture");
			monitorInfo.add(temperture);
	     
	    	log.logInput("collectDate:"+collectDate+",mreult:"+mresult+",position:"+position+",temperture:"+temperture);
	          
		 }	
	}

	@Override
	public String setQuery() {
		
		
		String query = "SELECT shlocalmonitor_collectdate,shlocalmonitor_mresult,shlocalmonitor_position, shlocalmonitor_temperture from shlocalmonitor order by shlocalmonitor_id desc limit 1";
		return query;
	}
	public ArrayList<String> getMonitorInfo(){
		return monitorInfo;
	}
}
