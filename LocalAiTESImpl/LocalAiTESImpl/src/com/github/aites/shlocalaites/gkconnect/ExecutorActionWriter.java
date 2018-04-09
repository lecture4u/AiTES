package com.github.aites.shlocalaites.gkconnect;

import java.sql.SQLException;

import com.github.aites.framework.globalknowledge.DBConnector;



public class ExecutorActionWriter extends DBConnector{
	String executeTime;
	String executeTopic;
	String executeModule;
	
	public ExecutorActionWriter(String executeTime, String executeTopic, String executeModule){
		this.executeTime = executeTime;
		this.executeTopic = executeTopic;
		this.executeModule = executeModule;
		
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		super.ps.setString(1, executeTime);
		super.ps.setString(2, executeTopic);
		super.ps.setString(3, executeModule);
		super.ps.execute();
	}

	@Override
	public String setQuery() {
		String query = "insert into shlocalexecutor(shlocalexecutor_executetime,shlocalexecutor_executetopic,shlocalexecutor_executemodule) values(?,?,?)";
		return query;
	}

}
