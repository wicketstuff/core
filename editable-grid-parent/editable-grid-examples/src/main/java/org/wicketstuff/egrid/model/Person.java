package org.wicketstuff.egrid.model;

import java.io.Serial;
import java.io.Serializable;

public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String address;
    private int age;
    private boolean married;

    public Person() {
    }

    public Person(final String name) {
        this.name = name;
    }

    public Person(final String name, final int age, final String address, final boolean married) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.married = married;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    @Override
    public String toString() {
        return name;
    }
}
