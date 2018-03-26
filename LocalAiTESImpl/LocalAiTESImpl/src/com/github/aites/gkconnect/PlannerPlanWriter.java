package com.github.aites.gkconnect;

import java.sql.SQLException;

import LocalPropertyConnect.DBConnector;

public class PlannerPlanWriter extends DBConnector{
	private String planTime;
	private String target;
	private String action;
	
	public PlannerPlanWriter(String planTime, String target, String action){
		this.planTime = planTime;
		this.target = target;
		this.action = action;
	
		
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		super.ps.setString(1, planTime);
		super.ps.setString(2, target);
		super.ps.setString(3, action);
		super.ps.execute();
	}

	@Override
	public String setQuery() {
		String query = "insert into shlocalplanner(shlocalplanner_plantime,shlocalplanner_target,shlocalplanner_action) values(?,?,?)";
		return query;
	
	}

}
