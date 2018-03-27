package com.github.aites.framework.rule;
import java.io.File;
import java.util.Map;
import java.util.Set;

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
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
public class RuleSetLoader {
	private String ruleSetURL;
	//private static final String BASE_URL = "http://acrab.ics.muni.cz/ontologies/tutorial.owl";
	public RuleSetLoader(String ruleSetURL){
		this.ruleSetURL = ruleSetURL;
	}
	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer(); 
	public void resonOntologyFromFile() throws OWLOntologyCreationException{
		System.out.println("*----------------------Load Ontology--------------------------------*");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); 
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(ruleSetURL));
        OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance(); 
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration()); 
        OWLDataFactory factory = manager.getOWLDataFactory(); 
        
        System.out.println(ontology);
        //PrefixManager pm = new DefaultPrefixManager(BASE_URL+"#");
		//((DefaultPrefixManager) pm).setDefaultPrefix(BASE_URL + "#");
     //   OWLClass rule1Class = factory.getOWLClass(":OverPower",pm);
      //  OWLClass rule2Class  = factory.getOWLClass(":UnderPower",pm);
      //  OWLClass rule3Class = factory.getOWLClass(":StatusEqualAct",pm);
       
       // OWLNamedIndividual cctvdata1 = factory.getOWLNamedIndividual(":cctvdata1", pm);
       
       // OWLClassAssertionAxiom axiomToRule1 = factory.getOWLClassAssertionAxiom(rule1Class, cctvdata1);
      //  OWLClassAssertionAxiom axiomToRule2 = factory.getOWLClassAssertionAxiom(rule2Class, cctvdata1);
      //  OWLClassAssertionAxiom axiomToRule3 = factory.getOWLClassAssertionAxiom(rule3Class, cctvdata1);
      //  if(reasoner.isEntailed(axiomToRule1)){
       // 	if(reasoner.isEntailed(axiomToRule3)){
       // 		System.out.println("this cctv status is overpower and acting");
       // 	}
       // }
        
      //  if(reasoner.isEntailed(axiomToRule2)){
       // 	if(reasoner.isEntailed(axiomToRule3)){
       // 		System.out.println("this cctv status is underpower and acting");
        //	}
       // }
	}
}
