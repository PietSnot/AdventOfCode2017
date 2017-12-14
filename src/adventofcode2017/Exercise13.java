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
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class Exercise13 {
    
    List<Point> layers = new ArrayList<>();
    int solutionPart1 = 0;
    int solutionPart2;
    
    public static void main(String... args) {
        Exercise13 opgave13 = new Exercise13();
        opgave13.solvePart1();
        opgave13.solvePart2();
        System.out.format("solution to part 1: %,d%n", opgave13.solutionPart1);
        System.out.format("solution to part 2: %,d%n", opgave13.solutionPart2);
    }
    
    private Exercise13() {
        readInput();
    }
    
    private void readInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise13.txt");
            Path path = Paths.get(url.toURI());
            List<String> lines = Files.readAllLines(path);
            processLines(lines);
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read input file!!!!");
        }
    }
    
    private void processLines(List<String> list) {
        for (String line: list) {
            String[] strings = line.split(":");
            int depth = Integer.parseInt(strings[0].trim());
            int range = Integer.parseInt(strings[1].trim());
            layers.add(new Point(depth, range));
        }
//        layers.add(new Point(0, 3));
//        layers.add(new Point(1, 2));
//        layers.add(new Point(4, 4));
//        layers.add(new Point(6, 4));
    }
    
    private void solvePart1() {
        solutionPart1 = solve(0);
    }
    
    private int solve(int delay) {   
        int totalDamage = 0;
        for (Point p: layers) {
            if (positionOfScannerIsZero(p, delay)) {
                int damage = p.x * p.y;
                totalDamage += damage;
            }
        }
        return totalDamage;
    }
    
    private boolean positionOfScannerIsZero(Point p, int delay) {
        int picosecond = p.x + delay;
        int range = p.y;
        return (range == 1 || picosecond % (2 * (range - 1)) == 0);
    }
   
    private boolean isCaught(int delay) {
        return layers.stream().anyMatch(p -> positionOfScannerIsZero(p, delay));
    }
    
    private void solvePart2() {
        solutionPart2 = IntStream.iterate(0, delay -> delay + 1)
                .filter(delay -> !isCaught(delay))
                .findFirst()
                .getAsInt()
        ;
    }
}
