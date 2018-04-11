package com.github.aites.shlocalaites.analyze;

import com.github.aites.framework.analyzer.StateReasoner;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;



public class TemperatureStateReasoner implements StateReasoner{
	String tempertureState;
	
	LogWritter log = LogWritter.getInstance();
	@Override
	public String stateResoning(String temperture, String collectDate){
		log.logInput("*****Start Home Temperture State Reasoning*****");
		log.logInput(temperture);
		
		String feedBackInd = "SHEdata"+collectDate.replaceAll("[.: ]", "");
		log.logInput("#####Reasoing rule about individual:"+feedBackInd+" and dataProperty:"+tempertureState+"#####");
		RuleSetManager ruleSetManager = new RuleSetManager("smartHome.xml");
	    ruleSetManager.assertDataProperty("hasPS", feedBackInd, tempertureState, "double");
		ruleSetManager.saveRuleSet();
		
		RuleManager ruleManager = new RuleManager("smartHome.xml");
		ruleManager.loadOntology(); 
		boolean isHotTem= ruleManager.reasoningRule(feedBackInd, "TempertureHotRule");
		boolean isColdTem = ruleManager.reasoningRule(feedBackInd, "TempertureColdRule");
		if(isHotTem){
			tempertureState = "hot";
		}
		else if(isColdTem){
			tempertureState = "cold";
		}
		else{
			tempertureState = "warm";
		}
		
		return tempertureState;
	}
	
}
