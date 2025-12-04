package com.ai.lab6.week13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    public DatabaseManager() {}

    public synchronized void saveLottoRun(int runNumber, String numbers) {
        String sql = "INSERT INTO lotto_results (run_number, numbers, date_created) VALUES (?, ?, NOW())";

        // Step 1: Get Connection outside of the primary try-with-resources block
        Connection conn = Database.getDBConnection(); 

        // Step 2: Now the 'null' check is reachable and necessary
        if (conn == null) {
            System.err.println("Error saving run " + runNumber + " to MySQL: Could not create connection to database server.");
            return; // Exit the method cleanly
        }

        // Step 3: Use the already-established connection in the try-with-resources block
        // The block is now simplified, and the connection will be automatically closed.
        try (conn; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, runNumber);     
            pstmt.setString(2, numbers);    
            pstmt.executeUpdate();
            
            System.out.println("DB Save SUCCESS: Run " + runNumber + " results saved.");

        } catch (SQLException e) {
            // This catches errors during the PreparedStatement setup or execution
            System.err.println("Error saving run " + runNumber + " to MySQL: " + e.getMessage());
        }
    }
}