package com.github.aites.shlocalaites.analyze;

import java.util.ArrayList;
import java.util.Collections;

import com.github.aites.framework.analyzer.StateReasoner;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;


public class PositionStateReasoner implements StateReasoner{
	ArrayList<String> latitudeList = new ArrayList<String>();
	ArrayList<String> rongitudeList = new ArrayList<String>();
	LogWritter log = LogWritter.getInstance();
	String positionState;
	String collectDate;
	String ruleSetURL;
	Timer timer = Timer.getInstance();
	public PositionStateReasoner(String ruleSetURL){
		this.ruleSetURL = ruleSetURL;
	}
 	private void processedPosition(String position){
		String[] positionParser = position.split(":");
		String delims = "[ .,?!]+";
		String[] latitudeParser = positionParser[0].split(delims);
		String[] rongitudeParser = positionParser[1].split(delims);
		
		Collections.addAll(latitudeList, latitudeParser);
		Collections.addAll(rongitudeList, rongitudeParser);

		String feedBackInd = "SHEdata"+timer.getAbbTime();
		RuleSetManager ruleSetManager = new RuleSetManager(ruleSetURL);
		
		String otro = latitudeParser[0]+"."+latitudeParser[1];
		String otla = rongitudeParser[0]+"."+rongitudeParser[1];
		String thridro = latitudeParser[2];
		String thridla = rongitudeParser[2];
		String forthro = latitudeParser[3];
		String forthla = rongitudeParser[3];
	    ruleSetManager.assertDataProperty("otLa", feedBackInd, otla, "string");
	    ruleSetManager.assertDataProperty("otRo", feedBackInd, otro, "string");
	    ruleSetManager.assertDataProperty("thridLa", feedBackInd, thridla, "integer");
	    ruleSetManager.assertDataProperty("thridRo", feedBackInd, thridro, "integer");
	    ruleSetManager.assertDataProperty("forthLa", feedBackInd, forthla, "integer");
	    ruleSetManager.assertDataProperty("forthRo", feedBackInd, forthro, "integer");
		ruleSetManager.saveRuleSet();
		

		log.logInput("#####Reasoing rule about individual#####");
		log.logInput("otla:"+otla);
		log.logInput("otro:"+otro);
		log.logInput("thridla:"+thridla);
		log.logInput("thridro:"+thridro);
		log.logInput("forthla:"+forthla);
		log.logInput("forthro:"+forthro);
		RuleManager ruleManager = new RuleManager(ruleSetURL);
		ruleManager.loadOntology(); 
		boolean isNearHome= ruleManager.reasoningRule(feedBackInd, "PositionNearRule");
		boolean isInHome = ruleManager.reasoningRule(feedBackInd, "PositionInRule");
	
		if(isInHome){
			positionState = "inHome";
		}
		else if(isNearHome){
			positionState = "nearHome";
		}
		else{
			positionState = "outHome";
		}
		
	}
 	@Override
	public String stateResoning(String position, String collectDate){
		this.collectDate = collectDate;
		log.logInput("*****Start Position State Reasoning*****");
		processedPosition(position);
		log.logInput("resoning user position state:"+positionState);
		return positionState;
	}
}
