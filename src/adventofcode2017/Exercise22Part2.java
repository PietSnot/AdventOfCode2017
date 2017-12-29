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
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise22Part2 {
    Map<Point, State> inputMap = new HashMap<>();
    Direction currentDir = Direction.UP;
    Point currentPos = new Point (0, 0);
    int solutionPart2;
    
    //------------------------------------------------------------
    public static void main(String... args) {
        boolean test = false;
        Exercise22Part2 opgave22 = new Exercise22Part2(test);
        opgave22.solvePart2();
        System.out.format("solution to part 2: %,d%n", opgave22.solutionPart2);
    }
    
    //------------------------------------------------------------
    private Exercise22Part2(boolean test) {
        try {
            URL url = getClass().getResource("Repositories/inputExercise22.txt");
            Path path = Paths.get(url.toURI());
            List<String> list = Files.readAllLines(path);
            List<String> listTest = Arrays.asList( "..#", "#..", "...");
            fillInputMap(test ? listTest : list);
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read input file!!!!");
        }
    }
    
    //------------------------------------------------------------
    private void fillInputMap(List<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        for (int row = 0; row < height; row++) {
            String s = list.get(row);
            for (int col = 0; col < width; col++) {
                Point p = new Point(col - width / 2, height / 2 - row);
                inputMap.put(p, State.getState("" + s.charAt(col))); 
            }
        }
        System.out.println("inputmap:");
        print(inputMap);
        System.out.println("******************************");
    }
    
    //------------------------------------------------------------
    private void solvePart2() {
        Map<Point, State> copy = new HashMap<>(inputMap);
        int totalBursts = 10_000_000;
        solutionPart2 = 0;
        currentDir = Direction.UP;
        currentPos = new Point(0, 0);
        
        for (int burst = 1; burst <= totalBursts; burst++) {
            if (!copy.containsKey(currentPos)) {
                copy.put(currentPos, State.CLEAN);
            }
            currentDir = currentDir.newDirection(copy.get(currentPos));
            if (copy.get(currentPos).equals(State.WEAKENED)) solutionPart2++;
            copy.put(currentPos, copy.get(currentPos).changeState());
            currentPos = currentDir.nextPoint(currentPos);
//            System.out.println("map after burst " + burst);
//            System.out.println("*********************");
//            print(copy);
//            System.out.format("current dir: %s   current pos: %s    current solution: %,d%n", currentDir.toString(), currentPos.toString(), solutionPart2);
//            System.out.println("**8************8**************************************");
//            System.out.println("**8************8**************************************");
            int a = 0;    // you can put a linebreak here
        }
    }
    
    //------------------------------------------------------------
    private void print(Map<Point, State> map) {
        int minrow = map.keySet().stream().mapToInt(p -> p.y).min().getAsInt();
        int maxrow = map.keySet().stream().mapToInt(p -> p.y).max().getAsInt();
        int minx = map.keySet().stream().mapToInt(e -> e.x).min().getAsInt();
        int maxx = map.keySet().stream().mapToInt(e -> e.x).max().getAsInt();
        int[] x = new int[1];
        for (int row = maxrow; row >= minrow; row--) {
            x[0] = row;
            List<Integer> list = map.entrySet().stream()
                    .filter(e -> e.getKey().y == x[0])
                    .map(e -> e.getKey().x)
                    .sorted()
                    .collect(Collectors.toList())
            ;
            System.out.format("row %3d: ", row);
            for (int i = minx; i <= maxx; i++) {
                Point p = new Point(i, row);
                String f;
                if (p.x == 0 && p.y == 0) f = "|[%s]|";
                else f = "| %s |";
                if (map.keySet().contains(p)) System.out.format(f, map.get(p).toString());
                else System.out.format(f, ".");
            }
            System.out.println();
//            if (row == minrow) System.out.println("******************************************");
        }
    }
}

//*********************************************************************
enum State {

    CLEAN ("."), WEAKENED ("W"), INFECTED ("#"), FLAGGED ("F");
    String s;

    //------------------------------------
    State(String s) {
        this.s = s;
    }

    //------------------------------------
    @Override
    public String toString() {
        return s;
    }

    //------------------------------------
    public static State getState(String s) {
        switch (s) {
            case ".": return State.CLEAN;
            case "W": return State.WEAKENED;
            case "#": return State.INFECTED;
            case "F": return State.FLAGGED;
            default: throw new RuntimeException("Unknown string in getState");
        }
    }
    
    //------------------------------------
    public State changeState() {
        switch (this) {
            case CLEAN: return State.WEAKENED;
            case WEAKENED: return State.INFECTED;
            case INFECTED: return State.FLAGGED;
            case FLAGGED: return State.CLEAN;
            default: throw new RuntimeException("Unknown state in chageState");
        }
    }
}

//*********************************************************************
enum Direction {
    UP (0, 1), RIGHT (1, 0), DOWN (0, -1), LEFT (-1, 0);

    int dx, dy;

    //------------------------------------
    Direction(int x, int y) {
        dx = x;
        dy = y;
    } 

    //------------------------------------
    public Direction reverse() {
        switch (this) {
            case UP: return Direction.DOWN;
            case RIGHT: return Direction.LEFT;
            case DOWN: return Direction.UP;
            case LEFT: return Direction.RIGHT;
            default: throw new RuntimeException("Unknown direction in 'reverse'");
        }
    }

    //------------------------------------
    public Direction newDirection(State s) {
        if (State.WEAKENED.equals(s)) return this;
        if (State.FLAGGED.equals(s)) return this.reverse();
        switch (this) {
            case UP:
                switch (s) {
                    case CLEAN: return Direction.LEFT;
                    case INFECTED: return Direction.RIGHT;
                    default: throw new RuntimeException("Unknown parameter in 'newDirection'");
                }
            case RIGHT:
                switch (s) {
                    case CLEAN: return Direction.UP;
                    case INFECTED: return Direction.DOWN;
                    default: throw new RuntimeException("Unknown parameter in 'newDirection'");
                }
            case DOWN:
                switch (s) {
                    case CLEAN: return Direction.RIGHT;
                    case INFECTED: return Direction.LEFT;
                    default: throw new RuntimeException("Unknown parameter in 'newDirection'");
                }
            case LEFT:
                switch (s) {
                    case CLEAN: return Direction.DOWN;
                    case INFECTED: return Direction.UP;
                    default: throw new RuntimeException("Unknown parameter in 'newDirection'");
                }
        }
        throw new RuntimeException("Something wrong in 'newDirection'");
    }

    //------------------------------------
    Point nextPoint(Point p) {
        return new Point(p.x + dx, p.y + dy);
    }
}
