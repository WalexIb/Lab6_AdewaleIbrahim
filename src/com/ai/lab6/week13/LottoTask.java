package com.ai.lab6.week13;

// Import necessary classes
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Runnable task executed by a separate thread.
 * It handles generating lotto numbers, saving them to the database, and updating the GUI.
 */
public class LottoTask implements Runnable {

    private final int runNumber;
    private final DatabaseManager dbManager;
    private final MainDriver mainApp; 
    private final LottoGenerator generator = new LottoGenerator();

    // Fields to hold user input parameters
    private final int minNum;
    private final int maxNum;
    private final int count;

    /**
     * Constructor for the LottoTask.
     */
    public LottoTask(int runNumber, DatabaseManager dbManager, MainDriver mainApp, 
                     int minNum, int maxNum, int count) {
        this.runNumber = runNumber;
        this.dbManager = dbManager;
        this.mainApp = mainApp;
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            // 1. Generate the lotto numbers using user parameters
            List<Integer> numbers = generator.generateLottoNumbers(minNum, maxNum, count);
            
            // 2. Format the list of numbers into a comma-separated string for DB storage
            String numberString = numbers.stream()
                                         .map(String::valueOf)
                                         .collect(Collectors.joining(", "));
            
            // 3. Save the formatted result to the database
            dbManager.saveLottoRun(runNumber, numberString);

            // 4. Update the GUI safely
            mainApp.updateLastResult("Run " + runNumber + ": Your lotto numbers are: " + numberString); 
            
        } catch (IllegalArgumentException e) { 
            // Catches errors from NumberFormatException (a subclass) and generator's validation checks
             mainApp.updateLastResult("Task " + runNumber + " Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error in LottoTask run " + runNumber + ": " + e.getMessage());
        }
    }
}

