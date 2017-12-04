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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise04 {
    
    List<String> inputPassPhrases;
    
    public static void main(String... args) {
        Exercise04 opgave04 = new Exercise04();
        long validPassPhrasesPart1 = opgave04.solvePart1();
        System.out.format("nr of valid passphrases part 1: %,d%n", validPassPhrasesPart1);
        long validPassPhrasesPart2 = opgave04.solvePart2();
        System.out.format("nr of valid passphrases part 1: %,d%n", validPassPhrasesPart2);
    }
    
    private Exercise04() {
        readInputFile();
    }
    
    private long solvePart1() {
        return inputPassPhrases.stream().filter(this::isValidPassPhrasePart1).count();
    }
    
    private long solvePart2() {
        return inputPassPhrases.stream().filter(this::isValidPassPhrasePart2).count();
    }
    
    private void readInputFile() {
        try {
           URL url = Exercise02.class.getResource("Repositories/inputExercise04.txt");
           Path path = Paths.get(url.toURI());
           inputPassPhrases = Files.lines(path).collect(Collectors.toList());
        }
        catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Can't read inputfile!!!");
        }
    }
    
    private boolean isValidPassPhrasePart1(String s) {
        Set<String> set = new HashSet<>();
        boolean b = Pattern.compile("\\s+").splitAsStream(s).allMatch(set::add);
        return b;
    }
    
    private boolean isValidPassPhrasePart2(String s) {
        Set<String> set = new HashSet<>();
        return Arrays.stream(s.split("\\s+")).allMatch(str -> set.add(makeUniform(str)));
    }
    
    private String makeUniform(String s) {
        byte[] chars = s.getBytes();
        Arrays.sort(chars);
        return new String(chars);
    }
}
