package de.stetro.matin.dbw.prak1.parser;

import de.stetro.matin.dbw.prak1.entities.employees.Employees;
import de.stetro.matin.dbw.prak1.entities.products.Products;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

// /Users/stetro/Source/MTIN/DBW/prak1/src/src/main/resources/definitions/products.xml
// /Users/stetro/Source/MTIN/DBW/prak1/src/src/main/resources/definitions/employees.xml

public class XmlParser {
    public Products parseProductsWithSelectedPath(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            JAXBContext jc = null;
            try {
                jc = JAXBContext.newInstance("de.stetro.matin.dbw.prak1.entities.products");
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                return (Products) unmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                throw new Exception("Error during parsing ...");
            }
        } else {
            throw new Exception("File does not exist ...");
        }
    }

    public Employees parseEmployeesWithSelectedPath(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            JAXBContext jc = null;
            try {
                jc = JAXBContext.newInstance("de.stetro.matin.dbw.prak1.entities.employees");
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                return (Employees) unmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                throw new Exception("Error during parsing ...");
            }
        } else {
            throw new Exception("File does not exist ...");
        }
    }
}
