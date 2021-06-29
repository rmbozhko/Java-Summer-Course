package edu.summer.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
    void checkStoredGuessesHistory() {
        try {
            Field guessesHistory = model.getClass().getDeclaredField("guessesHistory");
            guessesHistory.setAccessible(true);
            guessesHistory.set(model, new ArrayList<Integer>());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        List<Integer>   guesses = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            model.checkIfCorrectGuess(i);
            guesses.add(i);
        }
        Assertions.assertEquals(guesses, model.getGuessesHistory());
    }

    @Test
    void checkUpdateLowerBound() {
        int newLowerBound = model.getSecretNumber() - 3;
        model.updateBounds(newLowerBound);
        Assertions.assertEquals(newLowerBound, model.getLowerBound());
        Assertions.assertEquals(SecretNumberInitialBounds.SECRET_NUMBER_UPPER_BOUND, model.getUpperBound());
    }

    @Test
    void checkRepetitiveUpdateLowerBound() {
        int newLowerBound = model.getSecretNumber() - 1;
        model.updateBounds(newLowerBound);
        Assertions.assertEquals(newLowerBound, model.getLowerBound());
        Assertions.assertEquals(SecretNumberInitialBounds.SECRET_NUMBER_UPPER_BOUND, model.getUpperBound());
    }

    @Test
    void checkUpdateUpperBound() {
        int newUpperBound = model.getSecretNumber() + 3;
        int backupLowerBound = model.getLowerBound();
        model.updateBounds(newUpperBound);
        Assertions.assertEquals(newUpperBound, model.getUpperBound());
        Assertions.assertEquals(backupLowerBound, model.getLowerBound());
    }

    @Test
    void checkRepetitiveUpdateUpperBound() {
        int newUpperBound = model.getSecretNumber() + 1;
        int backupLowerBound = model.getLowerBound();
        model.updateBounds(newUpperBound);
        Assertions.assertEquals(newUpperBound, model.getUpperBound());
        Assertions.assertEquals(backupLowerBound, model.getLowerBound());
    }
}
