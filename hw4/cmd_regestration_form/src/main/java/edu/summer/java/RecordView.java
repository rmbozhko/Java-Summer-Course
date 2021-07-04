package edu.summer.java;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class RecordView {
    public void println(String input) {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ps.println(input);
    }

    public void println(RecordModel model) {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ps.println(model);
    }

    public void print(String input) {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ps.print(input);
    }
}
