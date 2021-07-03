package edu.summer.java;

import java.util.Scanner;
import java.util.function.Predicate;

public class RecordController {
    RecordModel     model;
    RecordView      view;

    public RecordController(RecordView view) {
        this.view = view;
    }

    public void processInput() {
        this.setModel(makeRecord());
        view.print(this.getModel());
    }

    public String   readInput(Scanner scanner, Predicate<String> condition) {
        String input = scanner.nextLine();
        while (!validateInput(scanner.nextLine(), condition) && scanner.hasNextLine()) {
            input = scanner.nextLine();
            view.print("Invalid input.Try again.");
        }
        return input;
    }

    public boolean validateInput(String input, Predicate<String> condition) {
        return condition.test(input);
    }

    public RecordModel  makeRecord() {
        Scanner scanner = new Scanner(System.in);
        String  nicknamePattern = "^[^0-9][^@#$%\\\\*\\\\^]+$";
        String  cyrrilicNamesPattern = "^[а-яієїґ\\'\\-]+$gmiu";

        String name = readInput(scanner, input -> input.matches(cyrrilicNamesPattern));
        String surname = readInput(scanner, input -> input.matches(cyrrilicNamesPattern));
        String fatherName = readInput(scanner, input -> input.matches(cyrrilicNamesPattern));
        String nickname = readInput(scanner, input -> input.matches(nicknamePattern));
        scanner.close();
        return new RecordModel(name, surname, fatherName, nickname);
    }

    public RecordModel getModel() {
        return model;
    }

    public void setModel(RecordModel model) {
        this.model = model;
    }
}
