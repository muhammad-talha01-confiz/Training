package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Candidate {
    // CSV Format: ID, Name, Gender, Age, City, DOB
    private final int id;
    private final String name;
    private final char gender;
    private final int age;
    private final String city;
    private final Date dob;

    public Candidate(int id, String name, char gender, int age, String city, Date dob) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public String toString(){
        return id + ", " + name + ", " + gender +
                ", " + age + ", " + city + ", " + new SimpleDateFormat("dd/MM/yyyy").format(dob);
    }
}
