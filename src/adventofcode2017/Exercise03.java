/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Piet
 */
public class Exercise03 {
    
    public static void main(String... args) {
        Exercise03 opgave03 = new Exercise03();
        int givenInput = 289_326;
        int solution = opgave03.solvePart1(givenInput);
        System.out.println("number of steps to take: " + solution);
//          for (int i = 1; i <= 49; i++) {
//              Point p = opgave03.determineCoordinates(i);
//              System.out.format("N = %d: pomt(%d, %d)%n", i, p.x, p.y);
//          }
//        System.out.println(opgave03.determineIndex(new Point(3,-3)));
        int solution2 = opgave03.solvePart2(givenInput);
        System.out.println("solution part 2: " + solution2);
    }
    
    private int solvePart1(int givenInput) {
        Point p = determineCoordinates(givenInput);
        return Math.abs(p.x) + Math.abs(p.y);
    }
    
    private int solvePart2(int givenInput) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        int sum = 1;
        int currentCell = 1;
        list.add(sum);
        while (sum <= givenInput) {
            sum = 0;
            currentCell++;
            Point point = determineCoordinates(currentCell);
            List<Point> neighbors = getNeighbors(point);
            for (Point p: neighbors) {
                int index = determineIndex(p);
                if (index >= currentCell) continue;
                int pvalue = list.get(index);
                sum += pvalue;
            }
            list.add(sum);
        }
        return sum;
    }
    
    private List<Point> getNeighbors(Point p) {
        List<Point> list = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                list.add(new Point(p.x + dx, p.y + dy));
            }
        }
        return list;
    }
    
    private Point determineCoordinates(int n) {
        int ring = determineRing(n);
        int mt = middleTop(ring);
        int ml = middleLeft(ring);
        int mb = middleBottom(ring);
        int mr = middleRight(ring);
        
        if (n <= rightTop(ring)) return new Point(ring - 1, n - mr);
        else if (n <= leftTop(ring)) return new Point(mt - n, ring - 1);
        else if (n <= leftBottom(ring)) return new Point(-(ring - 1), ml - n);
        else return new Point(n - mb, -(ring - 1));
    }
    
    private int determineIndex(Point p) {
        int ring = Math.max(Math.abs(p.x), Math.abs(p.y)) + 1;
        int valueToCountFrom = rightBottom(ring - 1) + 1;
        int startY = -ring + 2;
        if (p.x == ring - 1) {
            if (p.y == -p.x) return (2 * ring - 1) * (2 * ring - 1);
            int x = valueToCountFrom + p.y - startY;
            return x;
        }
        else if (p.y == ring - 1) {
            valueToCountFrom = rightTop(ring);
            int x = valueToCountFrom + ring - 1 - p.x;
            return x;
        }
        else if (p.x == -ring + 1) {
            valueToCountFrom = leftTop(ring);
            int x = valueToCountFrom + ring - 1 - p.y;
            return x;
        }
        else {
            valueToCountFrom = leftBottom(ring);
            int x = valueToCountFrom + ring - 1 + p.x;
            return x;
        }
    }
    
    private int determineRing(int K) {
        return (int) Math.ceil((1 + Math.sqrt(K)) / 2);
    }
    
    private int rightTop(int ringnr) {
        return 4 * ringnr * ringnr - 10 * ringnr + 7;
    }
    
    private int leftTop(int ringnr) {
        return 4 * ringnr * ringnr - 8 * ringnr + 5;
    }
    
    private int leftBottom(int ringnr) {
        return 4 * ringnr * ringnr - 6 * ringnr + 3;
    }
    
    private int rightBottom(int ringnr) {
        return 4 * ringnr * ringnr - 4 * ringnr + 1;  // = (2n-1)^2
    }
    
    private int middleTop(int ringnr) {
        return 4 * ringnr * ringnr - 9 * ringnr + 6;
    }
    
    private int middleLeft(int ringnr) {
        return 4 * ringnr * ringnr - 7 * ringnr + 4;
    }
    
    private int middleBottom(int ringnr) {
        return 4 * ringnr * ringnr - 5 * ringnr + 2;
    }
    
    private int middleRight(int ringnr) {
        return 4 * ringnr * ringnr - 11 * ringnr + 8;
    }
}
