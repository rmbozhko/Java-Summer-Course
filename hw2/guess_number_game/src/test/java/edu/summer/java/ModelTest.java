package edu.summer.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ModelTest {
    private static Model model;

    @BeforeAll
    public static void      init() {
        model = new Model();
    }

    @Test
    void checkCorrectGuessedNumber() {
        Assertions.assertTrue(model.checkIfCorrectGuess(model.getSecretNumber()));
    }

    @Test
    void checkIncorrectGuessedNumber() {
        Assertions.assertFalse(model.checkIfCorrectGuess(model.getSecretNumber() + 1));
    }

    @Test
    void checkIfSecretNumberWithinRange() {
        Assertions.assertTrue(model.getSecretNumber() >= 0 && model.getSecretNumber() <= 100);
    }

    @Test
    void checkIfGuessedNumberWithinRange() {
        int guessNumber = (int)(Math.random() * Model.SECRET_NUMBER_UPPER_BOUND) + Model.SECRET_NUMBER_LOWER_BOUND;
        Assertions.assertTrue(model.validateGuessNumber(guessNumber));
    }

    @Test
    void checkIfGuessedNumberOutOfRange() {
        // way to get out of range may not be universal
        int guessNumber = (int)(Math.random() * Math.pow(Model.SECRET_NUMBER_UPPER_BOUND, 2.0)) + Model.SECRET_NUMBER_LOWER_BOUND;
        Assertions.assertFalse(model.validateGuessNumber(guessNumber));
    }
}
