package com.ai.lab6.week13;

// LottoGenerator class to generate lotto numbers
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// LottoGenerator class to generate lotto numbers
public class LottoGenerator {

    private static final int COUNT = 6;
    private static final int MIN = 0;
    private static final int MAX = 100;

    public List<Integer> generateLottoNumbers() {
        // The conditional range check is removed as it was always false with constants, 
        // causing the "dead code" warning.
        
        SecureRandom secureRandom = new SecureRandom();
        List<Integer> availableNumbers = new ArrayList<>();
        
        for (int i = MIN; i <= MAX; i++) {
            availableNumbers.add(i);
        }
// Shuffle the list to ensure randomness
        Collections.shuffle(availableNumbers, secureRandom);

        List<Integer> selectedNumbers = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            selectedNumbers.add(availableNumbers.get(i));
        }
        
        // Sort the selected numbers before returning
        Collections.sort(selectedNumbers);
        return selectedNumbers;
    }
}