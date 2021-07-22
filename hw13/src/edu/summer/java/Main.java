package edu.summer.java;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        HomeworkChallenges  challenger = new HomeworkChallenges();
        int[] array = new Random().ints(10, 1, 100).toArray();

        Arrays.stream(array).forEach(elem -> System.out.print(elem + " "));
        System.out.print("\n");
        // task #1
        System.out.println(challenger.findAverage(array));

        // task #2
        System.out.println(challenger.findMinArrayElementAndIndex(array));

        // task #3
        System.out.println(challenger.countElementsEqualToZero(array));

        // task #4
        System.out.println(challenger.countElementsMoreThanZero(array));

        // task #5
        Arrays.stream(challenger.multiplyArrayWithConstant(array)).forEach(elem -> System.out.print(elem + " "));
    }
}
