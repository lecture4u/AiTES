package Test;

import java.io.File;
import java.util.ArrayList;

import Rule.Rule;
import Rule.RuleSetParser;
import LocalPropertyConnect.ConnectionStarter;
import LocalPropertyConnect.DBConnector;
import RuleSet.DBSchemaLoader;
import RuleSet.RuleSetGenerator;

public class TestMain {
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Rule> ruleList = new ArrayList<Rule>();
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
	          
	         
	          Rule rule1 = new Rule("OverPower","Cctv","p","\"Rule for judge CCTV overpower condition\"");
	          rule1.addPropertyAtom("DataProperty", "power_consumtion", ruleBody1Variable);
	          rule1.addBuiltInAtom("greaterThan",ruleBody1BuiltVariable, "\"120\"^^xsd:integer");         
	          ruleList.add(rule1);
	          
	          Rule rule2 = new Rule("UnderPower","Cctv","p","\"Rule for judge CCTV underpower condition\"");
	          rule2.addPropertyAtom("DataProperty", "power_consumtion", ruleBody1Variable);
	          rule2.addBuiltInAtom("lessTan", ruleBody1BuiltVariable, "\"20\"^^xsd:integer");
	          ruleList.add(rule2);
	          
	          Rule rule3 = new Rule("StatusEqualAct","Device","p","\"Rule about status act \"");
	          rule3.addPropertyAtom("DataPropertyAtom", "power_consumtion", ruleBody1Variable);
	          rule3.addBuiltInAtom("equal", ruleBody3BuiltVariable, "\"act\"^^xsd:string");
	          ruleList.add(rule3);
	          
	          for(int i=0; i<ruleList.size(); i++){
	        	  ruleList.get(i).makeRuleBody();
	        	  ruleSetGener.addRuleToRuleSet(ruleList.get(i));
	          }
	    	 String fileName = "testRuleSet.xml";
	          ruleSetGener.rulesetGen("./",fileName);
	          
	          File ruleSet = ruleSetGener.getRuleSetFile();
	          
	          RuleSetParser rulesetParser = new RuleSetParser();
	          rulesetParser.ruleSetParsing(fileName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
