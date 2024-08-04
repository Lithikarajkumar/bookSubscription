package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; // Adjust the URL as needed
        String username = "SYSTEM"; // Your Oracle database username
        String password = "system"; // Your Oracle database password

        // Establish and return the connection
        return DriverManager.getConnection(url, username, password);
    }

    // Main method for testing the connection
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            System.out.println("Connection successful!");
            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
