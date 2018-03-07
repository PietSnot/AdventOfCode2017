/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Piet
 */
public class CareyBrown {
    
    public static void main(String... args) {
        List<Pair<Integer, Boolean>> b = IntStream.rangeClosed(1, 10)
                .mapToObj(CareyBrown::runConfig)
                .collect(Collectors.toList())
        ;
        System.out.println(b);
    }
    
    public static Pair<Integer, Boolean> runConfig(int a) {
        Random r = new Random();
        try {
            int b = a / r.nextInt(3);
            return new Pair<>(a, true);
        }
        catch (Exception e) {
            return new Pair<>(a, false);
        }
    }
}

class Pair<K, V> {
    final K k;
    final V v;
    
    Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }
    
    @Override
    public String toString() {
        return String.format("(%s, %s)", k, v);
    }
}
