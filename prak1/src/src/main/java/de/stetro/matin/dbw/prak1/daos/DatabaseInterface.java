package de.stetro.matin.dbw.prak1.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Connection Singleton
 * @author stetro
 */
public class DatabaseInterface {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        }
        return connection;
    }
}
