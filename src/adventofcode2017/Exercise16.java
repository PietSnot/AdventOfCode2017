/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise16 {
    int[] chars;
    String[] moves;
    
    public static void main(String... args) {
        Exercise16 opgave16 = new Exercise16();
        opgave16.solvePart1();
        opgave16.solvePart2();
    }
    
    private Exercise16() {
        chars = "abcdefghijklmnop".chars().toArray();
        readInput();
    }
    
    private void readInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise16.txt");
            Path path = Paths.get(url.toURI());
            moves = Files.lines(path)
                    .limit(1)
                    .flatMap(s -> Arrays.stream(s.split(",")))
                    .toArray(String[]::new)
            ;
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read input file!!!!!");
        }
    }
    
    private void solvePart1() {
        int[] copy = Arrays.copyOf(chars, chars.length);
        for (String move: moves) applyMove(copy, move);
        System.out.println("solution part 1: " + Arrays.toString(copy));
        String s = Arrays.stream(copy).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
        System.out.println("solution part 1: " + s);
    }
    
    private void solvePart2() {
        int[] copy = Arrays.copyOf(chars, chars.length);
        int startwaarde = 1_000_000_000 / 420_000 * 420_000;
        System.out.println("keer = " + startwaarde);
        for (int keer = startwaarde + 1; keer <= 1_000_000_000; keer++) {
            if (keer % 1_000 == 0) System.out.println("bezig met keer " + keer);
            for (String move: moves) applyMove(copy, move);
        }
        System.out.println("solution part 2: " + Arrays.toString(copy));
        String s = Arrays.stream(copy).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
        System.out.println("solution part 2: " + s);
    }
    
    private void applyMove(int[] array, String move) {
        String[] s;
        switch (move.substring(0, 1)) {
            case "x":
                s = splitMove(move);
                int eerste = Integer.parseInt(s[0]);
                int tweede = Integer.parseInt(s[1]);
                swap(array, eerste, tweede);
                break;
            case "p":
                s = splitMove(move);
                eerste = find(array, s[0].charAt(0));
                tweede = find(array, s[1].charAt(0));
                swap(array, eerste, tweede);
                break;
            case "s":
                int p = Integer.parseInt(move.substring(1));
                if (p < 0 || p >= array.length) {
                    System.out.println("p = " + p);
                }
                int templength = array.length - p;
                int[] temp = new int[templength];
                for (int i = 0; i < templength; i++) temp[i] = array[i];
                int from = templength;
                int to = 0;
                for (int i = from; i < array.length; i++, to++) {
                    array[to] = array[i];
                }
                for (int i = 0; i < templength; i++, p++) array[p] = temp[i];
                break;
        }
    }
    
    private String[] splitMove(String move) {
        return move.substring(1).split("/");
    }
    private void swap(int[] array, int x, int y) {
        int t = array[x];
        array[x] = array[y];
        array[y] = t;
    }
    
    private int find(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }
        throw new RuntimeException("Can't find character in Array!!!!");
    }
}
    

