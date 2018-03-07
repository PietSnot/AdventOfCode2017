/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Piet
 */
public class CarolinSha {

}

class ExtendedEuclid3 {

//  return array [d, a, b] such that d = gcd(p, q), ap + bq = d
    static BigInteger[] gcd(BigInteger p, BigInteger q) {
        if (q.equals(BigInteger.ZERO)) {
            return new BigInteger[]{p, BigInteger.ONE, BigInteger.ZERO};
        }

        BigInteger[] vals = gcd(q, p.mod(q));
        BigInteger d = vals[0];
        BigInteger a = vals[2];
        //int b = vals[1] - (p / q) * vals[2];
        BigInteger b = vals[1].subtract((p.divide(q)).multiply(vals[2]));
        System.out.println("returning [d, a, b] = " + Arrays.toString(vals));
        return new BigInteger[]{d, a, b};
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print(" Enter your p value: ");
        BigInteger p = sc.nextBigInteger();

        System.out.print(" Enter your q value: ");
        BigInteger q = sc.nextBigInteger();

        BigInteger vals[] = gcd(p, q);
        //System.out.println("gcd(" + p + ", " + q + ") = " + vals[0]);
        //System.out.println(vals[1] + "(" + p + ") + " + vals[2] + "(" + q + ") = " + vals[0]);
        System.out.println(Arrays.toString(vals));
        System.out.println(vals[1].add(q));
        System.out.println(99 * 660 - 293 * 223);
    }

}
