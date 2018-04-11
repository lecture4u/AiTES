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
    public SWRLrule(String ruleName, String classAtom, String classVar, String annotation){
    	this.annotation = makeAnnotation(annotation);
    	headClassAtom = new ClassAtom(ruleName,classVar);
    	bodyClassAtom = new ClassAtom(classAtom, classVar);
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
					}else if(bodyAtomType.equals("BuiltIn")){
						
						BuiltInAtom ba = parseBuiltInAtom(parsedRule[j]);
						ruleBulitInAtoms.add(ba);
					}
					
				}
			}
			else if(parsedRule[i].contains("Head")){
				
				headAtom = parsedRule[i+1];
			
				headClassAtom = parseClassAtom(headAtom);
				
			}
			else if(parsedRule[i].contains("Annotation")){
		
				annotation = parsedRule[i]+"\r\n";
			}
			
			
		}
	}
	public ClassAtom getHeadClassAtom(){
		return headClassAtom;
	}
	public void updateBulitInAtom(int bulitInIndex, String newValue){
		BuiltInAtom bulitIn = ruleBulitInAtoms.get(bulitInIndex);
		System.out.println("update BuiltInCondition:"+newValue);
		bulitIn.setDataValue(newValue);
	    
		ruleBulitInAtoms.set(bulitInIndex, bulitIn);
		makeSWRLrule();
	}
	public void makeSWRLrule(){
		System.out.println("####Making SWRL rule#####");
		swrlRule = "    DLSafeRule(\r\n"+annotation+
				   "        Body(\r\n";
		swrlRule = swrlRule+bodyClassAtom.makeClassAtom();
		for(PropertyAtom p:rulePropertyAtoms){
			swrlRule = swrlRule+p.makePropertyAtom();
		}
		for(BuiltInAtom b : ruleBulitInAtoms){
			swrlRule = swrlRule+b.makeBultInAtom();
		}
		swrlRule = swrlRule +"        )\r\n";
		swrlRule = swrlRule +"        Head(\r\n";
		swrlRule = swrlRule +headClassAtom.makeClassAtom();
		swrlRule = swrlRule +"        )\r\n";
		swrlRule = swrlRule +"    )\r\n";
		System.out.println(swrlRule);
	}
	public void addPropertyAtom(String type, String property, String[] variables){
		PropertyAtom propertyAtom = new PropertyAtom(type,property,variables);
		rulePropertyAtoms.add(propertyAtom);
	}
    public void addBuiltInAtom(String swrlAtom,String variable,String dataValue, String dataType){
    	BuiltInAtom builtInAtom = new BuiltInAtom(swrlAtom,variable, dataValue, dataType);
    	ruleBulitInAtoms.add(builtInAtom);
	}
    public String getSWRLrule(){
    	return swrlRule;
    }
    private String makeAnnotation(String annotation){
  
    	annotation = "        Annotation(rdfs:comment \""+annotation+"\")\r\n";
    	return annotation;
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
    private BuiltInAtom parseBuiltInAtom(String atomBody){
    	String bbap = atomBody.substring(30, atomBody.length());
    	int bbapEndIndex = bbap.indexOf(" ");
    	bbap = bbap.substring(0,bbapEndIndex); // equals, graaterThen, etc... 
    	
    	String bpvps = atomBody.substring(31+bbapEndIndex,atomBody.length()-1);
    	int bbvpsIndex = bpvps.indexOf(")");
    	bpvps = bpvps.substring(13,bbvpsIndex); // var:v
    	
    	
    	int bargusIndex = atomBody.indexOf("\"");
    	int bargueIndex = atomBody.indexOf("^");
    	String bargu = atomBody.substring(bargusIndex+1,bargueIndex-1);
    	String bartype = atomBody.substring(bargueIndex+6, atomBody.length()-1);
        	
    	
    	BuiltInAtom builtInAtom = new BuiltInAtom(bbap,bpvps, bargu, bartype);
    	
    	return builtInAtom;
	}
	public class ClassAtom{
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
		public String getClassAtom(){
			return classAtom;
		}
		public String makeClassAtom(){
			String classAtomStr = "            ClassAtom(:"+classAtom+" Variable(var:"+variable+"))\r\n";
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
			String propertyAtom = "            "+type+"Atom(:"+property;
			for(int i=0; i<variables.length; i++){
				propertyAtom = propertyAtom + " Variable(var:"+variables[i]+")";
			}
			propertyAtom = propertyAtom + ")\r\n";
			
			return propertyAtom;
		}
		
	}
	class BuiltInAtom{
		private String variable;
		private String dataType;
		private String swrlAtom;
		private String dataValue;
		BuiltInAtom(String swrlAtom,String variable,String dataValue, String dataType){
			this.dataValue =dataValue;
			this.variable = variable;
			this.swrlAtom = swrlAtom;
			this.dataType = dataType;
		}
		void setAtom(String swrlAtom){
			this.swrlAtom = swrlAtom;
		}
		void setPorerty(String dataType){
			this.dataType = dataType;
		}
		void setVariables(String variable){
			this.variable = variable;
		}
		void setDataValue(String dataValue){
			this.dataValue = dataValue;
		}
		String makeBultInAtom(){
			String propertyAtom = "            BuiltInAtom(swrlb:"+swrlAtom+" Variable(var:"+variable+")";						 			
			propertyAtom = propertyAtom+" \"" + dataValue + "\"^^xsd:"+dataType+")\r\n";
			
			
			return propertyAtom;
		}
		
	}
}
