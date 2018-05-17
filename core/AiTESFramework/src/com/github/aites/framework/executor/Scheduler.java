package com.github.aites.framework.executor;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import com.github.aites.framework.log.LogWritter;


import com.github.aites.framework.planner.Plan;


/**
 * Class for manage feedback loop schedule.
 * input plan, and execute.
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class Scheduler{
	static ArrayList<Plan> planList = new ArrayList<Plan>();
	
	Effector effector;
	LogWritter log = LogWritter.getInstance();
    private String moduleFolder;
    private File currentFile;
    private Class invokeClass;
    
    private String currentPubTopic;
    private String currentPubModuleName;
    
    private static class SchedulerSingleton{
		private static final Scheduler instance = new Scheduler();
	}
	
	public static Scheduler getInstance(){
		return SchedulerSingleton.instance;
	}
    public void setModuleFolder(String mudleFolder){
    	this.moduleFolder = moduleFolder;
    }
    public void setExecutuInfo(Class invokeClass, String pubTopic, String moduleFolder){
    	this.invokeClass = invokeClass;
    	
    	effector = new Effector(moduleFolder, pubTopic);
    }
    /**
	 * Method for execute schedule
	 * plan loaded global knowledge
	 * @param  plan
	 * @return none
	 */
	public void inputPlan(Plan plan){		
		planList.add(plan);
	}
	/**
	 * Method for execute schedule
	 * plan execute every time step.
	 * @param systemTime
	 * @return none
	 */
	public void execute(String systemTime){

		log.logInput("**********execute scheduled plan***************");
		
		for(Iterator<Plan> it = planList.iterator() ; it.hasNext() ; )
		{
			Plan p  = it.next();
			
			if(p.getPlanTime().equals(systemTime))
			{
				log.logInput("Execute plan Target:"+p.getTarget()+" and action:"+p.getAction());
				//effector.effectIoTgateway(p.getTarget(), p.getAction());
				
				currentPubTopic = "TestEffector";
				currentPubModuleName = "TestPubTopic";
				invokeExecuteResult();
				it.remove();
			}
		}

	}/**
	 * Method for print all plan information
	 * still this time step
	 * @param systemTime
	 * @return none
	 */
	public void printAllPlan(){
		int i=0;
		log.logInput("*****Print current remain plan*****");
		for(Plan p : planList){
			log.logInput("index:"+i+",plantime:"+p.getPlanTime()+",planTarget:"+p.getTarget()+",planAction:"+p.getAction());
			i++;
		}
	}
	public void invokeExecuteResult(){
		
	    Class[] methodParamClass = new Class[] {String.class, String.class};	 	 
	    
	    try{
		    Method method = invokeClass.getMethod("writeExecuteResult",methodParamClass);     
		    Object clsInstance = invokeClass.newInstance();
			method.invoke(clsInstance, currentPubTopic, currentPubModuleName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
