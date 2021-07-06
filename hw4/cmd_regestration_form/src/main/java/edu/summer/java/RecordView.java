package edu.summer.java;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Class RecordView contains methods used for user informing:
 * - invalid input
 * - general info
 * - RecordModel content
 * @see RecordModel
 * @author Roman Bozhko
 * @version 1.0
 */
public class   RecordView {

    /**
     * Prints info with newline at certain flow stages.
     * @param input general flow info
     */
    public void println(String input) {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ps.println(input);
    }

    /**
     * Prints content of RecordModel instance.
     * @param model instance of RecordModel
     * @see RecordModel
     */
    public void println(RecordModel model) {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ps.println(model);
    }

    /**
     * Prints info at certain flow stages.
     * @param input general flow info
     */
    public void print(String input) {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ps.print(input);
    }
}
