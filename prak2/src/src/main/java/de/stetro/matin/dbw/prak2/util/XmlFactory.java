package de.stetro.matin.dbw.prak2.util;

import de.stetro.matin.dbw.prak2.entities.company.Companies;
import de.stetro.matin.dbw.prak2.entities.products.Products;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * XmlFactory - does marshalling and parsing
 *
 * @author stetro
 */
public class XmlFactory {
    public Products parseProductsWithSelectedPath(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            JAXBContext jc = null;
            try {
                jc = JAXBContext.newInstance(Products.class);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                return (Products) unmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                throw new Exception("Error during parsing ...");
            }
        } else {
            throw new Exception("File does not exist ...");
        }
    }

    public Companies parseCompaniesWithSelectedPath(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            JAXBContext jc = null;
            try {
                jc = JAXBContext.newInstance(Companies.class);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                return (Companies) unmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                throw new Exception("Error during parsing ...");
            }
        } else {
            throw new Exception("File does not exist ...");
        }
    }
}
