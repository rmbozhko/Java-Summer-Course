package edu.summer.java;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.Scanner;

/**
 * Class RecordController is responsible for reading and primary validation the user data.
 * Contains references to:
 * @see RecordModel
 * @see RecordView
 * @author Roman Bozhko
 * @version 1.0
 */
public class RecordController {
    private RecordModel     model;
    private RecordView      view;
    private ResourceBundle  bundle;

    public RecordController(ResourceBundle bundle, RecordView view) {
        this.bundle = bundle;
        this.view = view;
    }

    private boolean inputIsValid(String input, Predicate<String> condition) {
        return condition.test(input);
    }

    /*
    * Scans console input until input is validated.
     */
    private String   readInput(Scanner scanner, String info, Predicate<String> condition) {
        view.print(info);
        String input = scanner.nextLine();
        while (true) {
            if (inputIsValid(input, condition)) {
                break;
            } else {
                view.println(bundle.getString("message.input.wrong"));
                input = scanner.nextLine();
            }
        }
        return input;
    }

    /**
     * Constructs RecordModel instances by reading and validating with regex user console input.
     * @return instance of RecordModel constructed by RecordModelBuilder
     * @see RecordModel
     * @see RecordModelBuilder#getRecord()
     */
    public RecordModel  makeRecord() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        RecordModelBuilder builder = new RecordModelBuilder();

        builder.setName(readInput(scanner,
                                    bundle.getString("message.input.form.name"),
                                    input -> input.matches(bundle.getString("regex.input.form.name"))));
        builder.setSurname(readInput(scanner,
                                    bundle.getString("message.input.form.surname"),
                                    input -> input.matches(bundle.getString("regex.input.form.name"))));
        builder.setFatherName(readInput(scanner,
                                        bundle.getString("message.input.form.fatherName"),
                                        input -> input.matches(bundle.getString("regex.input.form.name"))));
        builder.setNickname(readInput(scanner,
                                    bundle.getString("message.input.form.nickname"),
                                    input -> input.matches(bundle.getString("regex.input.form.nickname"))));
        scanner.close();
        return builder.getRecord();
    }

    /**
     * Sets reference to the created RecordModel and prints out its content.
     * @see #makeRecord()
     * @see RecordView#println(RecordModel)
     */
    public void processInput() {
        this.setModel(makeRecord());
        view.println(this.getModel());
    }

    public RecordModel getModel() {
        return model;
    }

    public void setModel(RecordModel model) {
        this.model = model;
    }
}
