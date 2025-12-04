package com.ai.lab6.week13;

// Database connection utility class
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage and provide a database connection instance.
 * Uses a static method to ensure connection logic is centralized and reusable.
 */
public class Database {
	
	// --- Configuration Constants ---
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	// JDBC URL: uses the simplified, working string for the lottoDB schema
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/lottoDB"; 
	
	// Credentials for the dedicated application user
	private static final String DB_USER = "root"; 
	private static final String DB_PASSWORD = "Adewale$1"; 
	
	/**
	 * Private constructor to prevent the instantiation of this utility class.
	 */
	private Database() {
		// Private constructor to prevent direct instantiation
	}
	
	/**
	 * Establishes and returns a database connection.
	 * * @return A valid Connection object if successful, or null if connection fails.
	 */
	public static Connection getDBConnection() {
		Connection connection = null;

		try {
			// 1. Load the MySQL JDBC Driver class dynamically
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException ex) {
			// Print error if the MySQL Connector/J JAR is missing from the build path
			System.err.println("JDBC Driver not found: " + ex.getMessage());
			return null;
		}

		try {
			// 2. Establish connection using the configured URL, user, and password
			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return connection;
		} catch (SQLException ex) {
			// Print error if the connection fails (e.g., server down, incorrect credentials)
			System.err.println("Connection Failed! SQL State: " + ex.getSQLState());
			System.err.println("Error Message: " + ex.getMessage());
			return null;
		}
	}
}

