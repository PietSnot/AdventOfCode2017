/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */

public class Exercise14 {
    
    String inputString = "vbqugkhl";
    int solutionPart1;
    int solutionPart2;
    
    public static void main(String... args) {
        Exercise14 opgave14 = new Exercise14();
        opgave14.solvePart1();
        opgave14.solvePart2();
        System.out.format("solution part 1: %,d%n", opgave14.solutionPart1);
        System.out.format("solution part 2: %,d%n", opgave14.solutionPart2);
    }
    
    private void solvePart1() {
        solutionPart1 = (int) IntStream.range(0, 128)
                .mapToObj(i -> inputString + "-" + i)
                .flatMap(s -> KnotHash(s).stream())
                .filter(i -> i == 1)
                .count()    
        ;
    }
    
    private void solvePart2() {
        int[][] array = IntStream.range(0, 128)
                .mapToObj(i -> inputString + "-" + i)
                .map(s -> KnotHash(s).stream().mapToInt(i -> i).toArray())
                .toArray(int[][]::new)
        ;
        solutionPart2 = countBlobs(array);
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
    
    private List<Integer> KnotHash(String input) {
        
        // part 1: creating the 64 fold extended array, as list
        int[] copy = IntStream.range(0, 256).toArray();
        List<Integer> temp = new ArrayList<>();
        for (byte b: input.getBytes()) temp.add(Byte.toUnsignedInt(b));
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
        
        List<Integer> as = xors.stream().flatMap(x -> getBits(x).stream())
                .collect(Collectors.toList())
        ;
        return as;
    }
    
    private int nrOfOneBits(int x) {
        String s = Integer.toBinaryString(x);
        int som = 0;
        for (char c: s.toCharArray()) {
            if (c == '1') som++;
        }
        return som;
    }
    
    private List<Integer> getBits(int b) {
        List<Integer> result = new ArrayList<>();
        for (int i = 7; i >= 0; i--) {
            result.add((b >> i) & 1);
        }
        return result;
    }
    
    private int blobSize(int[][] a, boolean[][] processed, int row, int col) {
        if (row < 0 || row >= a.length || col < 0 || col >= a[row].length) return 0;
        if (processed[row][col]) return 0;
        if (a[row][col] == 0) return 0;
        processed[row][col] = true;
        int count = 1;
        int x = blobSize(a, processed, row, col - 1);
        count += blobSize(a, processed, row, col + 1);
        count += blobSize(a, processed, row - 1, col);
        count += blobSize(a, processed, row + 1, col);
        return count;
    }
    
    private int countBlobs(int[][] a) {
        boolean[][] processed = new boolean[a.length][a[0].length];
        int count = 0;
        for (int row = 0; row < a.length; row++) {
            for (int col = 0; col < a[row].length; col++) {
                if (blobSize(a, processed, row, col) > 0) count++;
            }
        }
        return count;
    }
    
}
