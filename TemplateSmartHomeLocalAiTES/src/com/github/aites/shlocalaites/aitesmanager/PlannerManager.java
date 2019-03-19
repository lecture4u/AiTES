package com.github.aites.shlocalaites.aitesmanager;

import java.util.ArrayList;

import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.orchestration.Participants;
import com.github.aites.framework.planner.Plan;
import com.github.aites.framework.planner.PlanManager;
import com.github.aites.shlocalaites.gkconnect.PlannerPlanWriter;




public class PlannerManager extends Manager{
	String stateSet;
	String collectDate;
	LogWritter log = LogWritter.getInstance();
	Participants participant = Participants.getInstance();
	ArrayList<Plan> planList = new ArrayList<Plan>();
	
	public PlannerManager(String stateSet, String collectDate){
		this.stateSet = stateSet;
		this.collectDate = collectDate;
	
	}
	@Override
	public void run() {
		log.logInput("---------------run Planner for organization Plan Schedule---------------");
		log.logInput("stateSet:"+stateSet);
		log.logInput("collectDate"+collectDate);
	
		PlanManager pm = new PlanManager(stateSet);
		pm.setSchedulePrefix("sh");
		pm.planing();
		ArrayList<Plan> schedule = pm.getSchedule();
		log.logInput("&&&&&Write planning process to global knowledge&&&&&");
		log.logInput("planning date:"+collectDate);
		for(int i=0; i<schedule.size(); i++){
			log.logInput("Planning for state set:"+stateSet+" planTime:"+schedule.get(i).getPlanTime()+" ,action:"+schedule.get(i).getAction()+" ,target:"+schedule.get(i).getTarget());
			DBConnector dc = new PlannerPlanWriter(schedule.get(i).getPlanTime(),schedule.get(i).getTarget(),schedule.get(i).getAction());
			dc.dbConnect();
		}
		
		
		/*
		pm = new SHPlanManager(stateSet);
		planList = pm.managePlan();
		
		
		for(int i=0; i<planList.size(); i++){
			Plan plan = planList.get(i);
			plan.printPlan();
			DBConnector dc = new PlannerPlanWriter(plan.getPlanTime(),plan.getTarget(),plan.getAction());
			dc.dbConnect();
		
		}*/
	

		
	}

}
