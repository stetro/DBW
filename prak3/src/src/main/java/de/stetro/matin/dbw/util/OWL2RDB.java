package de.stetro.matin.dbw.util;


import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public interface OWL2RDB {

    public void loadOntologyFromFile() throws OWLOntologyCreationException;
}
