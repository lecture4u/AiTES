package com.github.aitest.shlocalaites.executor;

import java.util.ArrayList;

import com.github.aites.framework.executor.Effector;
import com.github.aites.framework.executor.Scheduler;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.planner.Plan;
import com.github.aites.shlocalaites.gkconnect.ExecutorActionWriter;



public class SHScheduler extends Scheduler{
	static ArrayList<Plan> planList = new ArrayList<Plan>();
	private String moduleName;
	private String moduleTopic;
	private String systemTime;
	Effector effector = new Effector();
	LogWritter log = LogWritter.getInstance();
	public void inputPlan(Plan plan){
		log.logInput("Input plan to scheduler");
		planList.add(plan);
	}
	public void execute(String systemTime){
		this.systemTime = systemTime;
		log.logInput("**********execute scheduled plan***************");
		
		for(int i=0; i<planList.size(); i++){
			Plan p = planList.get(i);
			
			if(p.getPlanTime().equals(systemTime)){
				log.logInput("Execute plannTarget:"+p.getTarget()+"and action:"+p.getAction());
			    moduleName = effector.findModuleAboutAction(p.getAction());
			
				moduleTopic = effector.getTopic();
				
				
			
			}
		}

	}
	public void printAllPlan(){
		int i=0;
		for(Plan p : planList){
			log.logInput("index:"+i+",plantime:"+p.getPlanTime()+",planTarget:"+p.getTarget()+",planAction:"+p.getAction());
			i++;
		}
	}
	@Override
	public void writeGlobalKnowledge() {
		// TODO Auto-generated method stub
		DBConnector dc = new ExecutorActionWriter(systemTime,moduleTopic,moduleName);
		dc.dbConnect();
	}
	
		
		
	
}
