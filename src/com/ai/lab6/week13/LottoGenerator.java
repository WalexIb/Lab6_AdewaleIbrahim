package com.ai.lab6.week13;

// Import necessary classes
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generates a list of unique, securely random lottery numbers based on user-defined parameters.
 */
public class LottoGenerator {
    
    /**
     * Generates a set of unique lotto numbers within a specified range and count.
     * * @param minNum The minimum number in the range (inclusive).
     * @param maxNum The maximum number in the range (inclusive).
     * @param count The number of balls to select.
     * @return A sorted List of unique Integers representing the lotto result.
     * @throws IllegalArgumentException if the range is invalid or too small for the count.
     */
    public List<Integer> generateLottoNumbers(int minNum, int maxNum, int count) {
        
        // Robust check to ensure the input parameters are valid for generation
        if (count <= 0 || minNum > maxNum || (maxNum - minNum + 1) < count) {
            throw new IllegalArgumentException("Invalid input: Max-Min range is smaller than the requested ball count, or min/max are incorrect.");
        }

        SecureRandom secureRandom = new SecureRandom();
        List<Integer> availableNumbers = new ArrayList<>();
        
        // Populate the list with all possible numbers in the user-defined range
        for (int i = minNum; i <= maxNum; i++) {
            availableNumbers.add(i);
        }

        // Use Collections.shuffle with SecureRandom for cryptographic randomness
        Collections.shuffle(availableNumbers, secureRandom);

        List<Integer> selectedNumbers = new ArrayList<>();
        // Select 'count' number of balls from the shuffled list
        for (int i = 0; i < count; i++) { 
            selectedNumbers.add(availableNumbers.get(i));
        }
        
        // Sort the final result list for clean display and storage
        Collections.sort(selectedNumbers);
        return selectedNumbers;
    }
}

