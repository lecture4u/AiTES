package com.github.aites.aitesmanager;

import java.util.ArrayList;

import com.github.aites.gkconnect.AnalyzerStateSetWriter;
import com.github.aites.gkconnect.PlannerPlanWriter;
import com.github.aites.log.LogWritter;
import com.github.aites.palnner.Plan;
import com.github.aites.palnner.PlanManager;


import AiTESManager.Manager;
import LocalPropertyConnect.DBConnector;

public class PlannerManager extends Manager{
	String stateSet;
	String collectDate;
	LogWritter log = LogWritter.getInstance();
	
	PlanManager pm;
	ArrayList<Plan> planList = new ArrayList<Plan>();
	
	public PlannerManager(String stateSet, String collectDate){
		this.stateSet = stateSet;
		this.collectDate = collectDate;
	
	}
	@Override
	public void run() {
		log.logInput("---------------run Planner for Response Plan---------------");
		log.logInput("stateSet:"+stateSet);
		log.logInput("collectDate"+collectDate);
	
		pm = new PlanManager(stateSet);
		planList = pm.managePlan();
		
		
		for(int i=0; i<planList.size(); i++){
			Plan plan = planList.get(i);
			plan.printPlan();
			DBConnector dc = new PlannerPlanWriter(plan.getPlanTime(),plan.getTarget(),plan.getAction());
			dc.dbConnect();
		
		}
	
		log.logInput("------------------------------------------------------------------------------------");
		
	}

}