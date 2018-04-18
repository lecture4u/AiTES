package com.github.aites.framework.analyzer;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;

public class StateCombiner {
	private LogWritter log = LogWritter.getInstance();
	private ArrayList<String> stateList = new ArrayList<String>();
	private String needPlan = "no";
	private String stateSet = "";
	private String collectDate;
	private HashMap<Integer, String> setOrderMap = new HashMap<Integer, String>();
	public StateCombiner(ArrayList<String> stateList, String collectDate, HashMap<Integer, String> setOrderMap){
		this.setOrderMap = setOrderMap;
		this.stateList = stateList;
		this.collectDate = collectDate;
	}
	public String reasoningStateSetNeedPlan(){
		log.logInput("****Start state combine and resoning this set need plan*****");
		RuleSetManager ruleSetManager = new RuleSetManager("smartHome.xml");
		String feedBackInd = "SHEdata"+collectDate.replaceAll("[.: ]", "");
		
		for(int i=0; i<setOrderMap.size(); i++){
			ruleSetManager.assertDataProperty(setOrderMap.get(i)+"State", feedBackInd, stateList.get(i), "string");
		}
	
		ruleSetManager.saveRuleSet();
		
		RuleManager ruleManager = new RuleManager("smartHome.xml");
		ruleManager.loadOntology();
		for(int i=1; i<=3; i++){
			if( ruleManager.reasoningRule(feedBackInd, "SHneedPlanRule"+i)){
				needPlan = "yes";
			}
		}
		
		for(int i=0; i<stateList.size(); i++){
			if(i == stateList.size()-1){
				stateSet = stateSet + stateList.get(i);
			}
			else{
				stateSet = stateSet + stateList.get(i)+",";
			}
		}
		
	    return needPlan;	
	}
	public String getStateSet(){
		return stateSet;
	}
	
}
