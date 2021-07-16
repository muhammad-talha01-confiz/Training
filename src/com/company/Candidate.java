package com.company;

//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Candidate {
    // CSV Format: ID, Name, Gender, Age, City, DOB
    private int id;
    private String name;
    private char gender;
    private int age;
    private String city;
    private Date dob;

    public Candidate(int id, String name, char gender, int age, String city, Date dob) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.dob = dob;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void  setGender(char gender) {
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDob() {
        return dob;
    }

    public String toString(){
        return id + ", " + name + ", " + gender +
                ", " + age + ", " + city + ", " + new SimpleDateFormat("dd/MM/yyyy").format(dob);
    }
}
