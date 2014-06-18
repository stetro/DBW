package de.stetro.matin.dbw;

import de.stetro.matin.dbw.util.DatabaseInitialisation;
import de.stetro.matin.dbw.util.OWL2RDB;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {
    private static ApplicationContext context;


    public static void main(String[] args) throws OWLOntologyCreationException {
        context = new ClassPathXmlApplicationContext("classpath*:/META-INF/applicationContext.xml");

        OWL2RDB owl2rdb = context.getBean(OWL2RDB.class);

        DatabaseInitialisation initializer = context.getBean(DatabaseInitialisation.class);
        initializer.initialize(context);

        // OWL Bootstrap
        owl2rdb.loadOntologyFromFile();
        owl2rdb.selectCompaniesFromRDB2OWL(context);

    }

    public static ApplicationContext getContext() {
        return context;
    }
}
