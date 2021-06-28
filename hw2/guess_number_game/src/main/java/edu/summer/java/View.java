package edu.summer.java;

import java.util.List;

/**
 * Class View contains constants and methods used for user informing on every stage of game flow.
 */
public class View {
    /**
     * Constants containing error message for distinct cases.
     */
    public static final String  INVALID_PATTERN_MSG = "Retrieved token does not match the integer pattern.";
    public static final String  INPUT_OUT_OF_BOUNDS_MSG = "Retrieved token is out of secret number bounds.";

    /**
     * Is invoked before start of the game to provide user with bounds where to search for secret number.
     * @param lowerBound the lowest possible number
     * @param upperBound the biggest possible number
     * @return hint message unless bounds are misplaced
     */
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

    /**
     * Is invoked before each guess to provide user with bounds where to search for secret number.
     * @param lowerBound the lowest possible number
     * @param upperBound the biggest possible number
     * @return hint message unless bounds are misplaced
     */
    public String getInformationBeforeGuess(int lowerBound, int upperBound) {
        if (lowerBound < upperBound) {
            return String.format("The secret number is between %d and %d", lowerBound, upperBound);
        } else if (upperBound < lowerBound){
            return "Guess Information Error: Upper bound is less than lower bound.";
        } else {
            return "Guess Information Error: Upper bound equals lower bound.";
        }
    }

    /**
     * Is invoked when the secret number is guessed.
     * @param guessesHistory contains history of valid user guesses
     * @param secretNumber is a randomly-generated integer
     * @return message of congratulations when user enters data which equals to the secretNumber
     */
    public String getWinnerInformation(List<Integer> guessesHistory, int secretNumber) {
        return String.format("Congrats! You guessed the secret number %d. It took %d " +
                ((guessesHistory.size() == 1) ? "try." : "tries.") +
                "\nTries: " + guessesHistory, secretNumber, guessesHistory.size());
    }

    /**
     * Is invoked at the end of program execution flow if the secret number is already guessed.
     * @return message about game ending in case of success
     */
    public String getGameEndInformation() {
        return "The game is finished. See you next time.";
    }

    /**
     * Is invoked when there is no more user data but secret number isn't still guessed.
     * @return message about an unsuccessful game ending
     */
    public String getLooserInformation() {
        return "Unfortunately, there wasn't secret number among provided data. You will have better luck next time.";
    }
}
