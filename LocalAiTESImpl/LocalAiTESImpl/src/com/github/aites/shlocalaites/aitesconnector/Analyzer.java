package com.github.aites.shlocalaites.aitesconnector;

import java.util.ArrayList;

import com.github.aites.framework.aitesconnector.Factory;
import com.github.aites.shlocalaites.aitesmanager.AnalyzerManager;


public class Analyzer implements Factory{
	private ArrayList<String> monitorInfo;
	private String clientID;
	private String ruleSetURL;
	
	public Analyzer(ArrayList<String> monitorInfo, String clientID, String ruleSetURL){
		this.monitorInfo = monitorInfo;
		this.clientID = clientID;
		this.ruleSetURL = ruleSetURL;
	}
	@Override
	public AnalyzerManager createManager() {
		
		return new AnalyzerManager(monitorInfo,clientID,ruleSetURL);
	}
}
