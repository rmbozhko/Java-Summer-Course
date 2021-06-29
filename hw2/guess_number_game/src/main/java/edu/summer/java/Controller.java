package edu.summer.java;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Controller is responsible for primary user data validation and running the program core method.
 * Contains references to Model and View.
 */
public class Controller {
    private final Scanner         scanner;
    private final Model           model;
    private final View            view;

    public Controller(Model model, View view) {
        scanner = new Scanner(System.in);
        this.model = model;
        this.view = view;
    }

    /**
     * Reads user input assuming integer should be provided.
     * @return integer user input
     * @throws InputMismatchException if user input doesn't match Integer pattern
     */
    public int getUserConsoleInput() throws InputMismatchException {
        return scanner.nextInt();
    }

    /**
     * Checks if provided parameter is within secret number bounds.
     * @param guessNumber potentially correct user data
     * @return result of within-bounds check
     */
    public boolean validateGuessNumber(int guessNumber) {
        return (guessNumber >= SecretNumberInitialBounds.SECRET_NUMBER_LOWER_BOUND &&
                guessNumber < SecretNumberInitialBounds.SECRET_NUMBER_UPPER_BOUND);
    }

    /**
     * Core method of program.
     * Calls @see Model for data comparison and further input validation.
     * Calls @see View in case of game start, invalid input errors, steps hints, winning or loosing information.
     */
    public void runApp() {
        int     input;
        boolean guessedSecretNumber = false;

        view.print(view.getInitialInformation(SecretNumberInitialBounds.SECRET_NUMBER_LOWER_BOUND,
                        SecretNumberInitialBounds.SECRET_NUMBER_UPPER_BOUND));
        while (!guessedSecretNumber && scanner.hasNext()) {
            try {
                input = getUserConsoleInput();
                if (validateGuessNumber(input)) {
                    if (model.checkIfCorrectGuess(input)) {
                        guessedSecretNumber = true;
                        view.print(view.getWinnerInformation(model.getGuessesHistory(),
                                                                    model.getSecretNumber()));
                    } else {
                        model.updateBounds(input);
                        view.print(view.getInformationBeforeGuess(model.getLowerBound(), model.getUpperBound()));
                    }
                } else {
                    view.print(View.INPUT_OUT_OF_BOUNDS_MSG);
                }
            } catch (InputMismatchException e) {
                view.print(View.INVALID_PATTERN_MSG);
                scanner.next();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        view.print((guessedSecretNumber) ?
            view.getGameEndInformation() : view.getLooserInformation());
    }
}
