package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "application.properties";
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            if (input == null) {
                System.err.println("Sorry, unable to find " + PROPERTIES_FILE);
            } else {
                prop.load(input);
                dbUrl = prop.getProperty("db.url");
                dbUsername = prop.getProperty("db.username");
                dbPassword = prop.getProperty("db.password");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
