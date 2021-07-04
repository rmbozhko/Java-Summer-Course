package edu.summer.java;

import java.nio.charset.StandardCharsets;
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

    public RecordController(RecordView view) {
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
                view.println("Invalid input.Try again.");
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
        String  nicknamePattern = "^[^0-9][^@#$%\\\\*]+$";
        String  cyrrilicNamesPattern = "[\\p{L}'-]+";
        RecordModelBuilder builder = new RecordModelBuilder();

        builder.setName(readInput(scanner, "Ім'я: ", input -> input.matches(cyrrilicNamesPattern)));
        builder.setSurname(readInput(scanner, "Призвіще: ", input -> input.matches(cyrrilicNamesPattern)));
        builder.setFatherName(readInput(scanner, "По-батькові: ", input -> input.matches(cyrrilicNamesPattern)));
        builder.setNickname(readInput(scanner, "Нікнейм: ", input -> input.matches(nicknamePattern)));
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
