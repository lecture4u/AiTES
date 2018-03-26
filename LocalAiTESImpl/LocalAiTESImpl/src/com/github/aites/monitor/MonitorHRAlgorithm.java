package com.github.aites.monitor;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.github.aites.log.LogWritter;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

public class MonitorHRAlgorithm {
	EnvData envData;
	
	LogWritter log = LogWritter.getInstance();
	public MonitorHRAlgorithm(EnvData envData){
		this.envData = envData;
	}
	public String getAllEnvData(){
		String envResult = "";
		for(int di=0; di<envData.getDeviceData().size(); di++){
			if(di==0){
				envResult = envResult+envData.getDeviceData().get(di);
			}
			else{
				envResult = envResult+","+envData.getDeviceData().get(di);
			}
			
		}
		
		return envResult;
	}
	public String envDataHRAlgorithm(){
		log.logInput("*****Smart Home Environment Data Heuristic funtion*****");
	
		Double hresult =0.0;
		for(int di=0; di<envData.getDeviceData().size(); di++){
			envData.getDeviceNmae().get(di);
			hresult += Double.parseDouble(envData.getDeviceData().get(di));
			
		}
		log.logInput("Power consumtion:"+hresult);
		
		if(hresult < 600){
			return "under";
		}
		else if(hresult>1300){
			return "over";
		}
		return "normal";
		
 
		
	}
}



