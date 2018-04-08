package com.github.aites.shlocalaites.aitesmanager;

import java.util.ArrayList;

import com.github.aites.shlocalaites.gkconnect.AnalyzerStateSetWriter;
import com.github.aites.shlocalaites.gkconnect.PlannerPlanWriter;
import com.github.aites.shlocalaites.log.LogWritter;
import com.github.aites.shlocalaites.palnner.Plan;
import com.github.aites.shlocalaites.palnner.SHPlanManager;

import AiTESManager.Manager;
import LocalPropertyConnect.DBConnector;

public class PlannerManager extends Manager{
	String stateSet;
	String collectDate;
	LogWritter log = LogWritter.getInstance();
	
	SHPlanManager pm;
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
	
		pm = new SHPlanManager(stateSet);
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
