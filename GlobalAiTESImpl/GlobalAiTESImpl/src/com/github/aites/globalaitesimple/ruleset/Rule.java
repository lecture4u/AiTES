package com.github.aites.globalaitesimple.ruleset;

import java.util.ArrayList;

public class Rule {
	private String ruleName;
	private String ruleBody;
	private ArrayList<PropertyAtom> propertyAtoms = new ArrayList<PropertyAtom>();
	private BulitInAtom builtInAtom;
	private ClassAtom classAtom;
	
	public Rule(String ruleName, String afilclass, String variable){
		this.ruleName = ruleName;
		
		classAtom = new ClassAtom(afilclass,variable);
		ruleBody = classAtom.makeClassAtom();
		
	}
	public Rule(){
		
	}
	public void addPropertyAtom(String atomType, String property, String[] variables){
		PropertyAtom propertyAtom;
		propertyAtom = new PropertyAtom(atomType,property,variables);
		propertyAtoms.add(propertyAtom);	
	}
	public void addBuiltInAtom(String property, String[] variables, String atom){
		builtInAtom = new BulitInAtom(property,variables,atom);
	}
	public void makeRuleBody(){
		ruleBody ="";
		ruleBody = classAtom.makeClassAtom();
		for(int i=0; i<propertyAtoms.size(); i++){
			ruleBody = ruleBody + propertyAtoms.get(i).makePropertyAtom();
		}
		ruleBody = ruleBody + builtInAtom.makeBultInAtom();
	}
	public void printRule(){
		System.out.println(ruleBody);
	}
	public String getRuleBody(){
		return ruleBody;
	}
	public String getRuleName(){
		return ruleName;
	}
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
			String classAtomStr = "ClassAtom(:"+classAtom+" Variable(var:"+variable+")) ";
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
			String propertyAtom = type+"(:"+property;
			for(int i=0; i<variables.length; i++){
				propertyAtom = propertyAtom + " Variable(var:"+variables[i]+")";
			}
			propertyAtom = propertyAtom + ") ";
			
			return propertyAtom;
		}
		
	}
	class BulitInAtom{
		private String[] variables;
		private String atom;
		private String property;
		BulitInAtom(String propertyName,String[] variables, String atom){
			
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
			String propertyAtom = "BuiltInAtom(:swrlb"+property;
			for(int i=0; i<variables.length; i++){
				propertyAtom = propertyAtom + " Variable(var:"+variables[i]+")";
			}
			propertyAtom = propertyAtom + atom;
			propertyAtom = propertyAtom + ") ";
			
			return propertyAtom;
		}
		
	}
}
