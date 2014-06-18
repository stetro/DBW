package de.stetro.matin.dbw.util;


import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.context.ApplicationContext;

public interface OWL2RDB {

    public void loadOntologyFromFile() throws OWLOntologyCreationException;

    void selectCompaniesFromRDB2OWL(ApplicationContext context);
}
