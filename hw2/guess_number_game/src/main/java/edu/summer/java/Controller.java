package edu.summer.java;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
    Scanner         scanner;
    Model           model;
    View            view;
    private int     lowerBound;
    private int     upperBound;

    public Controller() {
        this.lowerBound = Model.SECRET_NUMBER_LOWER_BOUND;
        this.upperBound = Model.SECRET_NUMBER_UPPER_BOUND - 1;
        scanner = new Scanner(System.in);
        model = new Model();
        view = new View();
    }

    public int getUserConsoleInput() throws InputMismatchException {
        return scanner.nextInt();
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void updateBounds(int bound) {
        if (bound > model.getSecretNumber()) {
            upperBound = bound;
        } else {
            lowerBound = bound;
        }
    }

    public void run() {
        int     input = -1;

        view.getInitialInformation(Model.SECRET_NUMBER_LOWER_BOUND, Model.SECRET_NUMBER_UPPER_BOUND - 1);
        while (true) {
            try {
                input = getUserConsoleInput();
                if (model.validateGuessNumber(input)) {
                    if (model.checkIfCorrectGuess(input)) {
                        System.out.println(view.getWinnerInformation(model.getGuessesHistory(), model.getSecretNumber()));
                        break;
                    } else {
                        updateBounds(input);
                        System.out.println(view.getInformationBeforeGuess(getLowerBound(), getUpperBound()));
                    }
                } else {
                    System.err.println(View.INPUT_OUT_OF_BOUNDS_MSG);
                }
            } catch (InputMismatchException e) {
                System.err.println(View.INVALID_PATTERN_MSG);
            }
        }
        view.getGameEndInformation();
    }
}
