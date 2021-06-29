package edu.summer.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Model represents the randomly-generated secret number which is an objective to find in this game.
 * It contains guesses history which stores all valid user guesses.
 */
public class Model {
    private final int           secretNumber;
    private final List<Integer> guessesHistory;
    private int                 lowerBound;
    private int                 upperBound;

    public Model() {
        lowerBound = SecretNumberInitialBounds.SECRET_NUMBER_LOWER_BOUND;
        upperBound = SecretNumberInitialBounds.SECRET_NUMBER_UPPER_BOUND;
        this.secretNumber = (int)(Math.random() * upperBound);
        guessesHistory = new ArrayList<>();
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public List<Integer> getGuessesHistory() {
        return guessesHistory;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    /**
     * Updates bounds with parameter depending which one is closer to provided.
     * @param bound is an update for upper- or lowerBound used with @see View#getInformationBeforeGuess()
     */
    public void updateBounds(int bound) {
        if (bound > getSecretNumber() && bound < upperBound) {
            upperBound = bound;
        } else if (bound < getSecretNumber() && bound > lowerBound) {
            lowerBound = bound;
        }
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
}
