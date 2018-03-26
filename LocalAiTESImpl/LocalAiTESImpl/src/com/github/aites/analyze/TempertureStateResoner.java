package com.github.aites.analyze;

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
import com.github.aites.log.LogWritter;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

public class TempertureStateResoner {
	String tempertureState;
	String tempIndname = "envData1";
	LogWritter log = LogWritter.getInstance();
	public String stateResoning(String temperture){
		log.logInput("*****Start Home Temperture State Reasoning*****");
		log.logInput(temperture);
		
		double temp = Double.parseDouble(temperture);
		
		if(temp > 28){
			tempertureState = "hot";
		}
		else if(temp < 18){
			tempertureState = "cold";
		}
		else{
			tempertureState = "normal";
		}
		
		return tempertureState;
	}
	
}
