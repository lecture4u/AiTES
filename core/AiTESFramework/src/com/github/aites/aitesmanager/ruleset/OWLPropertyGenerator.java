package com.github.aites.aitesmanager.ruleset;

public class OWLPropertyGenerator {
	public String owlDataPropertyGener(String attributeName){
		
	    String generateDataProperty = "Declaration(DataProperty(:"+attributeName+"))\n";
		return generateDataProperty;
	}
	public String owlObjectPropertyGener(String attributeName){
		String generateObejctProperty = "Declaration(ObjectProperty(:has"+attributeName+"))\n"
									   +"Declaration(ObjectProperty(:is"+attributeName+"))\n"
									   +"InverseObjectProperties(:has"+attributeName+" :is"+attributeName+")\n" ;
		return generateObejctProperty;
	}
}
