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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 *
 * @author Piet
 */
public class Exercise08 {
    
    private final Map<String, Integer> registers = new HashMap<>();
    private List<String> instructions = new ArrayList<>();
    private final Map<String, BiPredicate<String, Integer>> predicates = new HashMap<>();
    private final Map<String, BiConsumer<String, Integer>> actions = new HashMap<>();
    int absoluteMax = Integer.MIN_VALUE;
    
    private void fillPredicates() {
        predicates.put("==", (a, b) -> registers.get(a).equals(b));
        predicates.put("!=", (a, b) -> !(registers.get(a).equals(b)));
        predicates.put("<", (a, b) -> registers.get(a) < b);
        predicates.put("<=", (a, b) -> registers.get(a) <= b);
        predicates.put(">", (a, b) -> registers.get(a) > b);
        predicates.put(">=", (a, b) -> registers.get(a) >= b);
    }
    
    private void fillActions() {
        actions.put("INC", (a, b) -> registers.put(a, registers.get(a) + b));
        actions.put("DEC", (a, b) -> registers.put(a, registers.get(a) - b));
    }
    
    public static void main(String... args) {
        Exercise08 opgave08 = new Exercise08();
        opgave08.readAndProcessInput();
        System.out.format("oplossing part 1: %,d%n", opgave08.solvePart1AndPart2());
        System.out.format("maximum reg. during process: %,d%n", opgave08.absoluteMax);
    }
    
    private Exercise08() {
        readAndProcessInput();
    }
    private void readAndProcessInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise08.txt");
            Path path = Paths.get(url.toURI());
            instructions = Files.readAllLines(path);
            fillRegisters();
            fillPredicates();
            fillActions();
        } 
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Something went wrong processing the input data!!!!");
        }
    }
    
    private int solvePart1AndPart2() {
        for (String line: instructions) {
            String[] s = line.split("\\s+");
            boolean b = predicates.get(s[5]).test(s[4], Integer.parseInt(s[6]));
            if (b) {
                actions.get(s[1].toUpperCase()).accept(s[0], Integer.parseInt(s[2]));
                if (registers.get(s[0]) > absoluteMax) absoluteMax = registers.get(s[0]);
            }
        }
        return registers.values().stream().mapToInt(i -> i).max().getAsInt();
    }
    
    private void fillRegisters() {
        for (String line: instructions) {
            String[] strings = line.split("\\s+");
            registers.put(strings[0], 0);
            registers.put(strings[4], 0);
        }
    }
}
