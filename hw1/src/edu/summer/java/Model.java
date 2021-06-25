package edu.summer.java;

/**
 * Model class - a part of MVC pattern.
 * Contains methods and representation of message.
 */
public class Model {
    public static final String FINAL_MESSAGE = "Hello world!";
    private String  message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Validates user data due to technical requirements.
     * @param input user data
     * @return a message as a response for the retrieved data
     */
    public String validateInput(String input) {
        if (input.equals("Hello")) {
            if (!message.contains("Hello")) {
                message += input + " ";
                return View.CORRECT_INPUT_MSG;
            } else {
                return View.ALREADY_READ_TOKEN_MSG;
            }
        } else if (input.equals("world!") && message.contains("Hello ")) {
            if (!message.contains("world!")) {
                message += input;
                return View.CORRECT_INPUT_MSG;
            } else {
                return View.ALREADY_READ_TOKEN_MSG;
            }
        } else {
            if (message.equals("Hello world!")) {
                return View.READ_MESSAGE_FULLY_MSG;
            } else {
                return View.WRONG_INPUT_MSG;
            }
        }
    }
}
