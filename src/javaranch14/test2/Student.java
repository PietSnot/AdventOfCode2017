/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14.test2;

import java.util.Comparator;
import javaranch14.test1.Person;

/**
 *
 * @author Piet
 */
public class Student extends Person {
    final int id;
    private static int id_source = 1;
    
    public Student(String f, String n) {
        super(f, n);
        id = id_source;
        id_source++;
    }
    
    public static Comparator<Student> getIDComparator() {
        return Comparator.<Student>comparingInt(e -> e.id);
    }
    
    @Override
    public String toString() {
        return String.format("%s %s, id: %d", firstName, lastName, id);
    }
}
