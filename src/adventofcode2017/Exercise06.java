/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise06 {
    
    List<Integer> startList;
    List<Integer> firstDouble;
    
    public static void main(String... args) {
        Exercise06 opgave06 = new Exercise06();
        int nrOfStepsPart1 = opgave06.solvePart1();
        System.out.format("nr of steps part 1: %,d%n", nrOfStepsPart1);
        int nrOfStepsPart2 = opgave06.solvePart2();
        System.out.format("nr of steps part 2: %,d%n", nrOfStepsPart2);
    }
    
    private Exercise06() {
        readInputdata();
    }
    
    private void readInputdata() {
        try {
            URL url = Exercise02.class.getResource("Repositories/inputExercise06.txt");
            Path path = Paths.get(url.toURI());
            List<String> s = Files.readAllLines(path);
            startList = Arrays.stream(s.get(0).split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
        }
        catch (Exception ex) {
            throw new RuntimeException("Can't read input data!!");
        }
    }
    
    private int solvePart1() {
        return determineNrOfStepsPart1(startList);
    }
    
    private int determineNrOfStepsPart1(List<Integer> list) {
        Set<List<Integer>> setOfLists = new HashSet<>();
        setOfLists.add(list);
        List<Integer> currentList = new ArrayList<>(list);
        while (setOfLists.add(currentList = determineNextList(currentList)));
        firstDouble = new ArrayList<>(currentList);
        return setOfLists.size();
    }
    
    private int solvePart2() {
        int nrOfStepsPart2 = 1;
        List<Integer> currentList = determineNextList(firstDouble);
        while (!(firstDouble.equals(currentList))) {
            nrOfStepsPart2++;
            currentList = determineNextList(currentList);
        }
        return nrOfStepsPart2;
    }
    
    private List<Integer> determineNextList(List<Integer> list) {
        List<Integer> result = new ArrayList<>(list);
        int maxIndex = getIndexOfMax(result);
        int length = result.size();
        int valueToDistribute = result.get(maxIndex);
        result.set(maxIndex, 0);
        for (int i = (maxIndex + 1) % length; valueToDistribute > 0; i = (i + 1) % length) {
            result.set(i, result.get(i) + 1);
            valueToDistribute--;
        }
        return result;
    }
    
    private int getIndexOfMax(List<Integer> list) {
        int maxIndex = 0, maxValue = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > maxValue) {
                maxIndex = i;
                maxValue = list.get(i);
            }
        }
        return maxIndex;
    }
}
