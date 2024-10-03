package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class TeacherModel {
    private int id;
    private String name;
    private String user;
    private String email;  // New field for teacher email
    private String pass;

    // Constructor with parameters
    public TeacherModel(int id, String name, String user, String email, String pass) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.email = email; // Added email field
        this.pass = pass;
    }
    public TeacherModel(String name, String user, String email, String pass) {
        this.name = name;
        this.user = user;
        this.email = email; // Added email field
        this.pass = pass;
    }

    // Default constructor
    public TeacherModel() {
    }

    // Override toString for easier debugging
    @Override
    public String toString() {
        return "TeacherModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", email='" + email + '\'' + // Added email to the string representation
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    // Temporary method for testing purposes to return a list of teachers
//    public List<TeacherModel> getTeachers() {
//        List<TeacherModel> teachers = new ArrayList<>();
//        teachers.add(new TeacherModel(1, "Kamil", "kamil123", "kamil@example.com", "123"));
//        teachers.add(new TeacherModel(2, "Riad", "riad456", "riad@example.com", "123"));
//        teachers.add(new TeacherModel(3, "Ahmed", "ahmed24", "ahmed@example.com", "123"));
//        teachers.add(new TeacherModel(4, "Ali", "ali322", "ali@example.com", "123"));
//
//        return teachers;
//    }
}
