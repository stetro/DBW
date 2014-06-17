package de.stetro.matin.dbw.util;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OWL2RDBImpl implements OWL2RDB {

    private OWLOntology ontology;

    public void loadOntologyFromFile() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        ontology = manager
                .loadOntologyFromOntologyDocument(new File("ontology.owl"));
        System.out.println("Loaded ontology: " + ontology.getOntologyID());
    }
}
