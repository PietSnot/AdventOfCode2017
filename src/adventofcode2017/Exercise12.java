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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class Exercise12 {
    
    private Map<Integer, List<Integer>> inputMap = new HashMap();
    int solutionPart1, solutionPart2;
    
    public static void main(String... args) {
        Exercise12 opgave12 = new Exercise12();
        opgave12.solvePart1AndPart2();
        System.out.format("solution to part 1: %,d%n", opgave12.solutionPart1);
        System.out.format("solution to part 2: %,d%n", opgave12.solutionPart2);
    }
    
    private Exercise12() {
        readInput();
    }
    
    private void readInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise12.txt");
            Path path = Paths.get(url.toURI());
            List<String> lines = Files.readAllLines(path);
            processLines(lines);
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read inputfile!!");
        }
    }
    
    private void processLines(List<String> lines) {
        for (String line: lines) {
            String[] parts = line.split("<->");
            int firstpart = Integer.parseInt(parts[0].trim());
            List<Integer> list = Arrays.stream(parts[1].split(","))
                    .map(s -> Integer.parseInt(s.trim()))
                    .collect(toList())
            ;
            inputMap.put(firstpart, list);
        }
    }
    
    private void solvePart1AndPart2() {
        UnionFind uf = new UnionFind(inputMap.size());
        for (int key: inputMap.keySet()) {
            for (int value: inputMap.get(key)) {
                uf.union(key, value);
            }
        }
        solutionPart1 = uf.getAllRelatedTo(0).size();
        solutionPart2 = uf.getAllGroups().size();
    }
}

//******************************************************************
// class nionFind from my library
//******************************************************************
class UnionFind {
    
    //==============================================
    // members
    //==============================================
    final private int size;
    final private Map<Integer, Object> map = new HashMap<>();
    final private int startValue;
    
    //==============================================
    // constructors
    //==============================================
    public UnionFind(int size) {
        this(size, 0);
    }
    
    public UnionFind(int size, int startValue) {
        this.size = size;
        this.startValue = startValue;
        IntStream.range(startValue, startValue + size).forEach(i -> map.put(i, new Object()));
    }
    
    //==============================================
    // public methods
    //==============================================
    public boolean areRelated(int a, int b) {
        if (!allAreInMap(a, b)) return false;
        return map.get(a) == map.get(b);
    }
    
    //--------------------------------------
    public boolean union(int a, int b) {
        if (!allAreInMap(a, b)) return false;
        if (areRelated(a, b)) return true;
        Object o = map.get(a);
        getGroup(b).forEach(s -> map.put(s, o));
        return true;
    }
    
    //--------------------------------------
    public Set<Integer> getElements() {
        return map.keySet();
    }
    
    //--------------------------------------
    public Collection<List<Integer>> getAllGroups() {
        Map<Object, List<Integer>> temp = map.entrySet().stream().collect(
            groupingBy(Entry::getValue, mapping(Entry::getKey, toList()))
        );
        return temp.values();
    }
    
    //--------------------------------------
    @Override
    public String toString() {
        return getAllGroups().toString();
    }
    
    //--------------------------------------
    public Set<Integer> getAllRelatedTo(int a) {
        return getGroup(a);
    }
    
    //==============================================
    // private methods
    //==============================================
    private Set<Integer> getGroup(int a) {
        final Object o = map.get(a);
        return map.keySet().stream()
                .filter(s -> map.get(s) == o)
                .collect(toSet());
    }
    
    //--------------------------------------
    private boolean allAreInMap(int... a) {
        return Arrays.stream(a).allMatch(i -> i >= startValue && i < startValue + size);
    }
    
    //==============================================
    // end of class
    //==============================================
}