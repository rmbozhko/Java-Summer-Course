package edu.summer.java;

import edu.summer.java.controller.Controller;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class with program entry point.
 * @author Roman Bozhko
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        new Controller(ResourceBundle.getBundle("constants", new Locale(args[0]))).runApp();
    }
}
