package de.stetro.matin.dbw.prak2.daos;


import de.stetro.matin.dbw.prak2.entities.company.*;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

/**
 * CompanyDao - does databaseaccess for company object
 *
 * @author stetro
 */
public class CompanyDao implements Dao<Companies> {

    private final Connection connection;
    private Statement statement;

    public CompanyDao() throws Exception {
        try {
            connection = DatabaseInterface.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists company");
            statement.executeUpdate("create table company (id INTEGER PRIMARY KEY AUTOINCREMENT, name string," +
                    "contactPhone string, contactFax string, contactPerson string, contactWeb string," +
                    "addressStreet string, addressCity string, type string )");
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in creating the product table.");
        }
    }

    @Override
    public Companies getAll() throws Exception {
        try {
            statement = connection.createStatement();
            String sql = "select * from company";
            ResultSet rs = statement.executeQuery(sql);
            Companies companies = new Companies();
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));

                Contact contact = new Contact();
                contact.setPhone(rs.getString("contactPhone"));
                contact.setFax(rs.getString("contactFax"));
                contact.setPerson(rs.getString("contactPerson"));
                contact.setWeb(rs.getString("contactWeb"));

                Address address = new Address();
                address.setCity(rs.getString("addressCity"));
                address.setStreet(rs.getString("addressStreet"));

                CompanyTypes companyTypes = new CompanyTypes();
                Collections.addAll(companyTypes.getType(), rs.getString("type").split(", "));

                company.setContact(contact);
                company.setAddress(address);
                company.setTypes(companyTypes);
                companies.getCompany().add(company);
            }
            statement.close();
            return companies;
        } catch (SQLException e) {
            throw new Exception("Error in get all products.");
        }
    }

    @Override
    public void persist(Companies object) throws Exception {
        try {
            statement = connection.createStatement();
            String sql = getInsertSqlStatement(object);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in persist product.");
        }
    }

    @Override
    public String getInsertSqlStatement(Companies companies) {
        String statement = "";
        for (Company c : companies.getCompany()) {
            Address address = c.getAddress();
            Contact contact = c.getContact();
            CompanyTypes companyTypes = c.getTypes();
            String types = StringUtils.join(companyTypes.getType(), ", ");
            statement = statement + "insert into company values(NULL, '" + c.getName() + "','" + contact.getPhone() + "', '" + contact.getFax() + "', '" + contact.getPerson() + "', '" + contact.getWeb() + "','" + address.getStreet() + "', '" + address.getCity() + "', '" + types + "' )";
        }
        return statement;
    }


}
