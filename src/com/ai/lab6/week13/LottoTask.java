package com.ai.lab6.week13;

import java.util.List;
import java.util.stream.Collectors;

public class LottoTask implements Runnable {

    private final int runNumber;
    private final DatabaseManager dbManager;
    private final MainDriver mainApp; 
    private final LottoGenerator generator = new LottoGenerator();

    public LottoTask(int runNumber, DatabaseManager dbManager, MainDriver mainApp) {
        this.runNumber = runNumber;
        this.dbManager = dbManager;
        this.mainApp = mainApp;
    }

    @Override
    public void run() {
        try {
            List<Integer> numbers = generator.generateLottoNumbers();
            String numberString = numbers.stream()
                                         .map(String::valueOf)
                                         .collect(Collectors.joining(", "));
            
            dbManager.saveLottoRun(runNumber, numberString);

            mainApp.updateLastResult(numberString); 
            
        } catch (Exception e) {
            System.err.println("Error in LottoTask run " + runNumber + ": " + e.getMessage());
        }
    }
}