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
        processInput();
        if (Model.FINAL_MESSAGE.equals(model.getMessage())) {
            view.printMessage(model.getMessage());
        } else {
            view.printInfoMessage(View.UNCOMPLETED_MESSAGE_MSG + " Incomplete message: " + model.getMessage());
        }
    }

    /**
     * Processing and initial validation the user console input.
     */
    private void     processInput() {
        Scanner scanner = new Scanner(System.in);
        String  inputResponse;

        System.out.println("Please, transmit the message [Hello world!]:");
        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input.matches("[a-zA-Z]{5}!?")) {
                inputResponse = model.validateInput(input);
            } else {
                inputResponse = View.WRONG_INPUT_MSG;
            }
            view.printInfoMessage(inputResponse);
        }
        scanner.close();
    }
}
