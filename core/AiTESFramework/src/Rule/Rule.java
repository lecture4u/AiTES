package Rule;


public class Rule {
	private String ruleName;
	private String classAtom;
	private String ruleBody;
	
	public Rule(String ruleName, String classAtom, String variable){
		this.ruleName = ruleName;
		classAtom = "ClassAtom(:"+classAtom+" Variable(var:"+variable+")) ";
		ruleBody = classAtom;
	}
	public void addPropertyAtom(String atomType, String property, String[] variables){
		String propertyAtom = new String();
		if(atomType == "DataProperty"){
			propertyAtom = "DataPropertyAtom(:"+property;
			for(int i=0; i<variables.length; i++){
				propertyAtom = propertyAtom + " Variable(var:"+variables[i]+")";
			}
			propertyAtom = propertyAtom + ") ";
		}    
		else if(atomType == "ObjectProperty"){
			propertyAtom = "ObjectPropertyAtom(:"+property;
			for(int i=0; i<variables.length; i++){
				propertyAtom = propertyAtom + " Variable(var:"+variables[i]+")";
			}
			propertyAtom = propertyAtom + ") ";
		}
		ruleBody = ruleBody + propertyAtom;
		
	}
	public void addBuiltInAtom(String property, String[] variables, String atom){
		
		String propertyAtom = "BuiltInAtom(swrlb:"+property;
		for(int i=0; i<variables.length; i++){
			propertyAtom = propertyAtom + " Variable(var:"+variables[i]+") ";
		}
		propertyAtom = propertyAtom + atom;
		propertyAtom = propertyAtom + ")";
		ruleBody = ruleBody + propertyAtom;
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
}
