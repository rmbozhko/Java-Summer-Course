package edu.summer.java;

/**
 * View class - a part of MVC pattern.
 * Contains methods and constants responsible for informing the user.
 */
public class View {
    public static final String CORRECT_INPUT_MSG = "Received correct word.";
    public static final String WRONG_INPUT_MSG = "Received invalid word.";
    public static final String ALREADY_READ_TOKEN_MSG = "Word is already read.";

    /**
     * Prints the message if it is fully read from user input
     * @param message a message to be printed
     */
    public void     printMessage(String message) {
        System.out.println("Final message:\n" + message);
    }

    /**
     * Prints a message as a response for token value.
     * @param message message with additional info
     * @param token token value
     */
    public void     printInfoMessage(String message, String token) {
        System.err.println(message + " Token: " + token);
    }

    /**
     * Prints a message how to stop inputting data if final message is already read.
     */
    public void     printExitMessage() {
        System.out.println("The message is read completely.\nTo exit the inputting enter Ctrl-D.");
    }
}
