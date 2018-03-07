/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author Piet
 */
public class JavaRanch14 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Map<String, Integer> distances = new HashMap<>();
//        distances.put("a", 100);
//        distances.put("b", 50);
//        distances.put("c", 120);
//        distances.put("d", 50);
//
//        List<Integer> list = new ArrayList<>(distances.values());
//        list.sort(naturalOrder());
//        int min = list.get(0);
//        List<String> strings = distances.entrySet().stream()
//                .filter(e -> e.getValue() == min)
//                .map(e -> e.getKey())
//                .collect(toList())      
//        ;
//        System.out.println(strings);
        String[] split = "12.047".split("\\.");
        System.out.println(Arrays.toString(split));
        LocalTime t = LocalTime.of(0, 0, Integer.parseInt(split[0]), Integer.parseInt(split[1]) * 1_000_000);
        System.out.println(t);
        System.out.println(t.getClass());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y-M-d");
        LocalDate u = LocalDate.parse("56-6-1", dtf);
        System.out.println("u = " + u);
        LocalDate v = LocalDate.parse("1956-06-01");
        System.out.println("v = " + v);
        
        BiFunction<String, Integer, Character> bif = String::charAt;
        String s = "Piet";
        Function<Integer, Character> funct = s::charAt;
        
    }
}
