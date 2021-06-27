package edu.summer.java;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

public class ControllerTest {
    private static Controller   controller;

    @BeforeAll
    public static void init() {
        controller = new Controller(new Model(), new View());
    }

    @Test
    @Disabled
    void checkUserConsoleInput() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("hello".getBytes());
        System.setIn(in);
        Assertions.assertThrows(InputMismatchException.class, () -> controller.getUserConsoleInput());
        System.setIn(sysInBackup);
    }

    @Test
    void checkUpdateLowerBound() {
        int newLowerBound = controller.model.getSecretNumber() - 3;
        controller.updateBounds(newLowerBound);
        Assertions.assertEquals(newLowerBound, controller.getLowerBound());
        Assertions.assertEquals(Model.SECRET_NUMBER_UPPER_BOUND - 1, controller.getUpperBound());
    }

    @Test
    void checkRepetitiveUpdateLowerBound() {
        int newLowerBound = controller.model.getSecretNumber() - 1;
        controller.updateBounds(newLowerBound);
        Assertions.assertEquals(newLowerBound, controller.getLowerBound());
        Assertions.assertEquals(Model.SECRET_NUMBER_UPPER_BOUND - 1, controller.getUpperBound());
    }

    @Test
    void checkUpdateUpperBound() {
        int newUpperBound = controller.model.getSecretNumber() + 3;
        int backupLowerBound = controller.getLowerBound();
        controller.updateBounds(newUpperBound);
        Assertions.assertEquals(newUpperBound, controller.getUpperBound());
        Assertions.assertEquals(backupLowerBound, controller.getLowerBound());
    }

    @Test
    void checkRepetitiveUpdateUpperBound() {
        int newUpperBound = controller.model.getSecretNumber() + 1;
        int backupLowerBound = controller.getLowerBound();
        controller.updateBounds(newUpperBound);
        Assertions.assertEquals(newUpperBound, controller.getUpperBound());
        Assertions.assertEquals(backupLowerBound, controller.getLowerBound());
    }
}
