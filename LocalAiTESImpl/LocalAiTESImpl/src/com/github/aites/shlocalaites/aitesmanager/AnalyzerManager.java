package com.github.aites.shlocalaites.aitesmanager;

import java.util.ArrayList;

import com.github.aites.shlocalaites.analyze.PositionStateResoner;
import com.github.aites.shlocalaites.analyze.StateCombiner;
import com.github.aites.shlocalaites.analyze.TempertureStateResoner;
import com.github.aites.shlocalaites.gkconnect.AnalyzerStateSetWriter;
import com.github.aites.shlocalaites.gkconnect.MonitorEnvDataReader;
import com.github.aites.shlocalaites.gkconnect.MonitorEnvDataWriter;
import com.github.aites.shlocalaites.log.LogWritter;
import com.github.aites.shlocalaites.monitor.DataPreProcessor;

import AiTESManager.Manager;
import LocalPropertyConnect.DBConnector;

public class AnalyzerManager extends Manager{
	private ArrayList<String> monitorInfo;
	private String clientID;

	LogWritter log = LogWritter.getInstance();
	PositionStateResoner pr = new PositionStateResoner();
	TempertureStateResoner tr = new TempertureStateResoner();
	StateCombiner sc; 
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
	
		String positionState = pr.stateResoning(position);
		String tempertureState = tr.stateResoning(temperture);
	
		sc = new StateCombiner(tempertureState, positionState, monitorInfo.get(1));
		String stateSet = sc.combinestateSetReasoing();
		String needPlanResult = sc.getNeedPlan();

		log.logInput("Analyze Result:"+stateSet+", needPlan?:"+needPlanResult);
		DBConnector dc = new AnalyzerStateSetWriter(monitorInfo.get(0),monitorInfo.get(1),tempertureState,positionState,stateSet,needPlanResult);
		dc.dbConnect();
		log.logInput("------------------------------------------------------------------------------------");
	}

}
