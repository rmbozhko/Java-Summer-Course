package edu.summer.java;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Task #1
        CollectionElementOccurrenceCounter counter = new CollectionElementOccurrenceCounter();
        var randomElemsList = new Random().ints(10, 1, 11)
                .boxed()
                .collect(Collectors.toList());
        Map<Integer, Long> intOccurrences = counter.count(randomElemsList);
        System.out.println(intOccurrences);

        // Task #2
        ImmutableArrayList<Integer> immutableList = new ImmutableArrayList<>();
        immutableList.addAll(randomElemsList);

        try {
            immutableList.remove(2);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } finally {
            System.out.println(immutableList);
        }
    }
}
