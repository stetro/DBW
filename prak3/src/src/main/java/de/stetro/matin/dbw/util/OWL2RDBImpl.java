package de.stetro.matin.dbw.util;

import de.stetro.matin.dbw.domain.Company;
import de.stetro.matin.dbw.repos.CompanyRepository;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OWL2RDBImpl implements OWL2RDB {

    private static String IRI_PATH = "http://www.semanticweb.org/de/mtin/dbw2/prak3/company";
    private OWLOntology ontology;
    private OWLOntologyManager manager;

    public void loadOntologyFromFile() throws OWLOntologyCreationException {
        manager = OWLManager.createOWLOntologyManager();
        ontology = manager
                .loadOntologyFromOntologyDocument(new File("ontology.owl"));
        System.out.println("Loaded ontology: " + ontology.getOntologyID());
    }

    @Override
    public void selectCompaniesFromRDB2OWL(ApplicationContext context) {
        CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
        OWLClass company = getClassByName("Company");
        OWLDataFactory factory = manager.getOWLDataFactory();
        for (Company c : companyRepository.findAll()) {
            OWLIndividual i = factory.getOWLNamedIndividual(IRI.create(IRI_PATH + "#" + c.getName()));
            System.out.println("adding company - " + c.getName());
            OWLAxiom axiom = factory.getOWLClassAssertionAxiom(company, i);
            manager.addAxiom(ontology, axiom);
        }

        try {
            manager.saveOntology(ontology, new StreamDocumentTarget(System.out));
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    private OWLClass getClassByName(String name) {
        for (OWLClass cl : ontology.getClassesInSignature()) {
            if (cl.getIRI().toString().contains(IRI_PATH + "#" + name)) {
                return cl;
            }
        }
        return null;
    }
}
