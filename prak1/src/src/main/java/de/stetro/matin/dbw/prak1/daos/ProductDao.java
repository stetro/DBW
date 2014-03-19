package de.stetro.matin.dbw.prak1.daos;

import de.stetro.matin.dbw.prak1.entities.products.Product;
import de.stetro.matin.dbw.prak1.entities.products.Products;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDao {
    private Statement statement;
    private Connection connection;

    public ProductDao() throws Exception {
        try {
            connection = DatabaseInterface.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists product");
            statement.executeUpdate("create table product (id integer, name string, buildtime string, price string)");
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in creating the product table.");
        }
    }

    public Products getAllProducts() throws Exception {
        try {
            statement = connection.createStatement();
            String sql = "select * from product";
            ResultSet rs = statement.executeQuery(sql);
            Products products = new Products();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setBuildtime(rs.getString("buildtime"));
                product.setPrice(rs.getString("price"));
                products.getProduct().add(product);
            }
            statement.close();
            return products;
        } catch (SQLException e) {
            throw new Exception("Error in get all products.");
        }
    }

    public Product getProduct(int id) throws Exception {
        try {
            statement = connection.createStatement();
            String sql = "select * from product where id = " + id;
            ResultSet rs = statement.executeQuery(sql);
            Product product = new Product();
            product.setId(id);
            if (rs.next()) {
                product.setName(rs.getString("name"));
                product.setBuildtime(rs.getString("buildtime"));
                product.setPrice(rs.getString("price"));
            }
            statement.close();
            return product;
        } catch (SQLException e) {
            throw new Exception("Error in get product.");
        }
    }

    public void createProduct(Product product) throws Exception {
        try {
            statement = connection.createStatement();
            String sql = getCreateSqlStatement(product);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in create product.");
        }
    }

    public String getCreateSqlStatement(Product product) {
        return "insert into product values(\n\t" + product.getId() + ", \n\t'"
                + product.getName() + "', \n\t'" + product.getBuildtime() + "', \n\t'" + product.getPrice() + "')";
    }
}
