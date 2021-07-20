package edu.summer.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectionElementOccurrenceCounterTest {
    private static CollectionElementOccurrenceCounter counter;

    @BeforeAll
    static void     init() {
        counter = new CollectionElementOccurrenceCounter();
    }

    @Test
    void testIntegerCount() {
        List<Integer> listWithElemsToCount = Arrays.asList(4,5,-6,4,5,3,4,2,4,5,7);
        Map<Integer, Long> mapWithElemsAndOccurrences = Map.of(-6, new Long(1),2, new Long(1),
                                                                3, new Long(1),4, new Long(4),
                                                                5, new Long(3),7, new Long(1));
        assertEquals(mapWithElemsAndOccurrences, counter.count(listWithElemsToCount));
    }

    @Test
    void testLongCount() {
        List<Long> listWithElemsToCount = Arrays.asList(4L,5L,-6L,4L,5L,3L,4L,2L,4L,5L,7L);
        Map<Long, Long> mapWithElemsAndOccurrences = Map.of(-6L, new Long(1),2L, new Long(1),
                                                            3L, new Long(1),4L, new Long(4),
                                                            5L, new Long(3),7L, new Long(1));
        assertEquals(mapWithElemsAndOccurrences, counter.count(listWithElemsToCount));
    }

    @Test
    void testFloatCount() {
        List<Float> listWithElemsToCount = Arrays.asList(4F,5F,-6F,4F,5F,3F,4F,2F,4F,5F,7F);
        Map<Float, Long> mapWithElemsAndOccurrences = Map.of(-6F, new Long(1),2F, new Long(1),
                                                            3F, new Long(1),4F, new Long(4),
                                                            5F, new Long(3),7F, new Long(1));
        assertEquals(mapWithElemsAndOccurrences, counter.count(listWithElemsToCount));
    }

    @Test
    void testCountEmptyCollection() {
        List<Long> listWithElemsToCount = Arrays.asList();
        Map<Long, Long> mapWithElemsAndOccurrences = Map.of();
        assertEquals(mapWithElemsAndOccurrences, counter.count(listWithElemsToCount));
    }
}