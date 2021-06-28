package edu.summer.java;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Controller is responsible for primary user data validation and running the program core method.
 * Contains references to Model and View.
 */
public class Controller {
    Scanner         scanner;
    Model           model;
    View            view;
    private int     lowerBound;
    private int     upperBound;

    public Controller(Model model, View view) {
        this.lowerBound = Model.SECRET_NUMBER_LOWER_BOUND;
        this.upperBound = Model.SECRET_NUMBER_UPPER_BOUND - 1;
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
        if (bound > model.getSecretNumber() && bound < upperBound) {
            upperBound = bound;
        } else if (bound < model.getSecretNumber() && bound > lowerBound) {
            lowerBound = bound;
        }
    }

    /**
     * Core method of program.
     * Calls @see Model for data comparison and further input validation.
     * Calls @see View in case of game start, invalid input errors, steps hints, winning or loosing information.
     */
    public void runApp() {
        int     input = -1;
        boolean guessedSecretNumber = false;

        view.getInitialInformation(Model.SECRET_NUMBER_LOWER_BOUND, Model.SECRET_NUMBER_UPPER_BOUND - 1);
        while (!guessedSecretNumber && scanner.hasNext()) {
            try {
                input = getUserConsoleInput();
                if (model.validateGuessNumber(input)) {
                    if (model.checkIfCorrectGuess(input)) {
                        guessedSecretNumber = true;
                        System.out.println(view.getWinnerInformation(model.getGuessesHistory(),
                                                                    model.getSecretNumber()));
                    } else {
                        updateBounds(input);
                        System.out.println(view.getInformationBeforeGuess(getLowerBound(), getUpperBound()));
                    }
                } else {
                    System.err.println(View.INPUT_OUT_OF_BOUNDS_MSG);
                }
            } catch (InputMismatchException e) {
                System.err.println(View.INVALID_PATTERN_MSG);
                scanner.next();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        System.out.println((guessedSecretNumber) ?
            view.getGameEndInformation() : view.getLooserInformation());
    }
}
