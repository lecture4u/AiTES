package com.github.aites.gkconnect;

import java.sql.SQLException;
import java.util.ArrayList;

import com.github.aites.log.LogWritter;
import com.github.aites.palnner.Plan;

import LocalPropertyConnect.DBConnector;

public class PlannerPlanReader extends DBConnector{
	String systemtime;
	ArrayList<Plan> planList = new ArrayList<Plan>();
	LogWritter log = LogWritter.getInstance();
	String plantime;
	String target;
	String action;
	public PlannerPlanReader(String systemtime){
		this.systemtime = systemtime;
	}
	@Override
	public void executeSetting() throws SQLException {
		log.logInput("read paln must extcuted in system time:"+systemtime);
		rs = ps.executeQuery();
		
		while(rs.next()){			   			    	 
			plantime = rs.getString("shlocalplanner_plantime");
			target = rs.getString("shlocalplanner_target");
			action = rs.getString("shlocalplanner_action");
			System.out.println("read Plan plantime:"+plantime+",target:"+target);
			planList.add(new Plan(plantime,target,action));
	          
	          
		 }	
	}

	@Override
	public String setQuery() {
		String query = "SELECT DISTINCT shlocalplanner_plantime,shlocalplanner_target,shlocalplanner_action from shlocalplanner where shlocalplanner_plantime='"+systemtime+"'";
		return query;
	}
	public ArrayList<Plan> getPlanList(){
		return planList;
	}
}
