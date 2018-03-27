package com.github.aitest.executor;

import java.util.ArrayList;
import org.quartz.*;

import com.github.aites.gkconnect.ExecutorActionWriter;
import com.github.aites.log.LogWritter;
import com.github.aites.palnner.Plan;

import Communicate.DataTransfer;
import LocalPropertyConnect.DBConnector;

public class Scheduler{
	static ArrayList<Plan> planList = new ArrayList<Plan>();
	
	Effector effector = new Effector();
	LogWritter log = LogWritter.getInstance();
	public void inputPlan(Plan plan){
		log.logInput("Input plan to scheduler");
		planList.add(plan);
	}
	public void execute(String systemTime){

		log.logInput("**********execute scheduled plan***************");
		
		for(int i=0; i<planList.size(); i++){
			Plan p = planList.get(i);
			
			if(p.getPlanTime().equals(systemTime)){
				log.logInput("Execute plannTarget:"+p.getTarget()+"and action:"+p.getAction());
				String moduleName = effector.findModuleAboutAction(p.getAction());
			
				String moduleTopic = effector.getTopic();
				
				DBConnector dc = new ExecutorActionWriter(systemTime,moduleTopic,moduleName);
				dc.dbConnect();
			
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
	
		
		
	
}