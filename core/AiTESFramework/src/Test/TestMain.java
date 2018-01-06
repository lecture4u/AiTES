package Test;

import java.io.File;

import Rule.Rule;
import Rule.RuleGenerator;
import LocalPropertyConnect.ConnectionStarter;
import LocalPropertyConnect.DBConnector;
import RuleSet.DBSchemaLoader;
import RuleSet.RuleSetGenerator;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello Test");
		try{
			  DBConnector db = new ConnectionStarter("jdbc:mysql://220.149.235.85:3306/globalknowledge","root","1213");
			  db.dbConnect();
			
			  DBSchemaLoader dbLoader = new DBSchemaLoader(db);
			  dbLoader.loadSchemaFromDB();
			  RuleGenerator ruleG = new RuleGenerator();
			  RuleSetGenerator ruleSetGener= new RuleSetGenerator("http://com.github.aites/ontologies/smarthome.owl#",dbLoader.getDBTableList());
			  ruleSetGener.generateRuleOntology();
		   	  
			  String[] ruleBody1Variable = {"p","power"};
	          String[] ruleBody1BuiltVariable = {"power"};
	              
	       
	          String[] ruleBody3BuiltVariable = {"stat"};
	          
	         
	          Rule ruleBody1 = new Rule("OverPower","Cctv","p");
	      
	          ruleBody1.addPropertyAtom("DataProperty", "power_consumtion", ruleBody1Variable);
	          ruleBody1.addBuiltInAtom("greaterThan",ruleBody1BuiltVariable, "\"120\"^^xsd:integer");         
	          
	          Rule ruleBody2 = new Rule("UnderPower","Cctv","p");
	          ruleBody2.addPropertyAtom("DataProperty", "power_consumtion", ruleBody1Variable);
	          ruleBody2.addBuiltInAtom("lessTan", ruleBody1BuiltVariable, "\"20\"^^xsd:integer");
	          
	          Rule ruleBody3 = new Rule("StatusEqualAct","Device","p");
	          ruleBody3.addPropertyAtom("DataPropertyAtom", "power_consumtion", ruleBody1Variable);
	          ruleBody3.addBuiltInAtom("equal", ruleBody3BuiltVariable, "\"act\"^^xsd:string");
	          
	          String rule1 = ruleG.ruleGenerate(ruleBody1.getRuleName(), ruleBody1.getRuleBody(), "\"Rule for judge CCTV owerfower condition\"", "p");
	          String rule2 = ruleG.ruleGenerate(ruleBody2.getRuleName(), ruleBody2.getRuleBody(), "\"Rule for judge CCTV underpower condition\"", "p");
	          String rule3 = ruleG.ruleGenerate(ruleBody3.getRuleName(), ruleBody3.getRuleBody(), "\"Rule about status act \"", "s");
	         
	          
	          ruleSetGener.addRuleToRuleSet(ruleBody1.getRuleName(), rule1);
	          ruleSetGener.addRuleToRuleSet(ruleBody2.getRuleName(), rule2);
	          ruleSetGener.addRuleToRuleSet(ruleBody3.getRuleName(), rule3);
	          
	          ruleSetGener.rulesetGen("./","testRuleSet.xml");
	          
	          File ruleSet = ruleSetGener.getRuleSetFile();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
