package de.stetro.matin.dbw.prak2.daos;

import de.stetro.matin.dbw.prak2.entities.products.Product;
import de.stetro.matin.dbw.prak2.entities.products.Products;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ProductDao - does databaseaccess for product object
 *
 * @author stetro
 */
public class ProductDao implements Dao<Products> {
    private Statement statement;
    private Connection connection;

    public ProductDao() throws Exception {
        try {
            connection = DatabaseInterface.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists product");
            statement.executeUpdate("create table product (id INTEGER PRIMARY KEY AUTOINCREMENT, name string, description string)");
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in creating the product table.");
        }
    }

    public Products getAll() throws Exception {
        try {
            statement = connection.createStatement();
            String sql = "select * from product";
            ResultSet rs = statement.executeQuery(sql);
            Products products = new Products();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                products.getProduct().add(product);
            }
            statement.close();
            return products;
        } catch (SQLException e) {
            throw new Exception("Error in get all products.");
        }
    }

    public void persist(Products products) throws Exception {
        try {
            statement = connection.createStatement();
            String sql = getInsertSqlStatement(products);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in persist product.");
        }
    }

    public String getInsertSqlStatement(Products products) {
        String statement = "";
        for (Product p : products.getProduct()) {
            statement = statement + "insert into product values(NULL, \n\t'" + p.getName() +
                    "', \n\t'" + p.getDescription() +
                    "');";
        }
        return statement;
    }
}
