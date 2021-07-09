package edu.summer.java;

import edu.summer.java.controller.RecordController;
import edu.summer.java.model.RecordModel;
import edu.summer.java.view.RecordView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class RecordControllerTest {
    private static RecordController controller;

    @BeforeAll
    public static void  init() {
        controller = new RecordController(ResourceBundle.getBundle("constants", new Locale("uk")), new RecordView());
    }

    @Test
    @Deprecated
    public void     checkEasyDataRecordCreation() {
        InputStream sysInBackup = System.in;
        String name = "Роман";
        String surname = "Божко";
        String fatherName = "В'ячеславович";
        String nickname = "zitran";

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        ByteArrayInputStream in = new ByteArrayInputStream((name + System.lineSeparator() +
                                                            surname + System.lineSeparator() +
                                                            fatherName + System.lineSeparator() +
                                                            nickname + System.lineSeparator())
                                                            .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
//        Assertions.assertEquals(new RecordModel(name, surname, fatherName, nickname),
//                                controller.makeRecord());
        System.setIn(sysInBackup);
    }

    @Test
    @Deprecated
    public void     checkMediumDataRecordCreation() {
        InputStream sysInBackup = System.in;
        String name = "Анна-Марія";
        String surname = "Дем'янівська";
        String fatherName = "Олегівна";
        String nickname = "zitran";

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        ByteArrayInputStream in = new ByteArrayInputStream((name + System.lineSeparator() +
                surname + System.lineSeparator() +
                fatherName + System.lineSeparator() +
                nickname + System.lineSeparator())
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
//        Assertions.assertEquals(new RecordModel(name, surname, fatherName, nickname),
//                controller.makeRecord());
        System.setIn(sysInBackup);
    }

    @Test
    @Deprecated
    public void     checkHardDataRecordCreation() {
        InputStream sysInBackup = System.in;
        String name = "Лук'ян";
        String surname = "Сербина-Полупан";
        String fatherName = "Валер'янович";
        String nickname = "zitran";

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        ByteArrayInputStream in = new ByteArrayInputStream((name + System.lineSeparator() +
                surname + System.lineSeparator() +
                fatherName + System.lineSeparator() +
                nickname + System.lineSeparator())
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
//        Assertions.assertEquals(new RecordModel(name, surname, fatherName, nickname),
//                controller.makeRecord());
        System.setIn(sysInBackup);
    }
}
