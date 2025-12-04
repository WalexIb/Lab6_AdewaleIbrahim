package com.ai.lab6.week13;

// Database connection utility class
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	// Database credentials
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/lottoDB";
	private static final String DB_USER = "root"; // <-- USE YOUR CREATED USER
	private static final String DB_PASSWORD = "Adewale$1"; // <-- USE THE PASSWORD FOR THAT USER
	
	private Database() {
		// Private constructor to prevent instantiation
	}
	
	public static Connection getDBConnection() {
		Connection connection = null;

		try {
			// Load the JDBC Driver
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException ex) {
			System.err.println("JDBC Driver not found: " + ex.getMessage());
			return null;
		}

		try {
			// Establish the connection
			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return connection;
		} catch (SQLException ex) {
			System.err.println("Connection Failed! SQL State: " + ex.getSQLState());
			System.err.println("Error Message: " + ex.getMessage());
			
			// Re-throw or return null based on application needs
			return null;
		}
	}
}