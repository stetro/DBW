package de.stetro.matin.dbw;

import de.stetro.matin.dbw.util.OWL2RDB;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
    private static ApplicationContext context;


    public static void main(String[] args) throws OWLOntologyCreationException {
        context = new ClassPathXmlApplicationContext("classpath*:/META-INF/applicationContext.xml");
        OWL2RDB owl2rdb = (OWL2RDB) context.getBean("owl2rdb");

        // OWL Bootstrap
        owl2rdb.loadOntologyFromFile();


    }

    public static ApplicationContext getContext() {
        return context;
    }
}
