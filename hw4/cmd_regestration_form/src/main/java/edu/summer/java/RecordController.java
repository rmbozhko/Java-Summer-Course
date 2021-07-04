package edu.summer.java;

import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;
import java.util.Scanner;

public class RecordController {
    RecordModel     model;
    RecordView      view;

    public RecordController(RecordView view) {
        this.view = view;
    }

    public boolean inputIsValid(String input, Predicate<String> condition) {
        return condition.test(input);
    }

    public String   readInput(Scanner scanner, String info, Predicate<String> condition) {
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

    public RecordModel  makeRecord() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        String  nicknamePattern = "^[^0-9][^@#$%\\\\*]+$";
        String  cyrrilicNamesPattern = "[\\p{L}'-]+";

        String name = readInput(scanner, "Ім'я: ", input -> input.matches(cyrrilicNamesPattern));
        String surname = readInput(scanner, "Призвіще: ", input -> input.matches(cyrrilicNamesPattern));
        String fatherName = readInput(scanner, "По-батькові: ", input -> input.matches(cyrrilicNamesPattern));
        String nickname = readInput(scanner, "Нікнейм: ", input -> input.matches(nicknamePattern));
        scanner.close();
        return new RecordModel(name, surname, fatherName, nickname);
    }

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
