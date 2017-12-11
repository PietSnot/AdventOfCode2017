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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class Exercise10 {
    
    private int[] inputArray;
    private String inputString;
    
    public static void main(String... args) {
        Exercise10 opgave10 = new Exercise10();
        int solutionPart1 = opgave10.solvePart1();
        System.out.format("answer to part 1: %,d%n", solutionPart1);
        String solutionPart2 = opgave10.solvePart2();
        System.out.format("answer to part 2: %s%n", solutionPart2);
    }
    
    private Exercise10() {
        readInput();
    }
    
    private void readInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise10.txt");
            Path path = Paths.get(url.toURI());
            inputString = Files.readAllLines(path).get(0);
            String[] s = inputString.split(",");
            inputArray = Arrays.stream(s).mapToInt(Integer::parseInt).toArray();
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read inputfile!!!!!!");
        }
    }
    
    private int solvePart1() {
        int[] copy = IntStream.range(0, 256).toArray();
//        int[] copy = {0, 1, 2, 3, 4};
        int currentPosition = 0;
        int skip = 0;
        
        for (int length: inputArray) {
            reversePartOfArray(copy, currentPosition, length);
            currentPosition = (currentPosition + length + skip) % copy.length;
            skip++;
        }
        return copy[0] * copy[1];
    }
    
    private void reversePartOfArray(int[] a, int curPos, int length) {
        for (int index = 0; index <= length / 2 - 1; index++) {
            swapTwoArrayElements(a, curPos + index, curPos + length - 1 - index);
        }
    }
    
    private void swapTwoArrayElements(int[] a, int pos1, int pos2) {
        int p1 = pos1 % a.length;
        int p2 = pos2 % a.length;
        int t = a[p1];
        a[p1] = a[p2];
        a[p2] = t;
    }
    
    private String solvePart2() {
        int[] copy = IntStream.range(0, 256).toArray();
        
        // part 1: creating the 64 fold extended array, here list
        List<Integer> temp = new ArrayList<>();
        for (byte b: inputString.getBytes()) temp.add(Byte.toUnsignedInt(b));
        temp.addAll(Arrays.asList(17, 31, 73, 47, 23));
        List<Integer> allLengths = new ArrayList<>();
        for (int i = 1; i <= 64; i++) allLengths.addAll(temp);
        
        // part 2: performing the loop
        int currentPosition = 0;
        int skip = 0;
        
        for (int length: allLengths) {
            reversePartOfArray(copy, currentPosition, length);
            currentPosition = (currentPosition + length + skip) % copy.length;
            skip++;
        }
        
        // part 3: xor'ing the 16 groups of 16
        List<Integer> xors = new ArrayList<>();
        for (int start = 0; start < 256; start += 16) {
            int x = Arrays.stream(copy).skip(start).limit(16).reduce(0, (a, b) -> a ^ b);
            xors.add(x);
        }
        
        // part 4: creating the hex-string
//        String result =  Arrays.asList(3, 49, 65, 147).stream() // xors.stream()
        String result = xors.stream()
                .map(xor -> {
                        String s = "00000000"+ Integer.toHexString(xor); 
                        return s.substring(s.length() - 2, s.length());
                    }
                )
                .collect(Collectors.joining())
        ;
        
        return result;
    }
}
