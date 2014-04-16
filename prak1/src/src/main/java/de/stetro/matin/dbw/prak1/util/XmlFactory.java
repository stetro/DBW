package de.stetro.matin.dbw.prak1.util;

import de.stetro.matin.dbw.prak1.entities.employees.Employee;
import de.stetro.matin.dbw.prak1.entities.employees.Employees;
import de.stetro.matin.dbw.prak1.entities.products.Product;
import de.stetro.matin.dbw.prak1.entities.products.Products;

import javax.xml.bind.*;
import java.io.File;
import java.io.StringWriter;

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

    public Employees parseEmployeesWithSelectedPath(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            JAXBContext jc = null;
            try {
                jc = JAXBContext.newInstance(Employees.class);

                Unmarshaller unmarshaller = jc.createUnmarshaller();
                unmarshaller.setEventHandler(new ValidationEventHandler() {
                    @Override
                    public boolean handleEvent(ValidationEvent validationEvent) {
                        return false;
                    }
                });
                return (Employees) unmarshaller.unmarshal(file);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("File does not exist ...");
        }
    }

    public String marshallProduct(Product p) throws Exception {
        JAXBContext jc = null;
        Products products = new Products();
        products.getProduct().add(p);
        try {
            jc = JAXBContext.newInstance(Products.class);
            Marshaller marshaller = jc.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(products, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e1) {
            throw new Exception("Marshalling problems ...");
        }
    }

    public String marshallEmployee(Employee e) throws Exception {
        JAXBContext jc = null;
        Employees employees = new Employees();
        employees.getEmployee().add(e);
        try {
            jc = JAXBContext.newInstance(Employees.class);
            Marshaller marshaller = jc.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(employees, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e1) {
            throw new Exception("Marshalling problems ...");
        }
    }

    public String marshallProducts(Products products) throws Exception {
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(Products.class);
            Marshaller marshaller = jc.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(products, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e1) {
            throw new Exception("Marshalling problems ...");
        }
    }

    public String marshallEmployees(Employees employees) throws Exception {
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(Employees.class);
            Marshaller marshaller = jc.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(employees, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e1) {
            throw new Exception("Marshalling problems ...");
        }
    }
}
