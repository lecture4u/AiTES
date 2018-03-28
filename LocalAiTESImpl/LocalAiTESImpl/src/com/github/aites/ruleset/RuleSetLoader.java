package com.github.aites.ruleset;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
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

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

public class RuleSetLoader {
	private static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private static OWLReasonerFactory resonerFactory = PelletReasonerFactory.getInstance();
	OWLDataFactory dataFactory;
	OWLReasoner reasoner;
	OWLOntology ontology;
	PrefixOWLOntologyFormat pm;
	private static String BASE_URL = "";
	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    
	private static class RuleSetLoaderSingleton{
		private static final RuleSetLoader instance = new RuleSetLoader();
	}
	
	public static RuleSetLoader getInstance(){

		return RuleSetLoaderSingleton.instance;
	}
	public void setRuleSetURL(String url){
		BASE_URL = url;
	}
	public void ruleSetLoad(){
		System.out.println("#####Load RuleSet FileName:"+BASE_URL+"######");
		try{
			ontology = manager.loadOntologyFromOntologyDocument(new File(BASE_URL));
			
			System.out.println("Loaded RuleSet Information: "+ontology);
		
			reasoner = resonerFactory.createReasoner(ontology, new SimpleConfiguration());
			dataFactory = manager.getOWLDataFactory();
			pm = manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat();
		
	        
			OWLClass personClass = dataFactory.getOWLClass(":SHEnvironment",pm);
			System.out.println(personClass);
		}catch(OWLOntologyCreationException e){
			e.printStackTrace();
		}
	}
}
