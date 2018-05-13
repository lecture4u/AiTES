package com.github.aites.framework.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.aites.framework.analyzer.StateCombiner;
import com.github.aites.framework.communicate.DataTransfer;
import com.github.aites.framework.executor.Effector;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.globalknowledge.ConnectionStarter;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.globalknowledge.LoadScheduleFromStateSet;
import com.github.aites.framework.orchestration.Device;
import com.github.aites.framework.orchestration.Participants;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.rule.SWRLrule;
import com.github.aites.framework.ruleset.DBSchemaLoader;
import com.github.aites.framework.ruleset.RuleSetGenerator;
import com.github.aites.framework.ruleset.RuleSetManager;

public class TestMain {
	 
	public static void main(String[] args) {
		Timer timer = Timer.getInstance();
		timer.setSystemTime("2017.12.30 0:00");
		RuleSetManager ruleSetManager;	
	    
		
		for(int i=0; i<=5; i++){
			ruleSetManager = new RuleSetManager("smarthome.xml");
			ruleSetManager.assertInd("SHEdata"+i, "SHEdata");
			ruleSetManager.assertDataProperty("hasPS", "SHEdata"+i, "1100.00", "double");
			ruleSetManager.saveRuleSet();
			ruleSetManager = new RuleSetManager("smarthome.xml");
			ruleSetManager.deleteInd("SHEdata3");
			ruleSetManager.saveRuleSet();
		}
		
		
		ruleSetManager = new RuleSetManager("smarthome.xml");
		ruleSetManager.printRuleSetInformation();	
		/*Participants participants = Participants.getInstance();
		ArrayList<String> actionList = new ArrayList<String>();
		actionList.add("power_saving");
		actionList.add("ready");
		actionList.add("stanby");
		actionList.add("activity");
		participants.setSoftwareActionList(actionList);
			
		participants.addDevice(new Device("Air_conditioner","connected","192.168.0.14","FGRC084451"));
		participants.addDevice(new Device("TV","connected","192.168.0.23","UN75J6350AFXK"));
		participants.addDevice(new Device("Lamp","connected","192.168.0.11","XMCTD01YL"));
		participants.addDevice(new Device("Robotic_Vaccum_Cleaner","connected","192.168.0.46","VR6480VMNC"));
		participants.addDevice(new Device("FlowerPot","connected","192.168.0.23","GA3A00417A14"));
		participants.addDevice(new Device("Washing_Machine","connected","192.168.0.11","F17WPSW"));
		participants.addDevice(new Device("Lamp2","connected","192.168.0.36","BE1915VWSP8"));
		participants.addDevice(new Device("Smart_Cook_Pot","connected","192.168.0.45","SCCPWM600-V2"));
		participants.addDevice(new Device("CCTV","connected","192.168.0.22","722823017"));
		participants.addDevice(new Device("Mixer","connected","192.168.0.45","B06XRKC719"));
		participants.addDevice(new Device("AI_Speaker","connected","192.168.0.61","GA3A00417A14"));
		participants.addDevice(new Device("Oven","connected","192.168.0.55","JC00A121601"));
		
	    participants.setDeviceActionLevel("Air_conditioner", 2);
	    participants.setDeviceActionLevel("TV", 2);
	    participants.setDeviceActionLevel("Lamp", 2);
	    participants.setDeviceActionLevel("Robotic_Vaccum_Cleaner", 2);
	    participants.setDeviceActionLevel("FlowerPot", 2);
	    participants.setDeviceActionLevel("Washing_Machine", 2);
	    participants.setDeviceActionLevel("Lamp2", 2);
	    participants.setDeviceActionLevel("Smart_Cook_Pot", 2);
	    participants.setDeviceActionLevel("CCTV", 2);
	    participants.setDeviceActionLevel("Mixer", 2);
	    participants.setDeviceActionLevel("AI_Speaker", 2);
	    participants.setDeviceActionLevel("Oven", 2);*/
		
		
		
		/*
		try{
			DBConnector db = new ConnectionStarter("jdbc:mysql://220.149.235.85:3306/globalknowledge","root","1234");
			db.dbConnect();
			
			String stateSet = "over,par,normal";
			System.out.println("Start StateCombinationCalculator");
			StateCombinationCalculator scc = new StateCombinationCalculator(stateSet);
			for(int i=1; i<=scc.getStateSetLength(); i++){
				scc.CalstateCombinateion(scc.getStateSetLength(), i, 0);
				//db = new LoadScheduleFromStateSet();
				System.out.println("");
			}
			ArrayList<String> allOccurStateSet = scc.getAllOccurStateSet();
			
			for(int i=0; i<allOccurStateSet.size(); i++){
				System.out.println(allOccurStateSet.get(i));
				db = new LoadScheduleFromStateSet(allOccurStateSet.get(i),timer,"sh");
				db.dbConnect();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}*/
		//RuleSetManager ruleSetManager2 = new RuleSetManager("smartHome.xml");
		//ruleSetManager2.deleteInd("SHEdata2");
		//ruleSetManager2.saveRuleSet();
		
		/*ArrayList<String> testStateSet = new ArrayList<String>();
		testStateSet.add("over");
		testStateSet.add("cold");
		testStateSet.add("out");	
		
		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		hashmap.put(2, "position");
		hashmap.put(1, "temperture");
		hashmap.put(0, "ps");
		StateCombiner testCombiner = new StateCombiner(testStateSet,"2018 09 14",hashmap);
		String pneed = testCombiner.reasoningStateSetNeedPlan();
		String stateSet = testCombiner.getStateSet();
		*/
		//System.out.println("pnned: " + pneed + ",StateSet: "+stateSet);
		
		
		// TODO Auto-generated method stub
		/*RuleSetManager ruleSetManager2 = new RuleSetManager("smartHome.xml");
		
		System.out.println("-----SWRLRule add test-----");
		SWRLrule testRule = new SWRLrule("TestRule", "SHEdata", "c", "rule for test");
		
		String variables[]  = {"c","tem"};
		String variable = "tem";
		String dataValue= "12";
		String dataType= "int";
		
		testRule.addPropertyAtom("DataProperty", "hasTemperture", variables);
		testRule.addBuiltInAtom("greaterThen", variable, dataValue, dataType);
		testRule.addBuiltInAtom("lessThen", variable, dataValue, dataType);
		testRule.updateBulitInAtom(0, "24");
		testRule.makeSWRLrule();
		
	    ruleSetManager2.assertSWRLrule(testRule);
	    ruleSetManager2.updateSWRLBulitInRule("TestRule", 1, "64");
		ruleSetManager2.saveRuleSet();
		
		ruleSetManager2.deleteInd("SHEData1");
		//ruleSetManager2.saveRuleSet();*/
		
		//Effector effector = new Effector("/Users/srsok/AiTES/core/AiTESFramework/modules");
		//effector.effectIoTgateway("Air_conditioner", "active");
		
		
		
		
		/*
		RuleManager ruleManager = new RuleManager("smartHome.xml");
		ruleManager.loadOntology(); 
		boolean isOverPS = ruleManager.reasoningRule("SHEdata1", "PSoverRule");
		System.out.println("testIsOverPS?:"+isOverPS);
		boolean isHot = ruleManager.reasoningRule("SHEdata1", "TempertureHotRule");
		System.out.println("testIsHot?:"+isHot);
		boolean isNearHome = ruleManager.reasoningRule("SHEdata1", "PositionNearRule");
		System.out.println("testIsNearHome?:"+isNearHome);
		*/
		
		
		
		
		
		
		
		
		
		
		
		/*
		System.out.println("Hello Test");
		try{
			  DBConnector db = new ConnectionStarter("jdbc:mysql://220.149.235.85:3306/globalknowledge","root","1213");
			  db.dbConnect();
			
			  DBSchemaLoader dbLoader = new DBSchemaLoader(db);
			  dbLoader.loadSchemaFromDB();
	
			  RuleSetGenerator ruleSetGener= new RuleSetGenerator("http://com.github.aites/ontologies/smarthome.owl#",dbLoader.getDBTableList());
			  ruleSetGener.generateRuleOntology();
		   	  
			  String[] ruleBody1Variable = {"p","power"};
	          String[] ruleBody1BuiltVariable = {"power"};
	              
	       
	          String[] ruleBody3BuiltVariable = {"stat"};
	      */    
	         
	      //    Rule rule1 = new Rule("OverPower","Cctv","p","\"Rule for judge CCTV overpower condition\"");
	       //   rule1.addPropertyAtom("DataProperty", "power_consumtion", ruleBody1Variable);
	       //   rule1.addBuiltInAtom("greaterThan",ruleBody1BuiltVariable, "\"120\"^^xsd:integer");         
	       //   ruleList.add(rule1);
	          
	         // Rule rule2 = new Rule("UnderPower","Cctv","p","\"Rule for judge CCTV underpower condition\"");
	        //  rule2.addPropertyAtom("DataProperty", "power_consumtion", ruleBody1Variable);
	        //  rule2.addBuiltInAtom("lessTan", ruleBody1BuiltVariable, "\"20\"^^xsd:integer");
	        //  ruleList.add(rule2);
	          
	        //  Rule rule3 = new Rule("StatusEqualAct","Device","p","\"Rule about status act \"");
	        //  rule3.addPropertyAtom("DataPropertyAtom", "power_consumtion", ruleBody1Variable);
	        //  rule3.addBuiltInAtom("equal", ruleBody3BuiltVariable, "\"act\"^^xsd:string");
	        //  ruleList.add(rule3);
	          
	       //   for(int i=0; i<ruleList.size(); i++){
	        //	  ruleList.get(i).makeRuleBody();
	        //	  ruleSetGener.addRuleToRuleSet(ruleList.get(i));
	        //  }
	    	// String fileName = "testRuleSet.xml";
	        //  ruleSetGener.rulesetGen("./",fileName);
	          
	         // File ruleSet = ruleSetGener.getRuleSetFile();
	          
	        //  RuleSetParser rulesetParser = new RuleSetParser();
	          //rulesetParser.ruleSetParsing(fileName);
	          
	          //TestMqttClient mqttClient = new TestMqttClient();
	          //mqttClient.runClient();
		//}catch(Exception e){
		//	e.printStackTrace();
		//}
	}

}
