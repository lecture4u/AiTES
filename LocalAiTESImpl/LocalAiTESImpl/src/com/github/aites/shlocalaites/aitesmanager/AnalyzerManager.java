package com.github.aites.shlocalaites.aitesmanager;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.framework.analyzer.StateCombiner;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.shlocalaites.analyze.PositionStateReasoner;

import com.github.aites.shlocalaites.analyze.TemperatureStateReasoner;
import com.github.aites.shlocalaites.gkconnect.AnalyzerStateSetWriter;


public class AnalyzerManager extends Manager{
	private ArrayList<String> monitorInfo;
	private String clientID;
    private String ruleSetURL;
	LogWritter log = LogWritter.getInstance();
	PositionStateReasoner pr;
	TemperatureStateReasoner tr;
	
	public AnalyzerManager(ArrayList<String> monitorInfo, String clientID, String ruleSetURL){
		this.monitorInfo = monitorInfo;
		this.ruleSetURL = ruleSetURL;
		this.clientID = clientID;
		
		tr = new TemperatureStateReasoner(ruleSetURL);
		pr= new PositionStateReasoner(ruleSetURL);
	}
	@Override
	public void run() {
		log.logInput("---------------AnalyzeManager: Reasoing entire environemnt factor and create state set.---------------"); 
		log.logInput("collectDate"+monitorInfo.get(0)); // 0:collectDate, 1:mresult, 2:position, 3:temperture
		log.logInput("psResult:"+monitorInfo.get(1)); // 1:mresult
		log.logInput("position"+monitorInfo.get(2)); // 2:position
		log.logInput("temperture"+monitorInfo.get(3)); //3:temperture
		log.logInput("clientID:"+clientID);
		log.logInput("Rule set URL:"+ruleSetURL);
		
		
		String position = monitorInfo.get(2);
		String temperture = monitorInfo.get(3);
	
		String positionState = pr.stateResoning(position,monitorInfo.get(0));
		String tempertureState = tr.stateResoning(temperture,monitorInfo.get(0));
		
		ArrayList<String> testStateSet = new ArrayList<String>();
		testStateSet.add(monitorInfo.get(1));
		testStateSet.add(positionState);
		testStateSet.add(tempertureState);
		log.logInput("#####Rasoning result - Smart Home's user position:"+positionState+" and temperture:"+tempertureState+"#####");
		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		hashmap.put(1, "position");
		hashmap.put(2, "temperture");
		hashmap.put(0, "ps");
		log.logInput("*****Combine Smart Home State Set.*****");
		StateCombiner testCombiner = new StateCombiner(testStateSet,hashmap,ruleSetURL);
		String needPlanResult = testCombiner.reasoningStateSetNeedPlan();
		String stateSet = testCombiner.getStateSet();
		
	
		DBConnector dc = new AnalyzerStateSetWriter(monitorInfo.get(0),monitorInfo.get(1),tempertureState,positionState,stateSet,needPlanResult);
		log.logInput("&&&&&Write analyze process to global knowledge&&&&&");
		log.logInput("Analyze Date:"+monitorInfo.get(0));
		log.logInput("Power Consumtion State:"+monitorInfo.get(1));
		log.logInput("Temperture State:"+tempertureState);
		log.logInput("Position State:"+positionState);
		log.logInput("State Set Result:"+stateSet+", and this State set is need Planning?:"+needPlanResult);
		dc.dbConnect();
		
	}

}
