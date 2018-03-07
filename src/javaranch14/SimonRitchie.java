/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class SimonRitchie {
    public static void main(String... args) {
        int[] array = {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6,6,6,6,6,6,6};
        IntStream.range(0, 8).forEach(i -> System.out.println(findRarest(array, i)));
    }
    
    public static long findRarest(int[] array, int nth) {
Map<Integer, Long> map = Arrays.stream(array).boxed().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        Comparator<Map.Entry<Integer, Long>> comp = Comparator.comparing(entry -> entry.getValue());
        List<Map.Entry<Integer, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(comp);
        if (nth > list.size() - 1) throw new IllegalArgumentException("no such element");
        return list.get(nth).getKey();
    }
}


