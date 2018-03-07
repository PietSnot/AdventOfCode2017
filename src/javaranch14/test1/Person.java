/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14.test1;

/**
 *
 * @author Piet
 */
public class Person implements Comparable<Person> {
    final protected String firstName;
    final protected String lastName;
    
    public Person(String f, String n) {
        firstName = f;
        lastName = n;
    }
    
    @Override
    public int compareTo(Person p) {
        if (p == null) return -1;
        int x = lastName.compareToIgnoreCase(p.lastName);
        return x != 0 ? x :
              firstName.compareToIgnoreCase(p.firstName);
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
}
