package edu.summer.java;

import java.util.Scanner;

/**
 * Controller class - a part of MVC pattern.
 * Contains methods and references to Model, View.
 * Responsible for running & validating an input.
 */

public class Controller {
    private final Model model;
    private final View  view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Running the process and displaying the message.
     */
    public void         runInputProcessing() {
        model.setMessage(processInput());
        view.printMessage(model.getMessage());
    }

    /**
     * Processing and validating the user input.
     * @return message to be printed as a result of successful completion
     */
    private String     processInput() {
        Scanner scanner = new Scanner(System.in);
        String message = "";

        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input.equals("Hello")) {
                if (!message.contains("Hello")) {
                    message += input + " ";
                } else {
                    view.printErrorMessage(View.ALREADY_READ_TOKEN_MSG, input);
                }
            } else if (input.equals("world!") && message.contains("Hello ")) {
                if (!message.contains("world!")) {
                    message += input;
                } else {
                    view.printErrorMessage(View.ALREADY_READ_TOKEN_MSG, input);
                }
            } else {
                view.printErrorMessage(View.WRONG_INPUT_MSG, input);
            }
        }
        scanner.close();
        return message;
    }
}
