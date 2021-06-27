package edu.summer.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ViewTest {
    private static View    view;

    @BeforeAll
    public static void setUp() {
        view = new View();
    }

    @Test
    public void checkInitialInformation() {
        int upperBound = 100;
        int lowerBound = 0;
        Assertions.assertEquals(String.format("I have secret number, which is within %d and %d. Try to guess it.",
                                                lowerBound, upperBound), view.getInitialInformation(lowerBound, upperBound));
    }

    @Test
    public void checkInitialInformationDisplayWithWrongBounds() {
        int upperBound = 0;
        int lowerBound = 100;
        Assertions.assertEquals("Initial Information Error: Upper bound is less than lower bound.",
                                view.getInitialInformation(lowerBound, upperBound));
    }

    @Test
    public void checkInitialInformationDisplayBoundsAreSame() {
        int upperBound = 0;
        int lowerBound = upperBound;
        Assertions.assertEquals("Initial Information Error: Upper bound equals lower bound.",
                                view.getInitialInformation(lowerBound, upperBound));
    }

    @Test
    void checkInformationBeforeGuess() {
        int upperBound = 100;
        int lowerBound = 0;
        Assertions.assertEquals(String.format("The secret number is between %d and %d", lowerBound, upperBound),
                                                view.getInformationBeforeGuess(lowerBound, upperBound));
    }

    @Test
    public void checkInformationBeforeGuessDisplayWithWrongBounds() {
        int upperBound = 0;
        int lowerBound = 100;
        Assertions.assertEquals("Guess Information Error: Upper bound is less than lower bound.",
                view.getInformationBeforeGuess(lowerBound, upperBound));
    }

    @Test
    public void checkInformationBeforeGuessDisplayBoundsAreSame() {
        int upperBound = 0;
        int lowerBound = upperBound;
        Assertions.assertEquals("Guess Information Error: Upper bound equals lower bound.",
                view.getInformationBeforeGuess(lowerBound, upperBound));
    }

    @Test
    void checkWinnerInformationWithLongGuessesHistory() {
        List<Integer>   guessesHistory = List.of(1, 2, 3, 4, 5, 6);
        int             secretNumber = 6;
        Assertions.assertEquals(String.format("Congrats! You guessed the secret number %d. It took %d tries.\n" +
                                            "Tries: " + guessesHistory, secretNumber, guessesHistory.size()),
                                            view.getWinnerInformation(guessesHistory, secretNumber));
    }

    @Test
    void checkWinnerInformationWithShortGuessesHistory() {
        List<Integer>   guessesHistory = List.of(1);
        int             secretNumber = 1;
        Assertions.assertEquals(String.format("Congrats! You guessed the secret number %d. It took %d try.\n" +
                                            "Tries: " + guessesHistory, secretNumber, guessesHistory.size()),
                                            view.getWinnerInformation(guessesHistory, secretNumber));
    }

    @Test
    void checkGameEndInformation() {
        Assertions.assertEquals("The game is finished. See you next time.", view.getGameEndInformation());
    }
}
