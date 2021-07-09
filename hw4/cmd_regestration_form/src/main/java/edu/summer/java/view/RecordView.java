package edu.summer.java.view;

import edu.summer.java.model.RecordModel;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class RecordView contains methods used for user informing.
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
     * @param records instance of RecordModel
     * @see RecordModel
     */
    public void println(List<RecordModel> records) {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        records.forEach(ps::println);
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
