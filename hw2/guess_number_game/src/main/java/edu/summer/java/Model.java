package edu.summer.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Model represents the randomly-generated secret number which is an objective to find in this game.
 * It contains guesses history which stores all valid user guesses.
 */
public class Model {
    public static final int SECRET_NUMBER_UPPER_BOUND = 101;
    public static final int SECRET_NUMBER_LOWER_BOUND = 0;
    private final int       secretNumber;
    private List<Integer>   guessesHistory;

    public Model() {
        this.secretNumber = (int)(Math.random() * 100);
        guessesHistory = new ArrayList<>();
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public List<Integer> getGuessesHistory() {
        return guessesHistory;
    }

    /**
     * Checks if provided parameter equals to secretNumber and stores it into guessesHistory for statistics.
     * @param guessNumber validated user data
     * @return result of values comparison
     */
    boolean     checkIfCorrectGuess(int guessNumber) {
        guessesHistory.add(guessNumber);
        return guessNumber == secretNumber;
    }

    /**
     * Checks if provided parameter is within secret number bounds.
     * @param guessNumber potentially correct user data
     * @return result of within-bounds check
     */
    public boolean validateGuessNumber(int guessNumber) {
        return (guessNumber >= Model.SECRET_NUMBER_LOWER_BOUND &&
            guessNumber < Model.SECRET_NUMBER_UPPER_BOUND);
    }
}
