/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Piet
 */
public class Exercise17 {
    
    int inputValue = 348;
    int solutionPart1, solutionPart2;
    
    public static void main(String... args) {
        Exercise17 opgave17 = new Exercise17();
        opgave17.solvePart1();
        System.out.format("solution Part1: %,d%n", opgave17.solutionPart1);
        opgave17.solvePart2();
        System.out.format("solution Part1: %,d%n", opgave17.solutionPart2);
    }
    
    private void solvePart1() {
        solutionPart1 = getValueAfterValue(2017, 2017);
    }
    
    private void solvePart2() {
        int currentValue = 1;
        int currentPos = 1;
        int neighborOfZero = 1;
        while (currentPos <= 50_000_000) {
            currentValue++;
            currentPos = (currentPos + inputValue) % currentValue + 1;
            if (currentPos == 1) {
                neighborOfZero = currentValue;
            }
        }
        solutionPart2 = neighborOfZero;
    }
    
    private int getValueAfterValue(int finalValue, int firstAfter) {
        List<Integer> list = new ArrayList<>(finalValue < 10 ? 10 : finalValue);
        int currentPosition = 0;
        int currentValue = 0;
        while (currentValue <= finalValue) {
            list.add(currentPosition, currentValue);
            currentPosition = (currentPosition + inputValue) % list.size() + 1;
            currentValue++;
            if (currentValue % 100_000 == 0) System.out.println("bezig met " + currentValue);
        }
        int p = list.indexOf(firstAfter) + 1;
        return list.get(p);
    }
}
