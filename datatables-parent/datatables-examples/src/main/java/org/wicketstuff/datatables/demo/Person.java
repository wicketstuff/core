package org.wicketstuff.datatables.demo;

import java.io.Serializable;

/**
*
*/
class Person implements Serializable {
    private String firstName;
    private String lastName;
    private int age;

    Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
