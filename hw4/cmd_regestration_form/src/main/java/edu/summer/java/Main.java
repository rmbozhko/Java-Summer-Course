package edu.summer.java;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Program entry point.
 * @author Roman Bozhko
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        new RecordController(ResourceBundle.getBundle("constants", new Locale(args[0])),
                            new RecordView()).processInput();
    }
}
