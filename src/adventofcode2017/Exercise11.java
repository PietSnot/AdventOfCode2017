/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise11 {
    
    private Map<String, Point> translations;
    private List<String> moves;
    int solutionPart1 = 0, solutionPart2 = 0;
    
    public static void main(String... args) {
        Exercise11 opgave11 = new Exercise11();
        opgave11.solvePart1AndPart2();
        System.out.format("solution part 1: %,d%nsolution part 2: %,d%n", opgave11.solutionPart1, opgave11.solutionPart2);
    }
    
    private Exercise11() {
        readInput();
        translations = new HashMap<>();
        translations.put("n", new Point(0, 2));
        translations.put("ne", new Point(1, 1));
        translations.put("se", new Point(1, -1));
        translations.put("s", new Point(0, -2));
        translations.put("sw", new Point(-1, -1));
        translations.put("nw", new Point(-1, 1));
    }
    
    private void readInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise11.txt");
            Path path = Paths.get(url.toURI());
            String s = Files.readAllLines(path).get(0);
            moves = Arrays.stream(s.split(",")).collect(Collectors.toList());
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read input file!!!!");
        }
    }
    
    private void solvePart1AndPart2() {
        Point point = new Point(0, 0);
        solutionPart2 = 0;

        for (String move: moves) {
            Point t = translations.get(move);
            point.translate(t.x, t.y);
            if (manhattan(point) > solutionPart2) solutionPart2 = manhattan(point);
        }
        solutionPart1 = manhattan(point);
    }
    
    private int manhattan(Point p) {
        return (Math.abs(p.x) + Math.abs(p.y)) / 2;
    }
}
