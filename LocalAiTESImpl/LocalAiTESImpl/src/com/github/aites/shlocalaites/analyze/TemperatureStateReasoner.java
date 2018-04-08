package com.github.aites.shlocalaites.analyze;

import java.io.File;
import java.util.Collections;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.github.aites.shlocalaites.log.LogWritter;
import com.github.aites.shlocalaites.rule.RuleManager;
import com.github.aites.shlocalaites.ruleset.RuleSetManager;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

public class TemperatureStateReasoner {
	String tempertureState;
	
	LogWritter log = LogWritter.getInstance();
	public String stateResoning(String temperture, String collectDate){
		log.logInput("*****Start Home Temperture State Reasoning*****");
		log.logInput(temperture);
		
		String feedBackInd = "SHEdata"+collectDate.replaceAll("[.: ]", "");
		log.logInput("#####Reasoing rule about individual:"+feedBackInd+" and dataProperty:"+tempertureState+"#####");
		RuleSetManager ruleSetManager = new RuleSetManager("smartHome.xml");
	    ruleSetManager.assertDataProperty("hasPS", feedBackInd, tempertureState, "double");
		ruleSetManager.saveRuleSet();
		
		RuleManager ruleManager = new RuleManager("smartHome.xml");
		ruleManager.loadOntology(); 
		boolean isHotTem= ruleManager.reasoningRule(feedBackInd, "TempertureHotRule");
		boolean isColdTem = ruleManager.reasoningRule(feedBackInd, "TempertureColdRule");
		if(isHotTem){
			tempertureState = "hot";
		}
		else if(isColdTem){
			tempertureState = "cold";
		}
		else{
			tempertureState = "warm";
		}
		
		return tempertureState;
	}
	
}
