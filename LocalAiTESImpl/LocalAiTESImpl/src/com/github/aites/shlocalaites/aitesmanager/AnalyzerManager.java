package com.github.aites.shlocalaites.aitesmanager;

import java.util.ArrayList;

import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.shlocalaites.analyze.PositionStateReasoner;
import com.github.aites.shlocalaites.analyze.SHStateCombiner;
import com.github.aites.shlocalaites.analyze.TemperatureStateReasoner;
import com.github.aites.shlocalaites.gkconnect.AnalyzerStateSetWriter;





public class AnalyzerManager extends Manager{
	private ArrayList<String> monitorInfo;
	private String clientID;

	LogWritter log = LogWritter.getInstance();
	PositionStateReasoner pr = new PositionStateReasoner();
	TemperatureStateReasoner tr = new TemperatureStateReasoner();
	SHStateCombiner sc; 
	public AnalyzerManager(ArrayList<String> monitorInfo, String clientID){
		this.monitorInfo = monitorInfo;
		
		this.clientID = clientID;
	}
	@Override
	public void run() {
		log.logInput("---------------Analyze IoT Environment when Problem Situation ocuured---------------"); // 0:collectDate, 1:mresult, 2:position, 3:temperture
		log.logInput("collectDate"+monitorInfo.get(0));
		log.logInput("psResult:"+monitorInfo.get(1));
		log.logInput("position"+monitorInfo.get(2));
		log.logInput("temperture"+monitorInfo.get(3));
		log.logInput("clientID:"+clientID);
		
		
		String position = monitorInfo.get(2);
		String temperture = monitorInfo.get(3);
	
		String positionState = pr.stateResoning(position,monitorInfo.get(0));
		String tempertureState = tr.stateResoning(temperture,monitorInfo.get(0));
	
		sc = new SHStateCombiner(tempertureState, positionState, monitorInfo.get(1));
		String stateSet = sc.combinestateSetReasoing();
		String needPlanResult = sc.getNeedPlan();

		log.logInput("Analyze Result:"+stateSet+", needPlan?:"+needPlanResult);
		DBConnector dc = new AnalyzerStateSetWriter(monitorInfo.get(0),monitorInfo.get(1),tempertureState,positionState,stateSet,needPlanResult);
		dc.dbConnect();
		log.logInput("------------------------------------------------------------------------------------");
	}

}
