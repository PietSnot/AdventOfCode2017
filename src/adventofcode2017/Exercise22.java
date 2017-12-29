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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise22 {
    
    Map<Point, Boolean> inputMap = new HashMap<>();
    Direction currentDir;
    Point currentPos;
    int solutionPart1, solutionPart2;
    
    public static void main(String... args) {
        Exercise22 opgave22 = new Exercise22();
        opgave22.solvePart1();
        System.out.println("solution to part 1: " + opgave22.solutionPart1);
    }
    
    private Exercise22() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise22.txt");
            Path path = Paths.get(url.toURI());
            List<String> list = Files.readAllLines(path);
            fillInputMap(list);
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read input file!!!!");
        }
    }
    
    private void solvePart1() {
        int bursts = 10_000;
        Map<Point, Boolean> copy = new HashMap<>(inputMap);
        
//        Map<Point, Boolean> copy = new HashMap<>();
//        copy.put(new Point(-1, 1), Boolean.FALSE);
//        copy.put(new Point(0, 1), Boolean.FALSE);
//        copy.put(new Point(1, 1), Boolean.TRUE);
//        copy.put(new Point(-1, 0), Boolean.TRUE);
//        copy.put(new Point(0, 0), Boolean.FALSE);
//        copy.put(new Point(1, 0), Boolean.FALSE);
//        copy.put(new Point(-1, -1), Boolean.FALSE);
//        copy.put(new Point(0, -1), Boolean.FALSE);
//        copy.put(new Point(1, -1), Boolean.FALSE);
//        int originallyInfected = (int) copy.entrySet().stream()
//            .filter(e -> e.getValue())
//            .count()
//        ;
        solutionPart1 = 0;
        currentPos = new Point(0, 0);
        currentDir = Direction.UP;
        for (int burst = 1; burst <= bursts; burst++) {
//            if (copy.containsKey(currentPos)) System.out.println("Point " + currentPos + " is presemt."); 
            if (!copy.containsKey(currentPos)) {
                copy.put(currentPos, false);
            }
//            if (!copy.get(currentPos) && !originallyInfected.contains(currentPos)) solutionPart1++;
            if (!copy.get(currentPos)) solutionPart1++;
//            System.out.println("value: " + copy.get(currentPos));
            nextDirection(copy.get(currentPos));
//            System.out.println("next direction therefore: " + currentDir);
            copy.put(currentPos, !copy.get(currentPos));
//            System.out.println("in map now: " + copy.get(currentPos));
//            System.out.println(copy.values());
            currentPos = currentDir.nextPoint(currentPos);  
//            System.out.println("next point: " + currentPos);
//            print(copy);
        }
//        solutionPart1 -= originallyInfected;
    }
    
    private void fillInputMap(List<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        for (int row = 0; row < height; row++) {
            String s = list.get(row);
            for (int col = 0; col < width; col++) {
                Point p = new Point(col - width / 2, height / 2 - row);
                inputMap.put(p, s.charAt(col) == '#'); 
            }
        }
        print(inputMap);
    }
    
    private void print(Map<Point, Boolean> map) {
        int minrow = map.keySet().stream().mapToInt(p -> p.y).min().getAsInt();
        int maxrow = map.keySet().stream().mapToInt(p -> p.y).max().getAsInt();
        int[] x = new int[1];
        for (int row = maxrow; row >= minrow; row--) {
            x[0] = row;
            List<Integer> list = map.entrySet().stream()
                    .filter(e -> e.getKey().y == x[0])
                    .map(e -> e.getKey().x)
                    .sorted()
                    .collect(Collectors.toList())
            ;
            System.out.print("row " + row + ":  ");
            list.stream().forEach(e -> System.out.print("| " + map.get(new Point(e, x[0])) + "|"));
            System.out.println();
            if (row == minrow) System.out.println("******************************************");
        }
    }
    
    //---------------------------------------------------------------
    private void nextDirection(boolean infected) {
        switch (currentDir) {
            case UP:
                currentDir = infected ? Direction.RIGHT : Direction.LEFT;
                break;
            case RIGHT:
                currentDir = infected ? Direction.DOWN : Direction.UP;
                break;
            case DOWN:
                currentDir = infected ? Direction.LEFT : Direction.RIGHT;
                break;
            case LEFT:
                currentDir = infected ? Direction.UP : Direction.DOWN;
                break;
            default:
                throw new RuntimeException("Unknown direction!!!!");
        }
    }
    
    //----------------------------------------------------------
    enum Direction {
        UP (0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0);
        
        Point p;
        
        Direction(int dx, int dy) {
            p = new Point(dx, dy);
        }
        
        public Point nextPoint(Point point) {
            return new Point(point.x + p.x, point.y + p.y);
        }
    }
}
