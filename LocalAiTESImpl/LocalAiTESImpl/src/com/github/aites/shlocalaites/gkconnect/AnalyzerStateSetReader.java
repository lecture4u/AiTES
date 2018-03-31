package com.github.aites.shlocalaites.gkconnect;

import java.sql.SQLException;

import com.github.aites.shlocalaites.log.LogWritter;

import LocalPropertyConnect.DBConnector;

public class AnalyzerStateSetReader extends DBConnector{
	String stateSet;
	String collectDate;
	String pneed;
	LogWritter log = LogWritter.getInstance();
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		log.logInput("read Analyzer stateset need plaining");
		rs = ps.executeQuery();
		
		while(rs.next()){			   			    	 
			stateSet = rs.getString("shlocalanalyzer_stateset");
			collectDate = rs.getString("shlocalanalyzer_analyzedate");
			pneed = rs.getString("shlocalanalyzer_pneed");
			log.logInput("stateSet:"+stateSet+",collectDate:"+collectDate+",pneed:"+pneed);
		 }	
	}

	@Override
	public String setQuery() {
		String query = "SELECT shlocalanalyzer_analyzedate, shlocalanalyzer_stateset, shlocalanalyzer_pneed from shlocalanalyzer order by shlocalanalyzer_id DESC limit 1  ";
		return query;
	}
	public String getStateSet(){
		return stateSet;
	}
	public String getCollectDate(){
		return collectDate;
	}
	public String getPneed(){
		return pneed;
	}
}
