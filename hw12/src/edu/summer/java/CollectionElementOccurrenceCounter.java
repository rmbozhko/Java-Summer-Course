package edu.summer.java;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Map;

public class CollectionElementOccurrenceCounter {
    /**
     * Counts number of element occurrences in the provided collection.
     * @param collection stores elements occurrence of which is to be counted
     * @param <T> can be the type which represent Number or any of its subclasses.
     * @return a map containing element as a key and number of its occurrences in collection as a value
     * @see Collection
     * @see Number
     * @see Map
     */
    public <T extends Number> Map<T, Long> count(Collection<T> collection) {
        return collection.stream().collect(Collectors.groupingBy(elem -> elem, Collectors.counting()));
    }
}
