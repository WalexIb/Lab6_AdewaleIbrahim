package com.ai.lab6.week13;

// Import necessary classes
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles the persistent storage of lotto results into the MySQL database.
 * The save operation is synchronized to ensure thread safety during database writes.
 */
public class DatabaseManager {

    /**
     * Public, zero-argument constructor required for successful instantiation in MainDriver.
     */
    public DatabaseManager() { 
        // No required initialization logic
    }

    /**
     * Saves a single lotto run result to the lotto_results table.
     * * @param runNumber The sequence number of the lotto run (1 to 5).
     * @param numbers The comma-separated string of the winning numbers.
     */
    public synchronized void saveLottoRun(int runNumber, String numbers) {
        // Use PreparedStatement to prevent SQL injection and insert into the table
        String sql = "INSERT INTO lotto_results (run_number, numbers, date_created) VALUES (?, ?, NOW())";

        // Attempt to get a connection instance from the static Database utility
        Connection conn = Database.getDBConnection(); 

        // Check if the connection attempt failed (e.g., server is offline)
        if (conn == null) {
            System.err.println("Error saving run " + runNumber + " to MySQL: Could not create connection to database server.");
            return; // Exit the method if connection failed
        }

        // Use try-with-resources (conn and PreparedStatement) for automatic resource closure
        try (conn; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            pstmt.setInt(1, runNumber);     
            pstmt.setString(2, numbers);    
            pstmt.executeUpdate(); // Execute the insert statement
            
            System.out.println("DB Save SUCCESS: Run " + runNumber + " results saved.");

        } catch (SQLException e) {
            // Catch errors during SQL execution (e.g., table structure issue)
            System.err.println("Error saving run " + runNumber + " to MySQL: " + e.getMessage());
        }
    }
}

//// DatabaseManager class to handle database operations
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//// DatabaseManager class to handle database operations
//public class DatabaseManager {
//
//    public DatabaseManager() {}
//
//    public synchronized void saveLottoRun(int runNumber, String numbers) {
//        String sql = "INSERT INTO lotto_results (run_number, numbers, date_created) VALUES (?, ?, NOW())";
//
//        // Step 1: Get database connection
//        Connection conn = Database.getDBConnection(); 
//
//        // Step 2: Check if connection is null
//        if (conn == null) {
//            System.err.println("Error saving run " + runNumber + " to MySQL: Could not create connection to database server.");
//            return; // Exit the method cleanly
//        }
//
//        // Step 3: Use try-with-resources for PreparedStatement only
//        try (conn; 
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, runNumber);     
//            pstmt.setString(2, numbers);    
//            pstmt.executeUpdate();
//            
//            // Log success message
//            System.out.println("DB Save SUCCESS: Run " + runNumber + " results saved.");
//
//            // Connection will be closed automatically by try-with-resources
//        } catch (SQLException e) {
//            // This catches errors during the PreparedStatement setup or execution
//            System.err.println("Error saving run " + runNumber + " to MySQL: " + e.getMessage());
//        }
//    }
//}