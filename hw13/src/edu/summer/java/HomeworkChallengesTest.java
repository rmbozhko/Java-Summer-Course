package edu.summer.java;

import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HomeworkChallengesTest {
    private static  HomeworkChallenges  challenger;
    private final int[]                 array;

    {
        array = new int[] {79, 99, 52, 65, 35, 25, 26, 64, 95, 5};
    }

    @BeforeAll
    static void     setUp() {
        challenger = new HomeworkChallenges();
    }

    @org.junit.jupiter.api.Test
    void findAverage() {
        assertEquals(54.5, challenger.findAverage(array));
    }

    @org.junit.jupiter.api.Test
    void findMinArrayElementAndIndex() {
        assertEquals(Map.of(9, 5), challenger.findMinArrayElementAndIndex(array));
    }

    @org.junit.jupiter.api.Test
    void countElementsEqualToZero() {
        assertEquals(0, challenger.countElementsEqualToZero(array));
    }

    @org.junit.jupiter.api.Test
    void countElementsMoreThanZero() {
        assertEquals(10, challenger.countElementsMoreThanZero(array));
    }

    @org.junit.jupiter.api.Test
    void multiplyArrayWithConstant() {
        assertTrue(checkArrayContent(challenger.multiplyArrayWithConstant(array)));
    }

    private boolean checkArrayContent(int[] ints) {
        int[] modifiedArray = new int[] {158, 198, 104, 130, 70, 50, 52, 128, 190, 10};
        for (int i = 0; i < ints.length; i++) {
            if (modifiedArray[i] != ints[i]) {
                return false;
            }
        }
        return true;
    }
}