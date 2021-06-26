package edu.summer.java;

public class Model {
    public static final int SECRET_NUMBER_UPPER_BOUND = 101;
    public static final int SECRET_NUMBER_LOWER_BOUND = 0;
    private final int       secretNumber;

    public Model() {
        this.secretNumber = (int)(Math.random() * 100);
    }

    boolean     checkIfCorrectGuess(int guessNumber) {
        return guessNumber == secretNumber;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public boolean validateGuessNumber(int guessNumber) {
        return (guessNumber >= Model.SECRET_NUMBER_LOWER_BOUND &&
            guessNumber < Model.SECRET_NUMBER_UPPER_BOUND);
    }
}
