package edu.summer.java;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final int SECRET_NUMBER_UPPER_BOUND = 101;
    public static final int SECRET_NUMBER_LOWER_BOUND = 0;
    private final int       secretNumber;
    private List<Integer>   guessesHistory;

    public Model() {
        this.secretNumber = (int)(Math.random() * 100);
        guessesHistory = new ArrayList<>();
    }

    boolean     checkIfCorrectGuess(int guessNumber) {
        guessesHistory.add(guessNumber);
        return guessNumber == secretNumber;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public boolean validateGuessNumber(int guessNumber) {
        return (guessNumber >= Model.SECRET_NUMBER_LOWER_BOUND &&
            guessNumber < Model.SECRET_NUMBER_UPPER_BOUND);
    }

    //!guessesHistory.contains(guessNumber)

    public List<Integer> getGuessesHistory() {
        return guessesHistory;
    }
}
