/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

/**
 *
 * @author Piet
 */
public class BinimanIdugboe {

}

//interface Comparable<T> 
//    {
//        public int compareTo(T o);
//    }

class TypeParametersOfMethod {

    public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
        for (T e : anArray) {
            if (e.compareTo(elem) > 0) {
                ++count;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String[] anArray = {"Ann", "Ben", "Joseph", "Halle", "Mary", "Smith", "Victor", "Wayne", "Xavier", "Yohane", "Zanadi"};

        System.out.println("The number of elements greater than Halle is: " + TypeParametersOfMethod.countGreaterThan(anArray, "Halle"));
    }
}
