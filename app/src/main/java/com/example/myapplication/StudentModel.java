package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    private int id;
    private String name;
    private String user;
    private String pass;
    private String email;  // New field for student email
    private String parentEmail;  // New field for parent email

    // Constructor with 'id', student email, and parent email (for existing students)
    public StudentModel(int id, String name, String user, String pass, String email, String parentEmail) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.parentEmail = parentEmail;
    }

    // Constructor without 'pass' for flexibility
    public StudentModel(int id, String name, String user, String email, String parentEmail) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.email = email;
        this.parentEmail = parentEmail;
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
                ", email='" + email + '\'' +
                ", parentEmail='" + parentEmail + '\'' +
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    // Test method to simulate fetching a list of students
    public List<StudentModel> getStudents() {
        List<StudentModel> students = new ArrayList<>();
        students.add(new StudentModel(1, "John", "john123", "123", "john@example.com", "wisemanmlora@gmail.com"));
        students.add(new StudentModel(2, "Alice", "alice456", "123", "alice@example.com", "soundavecentral01@gmail.com"));
        students.add(new StudentModel(3, "Bob", "bob789", "123", "bob@example.com", "parentbob@example.com"));
        students.add(new StudentModel(4, "Emily", "emily321", "123", "emily@example.com", "parentemily@example.com"));

        return students;
    }
}
