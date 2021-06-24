package edu.summer.java;

/**
 * View class - a part of MVC pattern.
 * Contains methods and constants responsible for informing the user.
 */
public class View {
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
     * Prints an message if error in input is encountered
     * @param errMessage type of error message
     * @param invalidToken invalid token value
     */
    public void     printErrorMessage(String errMessage, String invalidToken) {
        System.err.println(errMessage + " Token: " + invalidToken);
    }
}
