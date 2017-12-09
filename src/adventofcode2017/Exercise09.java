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
import java.util.List;

/**
 *
 * @author Piet
 */
public class Exercise09 {
    
    private String inputString;
    private int garbageCharCount = 0;
    
    public static void main(String... args) {
        Exercise09 opgave09 = new Exercise09();
        int solutionPart1 = opgave09.solvePart1();
        System.out.format("group total part 1: %,d%n", solutionPart1);
        System.out.format("cancelled chars in garbage: %,d%n", opgave09.garbageCharCount);
    }
    
    private Exercise09() {
        readInput();
    }
    
    private void readInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise09.txt");
            Path path = Paths.get(url.toURI());
            List<String> strings = Files.readAllLines(path);
            System.out.println("Aantal regels gelezen: " + strings.size());
            inputString = strings.get(0);
            System.out.format("The one inputstring has length: %,d%n", inputString.length());
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read inputfile!!!!!!");
        }
    }
    
    private int solvePart1() {
//        String inputString = "{{<a!>},{<a!>},{<a!>},{<ab>}}";
        boolean inGarbage = false;
        int groepLevel = 0;
        int groepTotaal = 0;
        garbageCharCount = 0;
        boolean skipNextChar = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (skipNextChar) {
                skipNextChar = false;
                continue;
            }
            if (inGarbage) {
                if(c == '!') {
                    skipNextChar = true;
                    continue;
                }
                if (c == '>') {
                    inGarbage = false;
                    continue;
                }
                garbageCharCount++;
            }
            if (!inGarbage) {
                if (c == '<') {
                    inGarbage = true;
                    continue;
                }
                if (c == '{') {
                    groepLevel++;
                    groepTotaal += groepLevel;
                    continue;
                }
                if (c == '}') {
                    groepLevel--;
                    continue;
                }
            }
        }
        return groepTotaal;
    }
}
