package com.github.aites.shlocalaites.gkconnect;

import java.sql.SQLException;

import LocalPropertyConnect.DBConnector;

public class AnalyzerStateSetWriter extends DBConnector{
	private String analyzeDate;
	private String pcstate;
	private String temstate;

	private String positionstate;
	private String stateset;
	
	private String pneed;
	public AnalyzerStateSetWriter(String analyeDate, String pcstate, String temstate, String positionstate, String stateset, String pneed){
		this.analyzeDate = analyeDate;
		this.pcstate = pcstate;
		this.temstate = temstate;
		this.positionstate = positionstate;
		this.stateset = stateset;
		this.pneed = pneed;
		
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		super.ps.setString(1, analyzeDate);
		super.ps.setString(2, pcstate);
		super.ps.setString(3, temstate);
		super.ps.setString(4, positionstate);
		super.ps.setString(5, stateset);
		super.ps.setString(6, pneed);
		super.ps.execute();
	}

	@Override
	public String setQuery() {
		String query = "insert into shlocalanalyzer(shlocalanalyzer_analyzedate,shlocalanalyzer_pcstate,shlocalanalyzer_temstate,shlocalanalyzer_positionstate,shlocalanalyzer_stateset,shlocalanalyzer_pneed) values(?,?,?,?,?,?)";
		return query;
	}

}
