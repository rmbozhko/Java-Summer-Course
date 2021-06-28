package edu.summer.java;

import java.util.List;

public class View {
    public static final String  INVALID_PATTERN_MSG = "Retrieved token does not match the integer pattern.";
    public static final String  INPUT_OUT_OF_BOUNDS_MSG = "Retrieved token is out of secret number bounds.";

    public String getInitialInformation(int lowerBound, int upperBound) {
        if (upperBound > lowerBound) {
            return String.format("I have secret number, which is within %d and %d. Try to guess it.",
                                    lowerBound, upperBound);
        } else if (upperBound < lowerBound){
            return "Initial Information Error: Upper bound is less than lower bound.";
        } else {
            return "Initial Information Error: Upper bound equals lower bound.";
        }
    }

    public String getInformationBeforeGuess(int lowerBound, int upperBound) {
        if (lowerBound < upperBound) {
            return String.format("The secret number is between %d and %d", lowerBound, upperBound);
        } else if (upperBound < lowerBound){
            return "Guess Information Error: Upper bound is less than lower bound.";
        } else {
            return "Guess Information Error: Upper bound equals lower bound.";
        }
    }

    public String getWinnerInformation(List<Integer> guessesHistory, int secretNumber) {
        return String.format("Congrats! You guessed the secret number %d. It took %d " +
                ((guessesHistory.size() == 1) ? "try." : "tries.") +
                "\nTries: " + guessesHistory, secretNumber, guessesHistory.size());
    }

    public String getGameEndInformation() {
        return "The game is finished. See you next time.";
    }

    public String getLooserInformation() {
        return "Unfortunately there wasn't secret number among provided data. You will have better luck next time.";
    }
}
