package edu.summer.java;

import java.util.*;
import java.util.stream.IntStream;

public class HomeworkChallenges {
    public double findAverage(int[] array) {
        return Arrays.stream(array).average().getAsDouble();
    }

    public Map<Integer, Integer>    findMinArrayElementAndIndex(int[] array) {
        List<Integer> intList = new ArrayList<Integer>(array.length);
        for (int i : array) {
            intList.add(i);
        }
        Optional<Integer> maxValueIndex = IntStream.range(0, intList.size())
                                                    .boxed()
                                                    .min(Comparator.comparing(intList::get));
        return Map.of(maxValueIndex.get(), intList.get(maxValueIndex.get()));
    }

    public long     countElementsEqualToZero(int[] array) {
        return Arrays.stream(array).filter(elem -> elem == 0).count();
    }

    public long     countElementsMoreThanZero(int[] array) {
        return Arrays.stream(array).filter(elem -> elem > 0).count();
    }

    public int[]     multiplyArrayWithConstant(int[] array) {
        int k = 2;
        return Arrays.stream(array).map(elem -> elem * k).toArray();
    }
}
