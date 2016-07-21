package org.wicketstuff.datatables.demo;

import java.io.Serializable;

/**
*
*/
public class Person implements Serializable {
    public String firstName;
    public String lastName;
    public int age;
    public long number;

    Person(String firstName, String lastName, int age, long number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.number = number;
    }
}
