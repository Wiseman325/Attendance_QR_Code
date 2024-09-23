package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    private int id;
    private String name;
    private String user;
    private String pass;


    // Constructor with 'id' (for existing students)
    public StudentModel(int id, String name, String user, String pass) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.pass = pass;
    }

    // Default constructor
    public StudentModel() {
    }

    // Override toString to display StudentModel details
    @Override
    public String toString() {
        return "StudentModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    // Test method to simulate fetching a list of students
    public List<StudentModel> getStudents() {
        List<StudentModel> students = new ArrayList<>();
        students.add(new StudentModel(1, "John", "john123", "123"));
        students.add(new StudentModel(2, "Alice", "alice456", "123"));
        students.add(new StudentModel(3, "Bob", "bob789", "123"));
        students.add(new StudentModel(4, "Emily", "emily321", "123"));

        return getStudents();
    }
}
