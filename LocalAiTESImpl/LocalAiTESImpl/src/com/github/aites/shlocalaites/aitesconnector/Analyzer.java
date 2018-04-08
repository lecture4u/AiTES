package com.github.aites.shlocalaites.aitesconnector;

import java.util.ArrayList;

import com.github.aites.shlocalaites.aitesmanager.AnalyzerManager;
import com.github.aitest.shlocalaites.executor.Scheduler;

import AiTESConnector.Factory;
import AiTESManager.Manager;

public class Analyzer implements Factory{
	private ArrayList<String> monitorInfo;
	private String clientID;
	
	
	public Analyzer(ArrayList<String> monitorInfo, String clientID){
		this.monitorInfo = monitorInfo;
	
		this.clientID = clientID;
	}
	@Override
	public AnalyzerManager createManager() {
		
		return new AnalyzerManager(monitorInfo,clientID);
	}
}