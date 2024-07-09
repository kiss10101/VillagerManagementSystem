package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String PROPERTIES_FILE = "application.properties";
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            props.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading " + PROPERTIES_FILE, ex);
        }

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error connecting to database", ex);
            throw ex;
        }
    }
}
