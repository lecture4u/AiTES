package com.github.aites.framework.rule;

import java.util.ArrayList;
/**
 * Class for parse and configure SWRL rule in ontology ruleset.
 * @author JungHyun An
 * @version 3.0.1
 * @see com.github.aites.framework.ruleset.RuleSetBody
 */
public class SWRLrule {
	private String swrlRule;
    private ArrayList<String> bodyAtoms = new ArrayList<String>();
    private String headAtom;
    private String annotation;
    
	private ClassAtom headClassAtom;
	
	private ClassAtom bodyClassAtom;
	private ArrayList<PropertyAtom> rulePropertyAtoms = new ArrayList<PropertyAtom>();
	private ArrayList<BuiltInAtom> ruleBulitInAtoms = new ArrayList<BuiltInAtom>();
    public SWRLrule(String swrlRule){
    	this.swrlRule = swrlRule;
    	ruleParsing();
    }
	private void ruleParsing(){ // parsing rule body, head.
		String dataPropertyString = "DataProperty";
		String[] parsedRule = swrlRule.split("\r\n");
		for(int i=0; i<parsedRule.length; i++){
			if(parsedRule[i].contains("Body")){
				for(int j=i+1; i<parsedRule.length; j++){
					if(parsedRule[j].equals("        )")){
						break;
					}
					bodyAtoms.add(parsedRule[j]);
					int bodyAtomTypeEndIndex = parsedRule[j].indexOf("Atom"); 
					String bodyAtomType = parsedRule[j].substring(12, bodyAtomTypeEndIndex);
					System.out.println("abcd:"+bodyAtomType);
					if(bodyAtomType.equals("Class")){
						bodyClassAtom = parseClassAtom(parsedRule[j]);
					
					}else if(bodyAtomType.equals(dataPropertyString)){
						PropertyAtom pa = parsePropertyAtom(parsedRule[j],dataPropertyString);
						rulePropertyAtoms.add(pa);
					}else if(bodyAtomType.equals("BuiltInAtom")){
						
					}
					
				}
			}
			else if(parsedRule[i].contains("Head")){
				
				headAtom = parsedRule[i+1];
			
				headClassAtom = parseClassAtom(headAtom);
				
			}
			else if(parsedRule[i].contains("Annotation")){
		
				annotation = parsedRule[i];
			}
			
			
		}
	}
	public void parsedRule(){
		System.out.println("####Parsed SWRL rule#####");
		System.out.println("Rule annotation:"+annotation);
		System.out.println("Rule headAtom:"+headAtom+", classAtom:"+headClassAtom.classAtom+", headVariable:"+headClassAtom.variable);
		for(String b : bodyAtoms){
			System.out.println("Rule bodyAtom:"+b);
		}
	}
	public void makeSWRLrule(){
		
	}
	private ClassAtom parseClassAtom(String atomBody){
		
		String bcap = atomBody.substring(23, atomBody.length());
		int hcapEndIndex = bcap.indexOf(" ");
		bcap = bcap.substring(0,hcapEndIndex);
		
		int bvapStartIndex = atomBody.indexOf("var:");
		int bvapEndIndex = atomBody.indexOf("))");
		String bvap = atomBody.substring(bvapStartIndex+4, bvapEndIndex);
		ClassAtom classAtom = new ClassAtom(bcap, bvap);
		return classAtom;
	}
	private PropertyAtom parsePropertyAtom(String atomBody, String type){
		String bpap = atomBody.substring(30, atomBody.length());
		int bpapEndIndex = bpap.indexOf(" ");
		bpap = bpap.substring(0,bpapEndIndex);
		
		String bpvps = atomBody.substring(31+bpapEndIndex,atomBody.length()-1);
		String variables[] = bpvps.split(" ");
		System.out.println(bpvps);
		for(int i=0; i<variables.length; i++){
			String var = variables[i].substring(13, variables[i].length()-1);
			variables[i] = var;
		}
		
		PropertyAtom propertyAtom = new PropertyAtom(type, bpap, variables);
		
		
		return propertyAtom;
	}
    /*private BuiltInAtom parseBuiltInAtom(String atomBody, String type){
		
	}*/
	class ClassAtom{
		private String classAtom;
		private String variable;
		ClassAtom(String classAtom, String variable){
			this.classAtom = classAtom;
			this.variable = variable;
		}
		void setClassAtom(String classAtom){
			this.classAtom = classAtom;
		}
		void variable(String variable){
			this.variable = variable;
		}
		public String makeClassAtom(){
			String classAtomStr = "        ClassAtom(:"+classAtom+" Variable(var:"+variable+")) ";
			return classAtomStr;
		}
	}
	class PropertyAtom{
		private String type;
		private String[] variables;
		private String property;
		
		
		PropertyAtom(String type, String property, String[] variables){
			this.type = type;
			this.variables = variables;
			this.property = property;
		}
		void setType(String type){
			this.type = type;
		}
		void stVariables(String[] variables){
			this.variables = variables;
		}
		void setProperty(String property){
			this.property = property;
		}
		String makePropertyAtom(){
			String propertyAtom = "        "+type+"(:"+property;
			for(int i=0; i<variables.length; i++){
				propertyAtom = propertyAtom + " Variable(var:"+variables[i]+")";
			}
			propertyAtom = propertyAtom + ") ";
			
			return propertyAtom;
		}
		
	}
	class BuiltInAtom{
		private String[] variables;
		private String atom;
		private String property;
		BuiltInAtom(String propertyName,String[] variables, String atom){
			
			this.variables = variables;
			this.atom = atom;
			this.property = propertyName;
		}
		void setAtom(String atom){
			this.atom = atom;
		}
		void setPorerty(String property){
			this.property = property;
		}
		void setVariables(String[] variables){
			this.variables = variables;
		}
		String makeBultInAtom(){
			String propertyAtom = "        BuiltInAtom(swrlb:"+property;
			for(int i=0; i<variables.length; i++){
				propertyAtom = propertyAtom + " Variable(var:"+variables[i]+")";
			}
			propertyAtom = propertyAtom+" "+ atom;
			propertyAtom = propertyAtom + ") ";
			
			return propertyAtom;
		}
		
	}
}
