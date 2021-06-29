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
    void checkIfGuessedNumberWithinRange() {
        int guessNumber = (int)(Math.random() * SecretNumberInitialBounds.SECRET_NUMBER_UPPER_BOUND) +
                                                SecretNumberInitialBounds.SECRET_NUMBER_LOWER_BOUND;
        Assertions.assertTrue(controller.validateGuessNumber(guessNumber));
    }

    @Test
    void checkIfGuessedNumberOutOfRange() {
        // way to get out of range may not be universal
        int guessNumber = (int)(Math.random() *
                                Math.pow(SecretNumberInitialBounds.SECRET_NUMBER_UPPER_BOUND, 2.0)) +
                                        SecretNumberInitialBounds.SECRET_NUMBER_LOWER_BOUND;
        Assertions.assertFalse(controller.validateGuessNumber(guessNumber));
    }
}
