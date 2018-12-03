/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

/**
 *
 * @author Piet
 */
public class Exercise23Deel2 {
    public static void main(String... args) {
        int a = 0, b = b = 107_900, c = b + 17_000, d = 2, e = 0, f = 0, g = 0, h = 0;
        while ( b < c) {
            System.out.println("b = " + b);
            for  (d = 2; d < b / d; d++) {
                if (b % d == 0) h++;
            }
            b += 17;
        } 
        System.out.println("h = " + h);
    }
}
