package edu.summer.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ImmutableArrayListTest {
    private static ImmutableArrayList<Integer> immutableArrayList;

    @BeforeAll
    static void setUp() {
        immutableArrayList = new ImmutableArrayList<>();
        immutableArrayList.addAll(Arrays.asList(4,5,-6,4,5,3,4,2,4,5,7));
    }

    @Test
    void clear() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.clear());
    }

    @Test
    void remove() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.remove(1));
    }

    @Test
    void testRemove() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.remove(Integer.valueOf(7)));
    }

    @Test
    void removeAll() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.removeAll(immutableArrayList));
    }

    @Test
    void removeIf() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.removeIf(elem -> elem > 3));
    }

    @Test
    void removeRange() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.removeRange(1, 4));
    }

    @Test
    void retainAll() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.retainAll(immutableArrayList));
    }

    @Test
    void set() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableArrayList.set(1, 5));
    }
}