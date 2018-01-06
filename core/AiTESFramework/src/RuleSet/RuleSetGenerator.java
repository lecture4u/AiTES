package RuleSet;
import java.io.*;
import java.util.ArrayList;

import DBComponent.DBForeignKey;
import DBComponent.DBProperty;
import DBComponent.DBTable;
public class RuleSetGenerator {
	private static String head;
	private static final String tail = ")";
	private String body ="";
	OWLClassGenerator owlclassg;
	private ArrayList<DBTable> tableList;
	
	private File ruleSetFile;
	public RuleSetGenerator(String owlURL, ArrayList<DBTable> tableList){
		head =  "Prefix(swrl:=<http://www.w3.org/2003/11/swrl#>)\n"+
				"Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"+
                "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"+
				"Prefix(var2:=<urn:http://www.w3.org/2003/11/swrl#>)\n"+
                "Prefix(var:=<urn:swrl#>)\n"+
				"Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"+
                "Prefix(swrlb:=<http://www.w3.org/2003/11/swrlb#>)\n"+
				"Prefix(:=<"+owlURL+">)\n"+
				"Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"+
                "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n"+
				"Ontology(<"+owlURL+">\n";
		
		this.tableList = tableList;
		owlclassg = new OWLClassGenerator(tableList);
	}
	public void genearateClass(DBTable table){
		
		String className = stringFirstUpper(table.getTableName());
		body = body  + owlclassg.owlClassGener(className);
	}
	public void addRuleToRuleSet(String ruleName, String rule){
		String ruleClassAssertion = "Declaration(Class(:"+ruleName+"))\n";
		body = body + ruleClassAssertion;
		body = body + rule + "\n";
	}
    public void generateRuleOntology(){
    	for(int i=0; i<tableList.size(); i++){
      	  DBTable tempDT = tableList.get(i);
      	  tempDT.printDBTable();
      	  genearateClass(tempDT);
      	  generateObjectProperty(tempDT);
        }
        generateDataProperty();
    }
	public void generateDataProperty(){
		OWLPropertyGenerator owlpropertyg = new OWLPropertyGenerator();
		
		for(int i=0; i<tableList.size(); i++){
			String primaryKey = tableList.get(i).getPrimaryKey();
			System.out.println(primaryKey);
			ArrayList<DBProperty> attributeList = tableList.get(i).getPropertys();
			for(int j =0; j<attributeList.size(); j++){
				DBProperty property = attributeList.get(j);
				
				if(!property.getName().equals(primaryKey)){
					body = body+owlpropertyg.owlDataPropertyGener(property.getName());
				}
				
			}
			
			
		}
	}
	public void generateObjectProperty(DBTable table){
		body = body  + owlclassg.classRelationGenerate(table);
	}
	public File getRuleSetFile(){
		return ruleSetFile;
	}
	public void rulesetGen(String saveURL, String rulesetName){
		try {
		      ////////////////////////////////////////////////////////////////
		      BufferedWriter out = new BufferedWriter(new FileWriter(rulesetName));
		
		      String ruleset = head+body+tail;
		      out.write(ruleset); out.newLine();
		   
		      
		      out.close();
		     
		      ////////////////////////////////////////////////////////////////
		      
		      ruleSetFile = new File(rulesetName);
		     
		    } catch (IOException e) {
		        System.err.println(e); // 에러가 있다면 메시지 출력
		        System.exit(1);
		    }
	}
	private String stringFirstUpper(String data){
		String transString = data.substring(0,1);
		transString = transString.toUpperCase();
		transString += data.substring(1);
		return transString;
	}
	
}
