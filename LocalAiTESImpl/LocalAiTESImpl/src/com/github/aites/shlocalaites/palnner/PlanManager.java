package com.github.aites.shlocalaites.palnner;

import java.util.ArrayList;
import java.util.Collections;

import com.github.aites.shlocalaites.log.LogWritter;

public class PlanManager {
	private String stateSet;
	ArrayList<String> stateList = new ArrayList<String>();
	ArrayList<Plan> palnList = new ArrayList<Plan>();
	LogWritter log = LogWritter.getInstance();
	public PlanManager(String stateSet){
		this.stateSet  = stateSet;
	}
	private void stateParser(){
		String[] stateparser = stateSet.split(",");
		Collections.addAll(stateList, stateparser);
	}
	public ArrayList<Plan> managePlan(){
		stateParser();
		log.logInput("*****manage Plan*****");
		if(stateList.get(0).equals("over") && stateList.get(1).equals("outHome") && stateList.get(2).equals("normal")){
			log.logInput("need Plan for over ps and outHome and normal");
			palnList.add(new Plan("2017.7.17 5:00","flowerPot","turnStanby"));
			palnList.add(new Plan("2017.7.17 5:00","Robotic Vacuum Cleaner","turnStanby"));
			palnList.add(new Plan("2017.7.17 5:00","CCTV","turnStanby"));
			palnList.add(new Plan("2017.7.17 5:00","TV","turnStanby"));
			palnList.add(new Plan("2017.7.17 6:00","Robotic Vacuum Cleaner","turnReady"));
			palnList.add(new Plan("2017.7.17 6:00","TV","turnReady"));
			palnList.add(new Plan("2017.7.17 7:00","TV","turnUReady"));
			
		}
		else if(stateList.get(0).equals("under") && stateList.get(1).equals("inHome") && stateList.get(2).equals("cold")){
			log.logInput("need Plan for under ps and inHome and cold");
			palnList.add(new Plan("2017.7.17 8:00","Air_conditioner","turnPS"));
			palnList.add(new Plan("2017.7.17 8:00","TV","turnPS"));
			palnList.add(new Plan("2017.7.17 8:00","Lamp","turnPS"));
			palnList.add(new Plan("2017.7.17 8:00","Robotic Vacuum Cleaner","turnPS"));
			palnList.add(new Plan("2017.7.17 9:00","Air_conditioner","turnOn"));
			palnList.add(new Plan("2017.7.17 9:00","TV","turnOn"));
		}
		else if(stateList.get(0).equals("over") && stateList.get(1).equals("inHome") && stateList.get(2).equals("normal")){
			log.logInput("need Plan for over ps and inHome and normal");
			palnList.add(new Plan("2017.7.17 9:00","Air_conditioner","turnReady"));
			palnList.add(new Plan("2017.7.17 9:00","TV","turnReady"));
			palnList.add(new Plan("2017.7.17 9:00","Smart Cooking Pot","turnReady"));
			palnList.add(new Plan("2017.7.17 10:00","Smart Cooking Pot","turnStanby"));
			palnList.add(new Plan("2017.7.17 10:00","Air_conditioner","turnStanby"));
		}
		
		return palnList;
	}
}
