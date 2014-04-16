package de.stetro.matin.dbw.prak2;

import de.stetro.matin.dbw.prak2.daos.CompanyDao;
import de.stetro.matin.dbw.prak2.daos.ProductDao;
import de.stetro.matin.dbw.prak2.entities.company.*;
import de.stetro.matin.dbw.prak2.entities.products.Product;
import de.stetro.matin.dbw.prak2.entities.products.Products;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;

/**
 * ParserApplication
 */
public class ParserApplication {
    private static ProductDao productDao;
    private static CompanyDao companyDao;

    public static void main(String[] args) throws Exception {
        productDao = new ProductDao();
        companyDao = new CompanyDao();

        parseProductAt("http://www.wlw.de/treffer/abreissmuttern.html");
        parseProductAt("http://www.wlw.de/treffer/datenlogger.html");
    }

    private static void parseProductAt(String url) throws Exception {
        Elements companyList = getCompanyElementsForProduct(url);
        Companies companies = new Companies();

        for (Element e : companyList) {
            try {
                companies.getCompany().add(getCompanyByElement(e));
            } catch (Exception exception) {
                System.out.println("Timeout or Status Error " + exception.getMessage());
            }

        }
        companyDao.persist(companies);
    }

    private static Company getCompanyByElement(Element e) throws IOException {
        System.out.println("Opening " + e.attr("data-link-to-fi"));
        Document doc = Jsoup.connect(e.attr("data-link-to-fi")).get();
        System.out.println("Parsing Company ...");
        Company company = new Company();

        company.setName(doc.select("article.vcard .name h1").text());

        Contact contact = new Contact();
        contact.setPhone(doc.select("article.vcard .contact .icon-tel").text());
        contact.setFax(doc.select("article.vcard .contact .icon-printer").text());
        contact.setPerson(doc.select("article.vcard .address .icon-user").text());
        contact.setWeb(doc.select("article.vcard .name .website").text());

        Address address = new Address();
        address.setCity(doc.select("article.vcard .address .place").text());
        address.setStreet(doc.select("article.vcard .address .place").text());

        CompanyTypes companyTypes = new CompanyTypes();
        Collections.addAll(companyTypes.getType(), doc.select(".supplier-type-indicator .active").text());

        company.setContact(contact);
        company.setAddress(address);
        company.setTypes(companyTypes);

        System.out.printf(company.getName());
        return company;
    }

    private static Elements getCompanyElementsForProduct(String url) throws Exception {
        System.out.println("Opening " + url);
        Document doc = Jsoup.connect(url).get();
        System.out.println("Parsing Product ...");
        Elements productTitle = doc.select("#heading-definition h4");
        Elements productDescription = doc.select("#heading-definition .tiny-text");
        Product p = new Product();
        p.setName(productTitle.text());
        p.setDescription(productDescription.text());
        Products products = new Products();
        products.getProduct().add(p);
        System.out.printf(p.getName() + " - " + p.getDescription());

        productDao.persist(products);
        return doc.select("#company-list .company-info");
    }
}
