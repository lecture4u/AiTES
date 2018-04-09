package com.github.aites.framework.rule;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;



import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

public class RuleManager {
	OWLOntologyManager manager; 
	OWLReasonerFactory resonerFactory;
	OWLDataFactory dataFactory;
	OWLReasoner reasoner;
	OWLOntology ontology;
	PrefixOWLOntologyFormat pm;
	private static String BASE_URL = "";
	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
	
	public RuleManager(String url){
		BASE_URL = url;
		manager = OWLManager.createOWLOntologyManager();
		resonerFactory = PelletReasonerFactory.getInstance();
	}
	
	public void loadOntology() {
		
		try{
			System.out.println("Load Ontology");
	
			ontology = manager.loadOntologyFromOntologyDocument(new File(BASE_URL));
			
			System.out.println("Loaded ontology: "+ontology);
		
			reasoner = resonerFactory.createReasoner(ontology, new SimpleConfiguration());
			dataFactory = manager.getOWLDataFactory();
		
			pm = manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat();
		
			
		}catch(OWLOntologyCreationException e){
			e.printStackTrace();
		}
		
	}
	public boolean reasoningRule(String indName, String ruleName){
		OWLNamedIndividual ind = dataFactory.getOWLNamedIndividual(":" + indName, pm);
		
		
		OWLClass ruleClass = dataFactory.getOWLClass(":"+ruleName, pm);
		OWLClassAssertionAxiom axoimToExplain = dataFactory.getOWLClassAssertionAxiom(ruleClass, ind);
		if(reasoner.isEntailed(axoimToExplain)){
			return true;
		}
		else{
			return false;
		}
	}
	public static void listAllDataPropertyValues(OWLNamedIndividual individual,OWLOntology ontology,OWLReasoner reasoner) { 
        OWLObjectRenderer renderer = new DLSyntaxObjectRenderer(); 
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> assertedValues = individual.getDataPropertyValues(ontology); 
        for (OWLDataProperty dataProp : ontology.getDataPropertiesInSignature(true)) { 
            for (OWLLiteral literal : reasoner.getDataPropertyValues(individual, dataProp)) { 
                Set<OWLLiteral> literalSet = assertedValues.get(dataProp); 
                boolean asserted = (literalSet!=null&&literalSet.contains(literal)); 
                System.out.println((asserted ? "asserted" : "inferred") + " data property for "+renderer.render(individual)+" : " 
                        + renderer.render(dataProp) + " -> " + renderer.render(literal)); 
            } 
        } 
    } 
}
