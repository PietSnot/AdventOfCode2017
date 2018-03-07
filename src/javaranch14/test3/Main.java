/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14.test3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javaranch14.test2.Student;

/**
 *
 * @author Piet
 */
public class Main {
    public static void main(String... args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Piet", "Souris"));
        students.add(new Student("Cody", "Biggs"));
        
        System.out.println(students);
        Collections.sort(students);
        System.out.println(students);
        students.sort(Student.getIDComparator());
        System.out.println(students);
    }
}
