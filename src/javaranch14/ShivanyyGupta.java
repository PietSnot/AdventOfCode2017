/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class ShivanyyGupta {

}

class Person {

    final static int FIELD_WIDTH = 10;
    final static boolean RIGHT = true, LEFT = true;
    final int age;
    final String name;
    final int salary;
    static int id = 0;
    final int identity;
    final static String[] HEADER = 
        {format("identity", LEFT), format("age", LEFT), format("name", LEFT), format("salary", LEFT)};
    
    Person(int a, String n, int sal) {
        id++;
        age = a;
        name = n;
        salary = sal;
        identity = id;
    }
    
    public String[] toStringSpecial() {
        String[] result = new String[4];
        result[0] = format(identity, RIGHT);
        result[1] = format(age, RIGHT);
        result[2] = format(name, RIGHT);
        result[3] = format(salary, RIGHT);
        return result;
    }
    
    private static String format(Object s, boolean alignedRight) {
        String space = IntStream.rangeClosed(1, FIELD_WIDTH).mapToObj(i -> " ").collect(Collectors.joining());
        String temp = alignedRight ? space + s : s + space;
        int start = alignedRight ? temp.length() - FIELD_WIDTH + 1 : 0;
        int end = alignedRight ? temp.length() : FIELD_WIDTH;        
        return temp.substring(start, end);
    }
            
    public static void main(String... args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(30, "aap", 10_000));
        persons.add(new Person(20, "noot", 20_000));
        persons.add(new Person(10, "mies", 30_000));
        String[][] table = IntStream.rangeClosed(0, persons.size())
                .mapToObj(i -> i == 0 ? Person.HEADER : persons.get(i - 1).toStringSpecial())
                .toArray(String[][]::new)
        ;
        String[][] tableT = transpose(table);
        Arrays.stream(table).map(Arrays::toString).forEach(System.out::println);
        System.out.println("*************************************************8");
        Arrays.stream(tableT).map(Arrays::toString).forEach(System.out::println);
    }
    
    private static String[] getColumn(String[][] matrix, int column) {
        return Arrays.stream(matrix).map(row -> row[column]).toArray(String[]::new);
    }
    
    private static String[][] transpose(String[][] matrix) {
        return IntStream.range(0, matrix[0].length)
                .mapToObj(i -> getColumn(matrix, i))
                .toArray(String[][]::new)
        ;
    }
}


