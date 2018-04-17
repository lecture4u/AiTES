package com.github.aites.framework.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.aites.framework.analyzer.StateCombiner;
import com.github.aites.framework.communicate.DataTransfer;
import com.github.aites.framework.executor.Effector;
import com.github.aites.framework.globalknowledge.ConnectionStarter;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.rule.SWRLrule;
import com.github.aites.framework.ruleset.DBSchemaLoader;
import com.github.aites.framework.ruleset.RuleSetGenerator;
import com.github.aites.framework.ruleset.RuleSetManager;

public class TestMain {
	 
	public static void main(String[] args) {
		//RuleSetManager ruleSetManager2 = new RuleSetManager("smartHome.xml");
		//ruleSetManager2.deleteInd("SHEdata2");
		//ruleSetManager2.saveRuleSet();
		ArrayList<String> testStateSet = new ArrayList<String>();
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
		
		System.out.println("pnned: " + pneed + ",StateSet: "+stateSet);
		
		
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
