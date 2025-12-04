package com.ai.lab6.week13;

// DatabaseManager class to handle database operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// DatabaseManager class to handle database operations
public class DatabaseManager {

    public DatabaseManager() {}

    public synchronized void saveLottoRun(int runNumber, String numbers) {
        String sql = "INSERT INTO lotto_results (run_number, numbers, date_created) VALUES (?, ?, NOW())";

        // Step 1: Get database connection
        Connection conn = Database.getDBConnection(); 

        // Step 2: Check if connection is null
        if (conn == null) {
            System.err.println("Error saving run " + runNumber + " to MySQL: Could not create connection to database server.");
            return; // Exit the method cleanly
        }

        // Step 3: Use try-with-resources for PreparedStatement only
        try (conn; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, runNumber);     
            pstmt.setString(2, numbers);    
            pstmt.executeUpdate();
            
            // Log success message
            System.out.println("DB Save SUCCESS: Run " + runNumber + " results saved.");

            // Connection will be closed automatically by try-with-resources
        } catch (SQLException e) {
            // This catches errors during the PreparedStatement setup or execution
            System.err.println("Error saving run " + runNumber + " to MySQL: " + e.getMessage());
        }
    }
}