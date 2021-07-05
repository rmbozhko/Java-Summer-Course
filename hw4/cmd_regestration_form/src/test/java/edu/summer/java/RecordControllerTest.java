package edu.summer.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class RecordControllerTest {
    private static RecordController    controller;

    @BeforeAll
    public static void  init() {
        controller = new RecordController(new RecordView());
    }

    @Test
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
        Assertions.assertEquals(new RecordModel(name, surname, fatherName, nickname),
                                controller.makeRecord());
        System.setIn(sysInBackup);
    }

    @Test
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
        Assertions.assertEquals(new RecordModel(name, surname, fatherName, nickname),
                controller.makeRecord());
        System.setIn(sysInBackup);
    }

    @Test
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
        Assertions.assertEquals(new RecordModel(name, surname, fatherName, nickname),
                controller.makeRecord());
        System.setIn(sysInBackup);
    }
}
