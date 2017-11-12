package Rule;
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
public class RuleSetLoader {
	private String ruleSetURL;
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
		
	}
}
