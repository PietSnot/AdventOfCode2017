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
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise19 {
    
    char[][] inputArray;
    Direction currentDirection;
    Point currentLocation;
    String solutionPart1 = "";
    int solutionPart2 = 0;
    
    public static void main(String... args) {
        Exercise19 opgave19 = new Exercise19();
        opgave19.solvePart1AndPart2();
        System.out.println("solution part 1: " + opgave19.solutionPart1);
        System.out.println("solution part 2: " + opgave19.solutionPart2);
    }
    
    private void solvePart1AndPart2() {
        currentDirection = Direction.DOWN;
        for (int i = 0; i < inputArray[0].length; i++) {
            if (inputArray[0][i] != ' ') {
                currentLocation = new Point(0, i);
                char c = inputArray[currentLocation.x][currentLocation.y];
                if (Character.isLetter(c)) solutionPart1 += c;
                break;
            }
        }
        
        solutionPart2 = 1;
        
        while (true) {
            getNextLocation();
            if (currentLocation == null) break;
            solutionPart2++;
            char c = inputArray[currentLocation.x][currentLocation.y];
//            System.out.println("directon: " + currentDirection + " ; location: " + currentLocation + "; char: " + c);
            if (Character.isLetter(c)) solutionPart1 += c;
        }
    }
    
     private Exercise19() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise19.txt");
            Path path = Paths.get(url.toURI());
            List<String> lines = Files.readAllLines(path);
            inputArray = lines.stream()
                    .map(line -> line.toCharArray())
                    .toArray(char[][]::new)
            ;
            System.out.println("arrays.lenght: " + Arrays.stream(inputArray).mapToInt(a -> a.length).boxed().collect(Collectors.toSet()));
            
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read inputfile!!!!!!!");
        }
    }
     
    private void getNextLocation() {
        int x = currentLocation.x;
        int y = currentLocation.y;
        switch (currentDirection) {
            case LEFT:
                if (canGoLeft()) goLeft();
                else if (canGoUp()) goUp();
                else if (canGoDown()) goDown();
                else currentLocation = null;
                break;
            case RIGHT:
                if (canGoRight()) goRight();
                else if (canGoUp()) goUp();
                else if (canGoDown()) goDown();
                else currentLocation = null;
                break;
            case UP:
                if (canGoUp()) goUp();
                else if (canGoLeft()) goLeft();
                else if (canGoRight()) goRight();
                else currentLocation = null;
                break;
            case DOWN:
                if (canGoDown()) goDown();
                else if (canGoLeft()) goLeft();
                else if (canGoRight()) goRight();
                else currentLocation = null;
                break;
            default:
                throw new RuntimeException("Unknown Direction!!!");
        }
    }
    
    private boolean canGoUp() {
        return currentLocation.x > 0 && 
               inputArray[currentLocation.x - 1][currentLocation.y] != ' '
        ;
    }
    
    private boolean canGoDown() {
        return currentLocation.x < inputArray.length - 1 && 
               inputArray[currentLocation.x + 1][currentLocation.y] != ' ';
    }
    
    private boolean canGoLeft() {
        return currentLocation.y > 0 && 
               inputArray[currentLocation.x][currentLocation.y - 1] != ' '
        ;
    }
    
    private boolean canGoRight() {
        return currentLocation.y < inputArray[currentLocation.x].length - 1 && 
               inputArray[currentLocation.x][currentLocation.y + 1] != ' '
        ;
    }
    
    private void goUp() {
        currentLocation.translate(-1, 0);
        currentDirection = Direction.UP;
    }
    
    private void goDown() {
        currentLocation.translate(1, 0);
        currentDirection = Direction.DOWN;
    }
    
    private void goLeft() {
        currentLocation.translate(0, -1);
        currentDirection = Direction.LEFT;
    }
    
    private void goRight() {
        currentLocation.translate(0, 1);
        currentDirection = Direction.RIGHT;
    }

    enum Direction {
        DOWN, LEFT, UP, RIGHT
    }
}
