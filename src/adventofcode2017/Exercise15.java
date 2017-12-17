/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

/**
 *
 * @author Piet
 */
public class Exercise15 {
    
    Generator A, B;
    long solutionPart1;
    long solutionPart2;
    
    public static void main(String... args) {
        Exercise15 opgave15 = new Exercise15();
        opgave15.solvePart1();
        System.out.format("solution Part 1: %,d%n", opgave15.solutionPart1);
        opgave15.solvePart2();
        System.out.format("solution Part 2: %,d%n", opgave15.solutionPart2);
//        new Exercise15().test();
    }
    
    Exercise15() {
        A = new Generator(16807L, 116L);
        B = new Generator(48271L, 299L);
    }
    
    private void test() {
        List<Long> list = new ArrayList<>();
        Generator A = new Generator(48271, 8921);
        for (int i = 1; i<=5; i++) list.add(A.nextValue(8));
        int a = 5;
    }
    
    private void solvePart1() {
        solutionPart1 = LongStream.range(0, 40_000_000)
                .filter(i -> A.last16Bits(A.nextValue()) == B.last16Bits(B.nextValue()))
                .count()
        ;
    }
    
    private void solvePart2() {
        A.reset();
        B.reset();
        solutionPart2 = LongStream.range(0, 5_000_000)
                .filter(i -> A.last16Bits(A.nextValue(4L)) == B.last16Bits(B.nextValue(8L)))
                .count()
        ;
    }
    
}

class Generator {
    private static final long divisor = 2147483647L;
    private final long startingValue;
    private final long multiplyFactor;
    private long previousValue;
    
    public Generator(long factor, long startValue) {
        multiplyFactor = factor;
        startingValue = startValue;
        previousValue = startingValue;
    }
    
    public long nextValue() {
        previousValue = (previousValue * multiplyFactor) % divisor;
        return previousValue;
    }
    
    public long nextValue(long multiple) {
        while (nextValue() % multiple != 0);
        return previousValue;
    }
    
    public long last16Bits(long x) {
        return x & 0xFFFF;
    }
    
    public void reset() {
        previousValue = startingValue;
    }
    
}
