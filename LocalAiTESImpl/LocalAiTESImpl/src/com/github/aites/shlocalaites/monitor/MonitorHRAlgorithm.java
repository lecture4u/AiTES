package com.github.aites.shlocalaites.monitor;

import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.monitor.EnvData;
import com.github.aites.framework.monitor.HRAlgorithm;
import com.github.aites.framework.ruleset.RuleSetManager;
import com.github.aites.shlocalaites.rule.RuleManager;


public class MonitorHRAlgorithm implements HRAlgorithm{
	EnvData envData;
	
	LogWritter log = LogWritter.getInstance();
	public MonitorHRAlgorithm(EnvData envData){
		this.envData = envData;
	}
	public String getAllEnvData(){
		String envResult = "";
		for(int di=0; di<envData.getDeviceData().size(); di++){
			if(di==0){
				envResult = envResult+envData.getDeviceData().get(di);
			}
			else{
				envResult = envResult+","+envData.getDeviceData().get(di);
			}
			
		}
		
		return envResult;
	}
	@Override
	public String envDataHRAlgorithm(){
		log.logInput("*****Smart Home Environment Data Heuristic funtion*****");
	
		Double hresult =0.0;
		for(int di=0; di<envData.getDeviceData().size(); di++){
			envData.getDeviceNmae().get(di);
			hresult += Double.parseDouble(envData.getDeviceData().get(di));
			
		}
		log.logInput("Power consumtion:"+hresult);
		
		String dpValue = hresult.toString();
		String feedBackInd = "SHEdata"+envData.getCollectDate().replaceAll("[.: ]", "");
		log.logInput("#####Reasoing rule about individual:"+feedBackInd+" and dataProperty:"+dpValue+"#####");
		RuleSetManager ruleSetManager = new RuleSetManager("smartHome.xml");
	    ruleSetManager.assertDataProperty("hasPS", feedBackInd, dpValue, "double");
		ruleSetManager.saveRuleSet();
		
		RuleManager ruleManager2 = new RuleManager("smartHome.xml");
		ruleManager2.loadOntology(); 
		boolean isOverPS = ruleManager2.reasoningRule(feedBackInd, "PSoverRule");
		boolean isUnderPS = ruleManager2.reasoningRule(feedBackInd, "PSunderRule");
		if(isUnderPS){
			return "under";
		}
		else if(isOverPS){
			return "over";
		}
		return "normal";
		
 
		
	}
}



