package com.github.aites.ruleset;



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
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

public class OWLExamManager {
	OWLOntologyManager manager; 
	OWLReasonerFactory resonerFactory;
	OWLDataFactory dataFactory;
	OWLReasoner reasoner;
	OWLOntology ontology;
	PrefixOWLOntologyFormat pm;
	private static String BASE_URL = "";
	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
	
	public OWLExamManager(String url){
		BASE_URL = url;
		manager = OWLManager.createOWLOntologyManager();
		resonerFactory = PelletReasonerFactory.getInstance();
	}
	
	public boolean loadOntology(String Client) {
		boolean a = false;
		try{
			System.out.println("Load Ontology");
	
			ontology = manager.loadOntologyFromOntologyDocument(new File(BASE_URL));
			
			System.out.println("Loaded ontology: "+ontology);
		
			reasoner = resonerFactory.createReasoner(ontology, new SimpleConfiguration());
			dataFactory = manager.getOWLDataFactory();
		
			pm = manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat();
		
			
			//Client
			OWLClass personClass = dataFactory.getOWLClass(":Client",pm);
			System.out.println(personClass);
			
			OWLNamedIndividual client1 = dataFactory.getOWLNamedIndividual(":" + Client, pm);
			//listAllDataPropertyValues(client1, ontology, reasoner);
			
			//DLSafeRule: Transfer(Client�쓽 愿�怨꾩� 湲덉븸 �솗�씤)
			OWLClass TransferClass = dataFactory.getOWLClass(":Transfer", pm);
			OWLClassAssertionAxiom axoimToExplain = dataFactory.getOWLClassAssertionAxiom(TransferClass, client1);
			System.out.println("클라이언트는 거래가 가능합니까? :" + reasoner.isEntailed(axoimToExplain));
			a = reasoner.isEntailed(axoimToExplain);
			
			System.out.println("End");
			
			
		}catch(OWLOntologyCreationException e){
			e.printStackTrace();
		}
		return a;
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
