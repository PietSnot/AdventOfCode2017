/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.util.Arrays;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.Map;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class LiutaurasVilda {

}

class Test {

    public static void main(String[] args) {
        int[] A = {5, 1, 3, 2, 3, 2, 2, 3, 4, 4, 4, 4};
        IntStream.range(0, 5).forEach(i -> System.out.println(findNthRarest(A, i)));
    }

    private static int findNthRarest(int[] A, int n) {
        List<Integer> list = Arrays.stream(A).boxed().collect(toList());
        Map<Integer, Integer> map = list.stream().collect(groupingBy(i -> i, summingInt(i -> 1)));
        Comparator<Map.Entry<Integer, Integer>> valuecomp = comparingByValue();
        Comparator<Map.Entry<Integer, Integer>> keycomp = comparing(e -> e.getKey(), comparing(list::indexOf));
        return map.entrySet().stream()
            .sorted(valuecomp.thenComparing(keycomp))
            .skip(n)
            .map(Map.Entry::getKey)
            .findFirst()
            .get()
        ;
    }
}
