/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class Exercise21 {
    
    Map<String, String> transformations = new TreeMap<>();
    long solutionPart1, solutionPart2;
    List<String> transformed = new ArrayList<>();
    
    public static void main(String... args) {
        Exercise21 opgave21 = new Exercise21();
        opgave21.solutionPart1 = opgave21.solve(5);
        System.out.println("solution part 1: " + opgave21.solutionPart1);
        opgave21.solutionPart2 = opgave21.solve(18);
        System.out.println("solution part 2: " + opgave21.solutionPart2);
    }
    
    private Exercise21() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise21.txt");
            Path path = Paths.get(url.toURI());
            List<String> list = Files.readAllLines(path);
            createTransformationsMap(list);
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Cant read input file!!!!!");
        }
    }
    
    private long solve(long n) {
        String result = ".#./..#/###";
        for (long i = 1; i <= n; i++) result = applyTransformation(result);
        return result.chars().filter(c -> c == '#').count();
    }
    
    private String applyTransformation(String toTransform) {
        int size = (int) toTransform.chars().filter(c -> c == '/').count() + 1;
        String s = toTransform.replaceAll("/", "");
        int factor = size % 2 == 0 ? 2 : 3;
        String[][] array = new String[size / factor][size / factor];
        Arrays.stream(array).forEach(x -> Arrays.fill(x, ""));
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array.length; col++) {
                int loc = row * factor * size + factor * col;
                String temp;
                if (factor == 2) {
                    temp = "" +  s.charAt(loc) + s.charAt(loc + 1) + "/"
                              + s.charAt(loc + size) + s.charAt(loc + size + 1)
                    ;
                }
                else {
                    temp = "" +  s.charAt(loc) + s.charAt(loc + 1) + s.charAt(loc + 2) + "/"
                              + s.charAt(loc + size) + s.charAt(loc + size + 1) + s.charAt(loc + size + 2) + "/"
                              + s.charAt(loc + 2 * size) + s.charAt(loc + 2 * size + 1) + s.charAt(loc + 2 * size + 2)
                    ;
                }       
                array[row][col] = transformations.get(temp);
            }
        }
        String[] result = new String[array.length];
        Arrays.fill(result, "");
        for (int row = 0; row < array.length; row++) {
            result[row] = array[row][0];
            for (int col = 1; col < array.length; col++) {
                result[row] = merge(result[row], array[row][col]);
            }
        }
        return Arrays.stream(result).collect(Collectors.joining("/"));
    }
    
    private String merge(String s1, String s2) {
        String[] list1 = s1.split("/");
        String[] list2 = s2.split("/");
        String[] result = new String[list1.length];
        Arrays.fill(result, "");
        for (int i = 0; i < list1.length; i++) {
            result[i] += list1[i] + list2[i];
        }
        String s = Arrays.stream(result).collect(Collectors.joining("/"));
        return s;
    }
    
    private void createTransformationsMap(List<String> list) {
        for (String string: list) {
            String[] s = string.split("=>");
            char[][] array2D = stringToArray2D(s[0].trim());
            Set<String> symmetrics = getAllEqualMatrices(array2D);
            symmetrics.forEach(str -> transformations.put(str.trim(), s[1].trim()));
        }
    }
    
    private char[][] stringToArray2D(String s) {
        String[] t = s.split("/");
        return Arrays.stream(t).map(String::toCharArray).toArray(char[][]::new);
    }
    
    private String array2DToString(char[][] a) {
        return Arrays.stream(a).map(String::new).collect(joining("/"));
    }
    
    private char[] getColumn(char[][] a, int col) {
        char[] chars = new char[a.length];
        for (int j = 0; j < a.length; j++) chars[j] = a[j][col];
        return chars;
    }
    
    private char[][] rotate90(char[][] a) {
        return IntStream.range(0, a.length)
                .mapToObj(i -> reverse(getColumn(a, i)))
                .toArray(char[][]::new)
        ;
    }
    
    private char[][] rotate180(char[][] a) {
        return IntStream.range(0, a.length)
                .mapToObj(i -> reverse(a[a.length - 1 - i]))
                .toArray(char[][]::new)  
        ;
    }
    
    private char[][] rotate270(char[][] a) {
        return IntStream.range(0, a.length)
                .mapToObj(i -> getColumn(a, a.length - 1 - i))
                .toArray(char[][]::new)
        ;
    }
    
    private char[][] flipOverXAxis(char[][] a) {
        return IntStream.range(0, a.length)
                .mapToObj(i -> a[a.length - 1 - i])
                .toArray(char[][]::new)
        ;
    }
    
    private char[][] flipOverYAxis(char[][] a) {
        return Arrays.stream(a)
                .map(this::reverse)
                .toArray(char[][]::new)
        ;
    }
    
    private char[] reverse(char[] a) {
        char[] reversed = new char[a.length];
        for (int i = 0; i < a.length; i++) reversed[i] = a[a.length - 1 - i];
        return reversed;
    }
    
    private Set<String> getAllEqualMatrices(char[][] x) {
        List<char[][]> list = new ArrayList<>();
        list.add(x);
        list.add(flipOverXAxis(x));
        list.add(flipOverYAxis(x));
        char[][] temp = rotate90(x);
        list.add(temp);
        list.add(flipOverXAxis(temp));
        list.add(flipOverYAxis(temp));
        temp = rotate180(x);
        list.add(temp);
        list.add(flipOverXAxis(temp));
        list.add(flipOverYAxis(temp));
        temp = rotate270(x);
        list.add(temp);
        list.add(flipOverXAxis(temp));
        list.add(flipOverYAxis(temp));
        return list.stream().map(this::array2DToString).collect(toSet());
    }
}