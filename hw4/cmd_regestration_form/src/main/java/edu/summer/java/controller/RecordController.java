package edu.summer.java.controller;

import edu.summer.java.NotUniqueDataException;
import edu.summer.java.model.RecordModel;
import edu.summer.java.model.RecordModelBuilder;
import edu.summer.java.view.RecordView;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

/**
 * Class RecordController is responsible for reading and primary validation the user data.
 * Contains references to:
 * @see RecordModel
 * @see RecordView
 * @author Roman Bozhko
 * @version 1.0
 */
public class RecordController {
    private final List<RecordModel> records;
    private final RecordView        view;
    private final ResourceBundle    bundle;

    public RecordController(ResourceBundle bundle, RecordView view) {
        this.bundle = bundle;
        this.view = view;
        records = new ArrayList<>();
    }

    private boolean inputIsValid(String input, Predicate<String> condition) {
        return condition.test(input);
    }

    /*
    * Scans console input until input is validated with regard to passed condition.
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
    public RecordModel  makeRecord(Scanner scanner) {
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
        builder.setNickname(getUniqueData(scanner,
                                        bundle.getString("message.input.form.nickname"),
                                        input -> input.matches(bundle.getString("regex.input.form.nickname"))));
        return builder.getRecord();
    }

    /**
     * Retrieves user unique data for specified fields. In case of duplicates requires data re-entry.
     * @param scanner instance of Scanner class
     * @param info used to inform user about the data needed to be entered
     * @param condition used to check if passed data is valid
     * @return validate unique user data
     * @see Scanner
     * @see Predicate
     */
    private String  getUniqueData(Scanner scanner, String info, Predicate<String> condition) {
        while (true) {
            String data = readInput(scanner, info, condition);
            Optional<String> duplicateNickname = records.stream()
                    .map(RecordModel::getNickname)
                    .filter(elem -> elem.equals(data))
                    .findFirst();
            try {
                if (duplicateNickname.isPresent()) {
                    throw new NotUniqueDataException("Not unique data encountered", data);
                } else {
                    return data;
                }
            } catch (NotUniqueDataException e) {
                System.out.println(e.getMessage() + " " + e.getLoginData());
            }
        }
    }

    /**
     * Sets reference to the created RecordModel and prints out its content.
     * @see #makeRecord(Scanner)
     * @see RecordView#println(List)
     */
    public void processInput() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        while (true) {
            RecordModel record = makeRecord(scanner);
            records.add(record);
            view.println("Do you want to continue?[y/n]");
            if (scanner.hasNext("n")) {
                break;
            }
            scanner.nextLine();
        }
        view.println(this.getRecords());
        scanner.close();
    }

    public List<RecordModel> getRecords() {
        return records;
    }

}
