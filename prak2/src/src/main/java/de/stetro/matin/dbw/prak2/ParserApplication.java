package de.stetro.matin.dbw.prak2;

import de.stetro.matin.dbw.prak2.daos.CompanyDao;
import de.stetro.matin.dbw.prak2.entities.company.Companies;
import de.stetro.matin.dbw.prak2.util.XmlFactory;

/**
 * ParserApplication - Main Application starts MainWindow
 */
public class ParserApplication {
    public static void main(String[] args) throws Exception {
        CompanyDao companyDao = new CompanyDao();
        XmlFactory xmlFactory = new XmlFactory();
        String filePath = "/Users/stetro/Source/MTIN/DBW/prak2/src/src/main/resources/definitions/companies.xml";
        System.out.println(filePath);
        Companies companies = xmlFactory.parseCompaniesWithSelectedPath(filePath);
        companyDao.persist(companies);
    }
}
