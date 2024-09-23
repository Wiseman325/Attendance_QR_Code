package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class TeacherModel {
    private int id;
    private String name;
    private String user;
    private String pass;

    // Constructor with parameters
    public TeacherModel(int id, String name, String user, String pass) {
        this.id = id;
        this.name = name;
        this.user = user;
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

    // Temporary method for testing purposes to return a list of teachers
    public List<TeacherModel> getTeachers() {
        List<TeacherModel> teachers = new ArrayList<>();
        teachers.add(new TeacherModel(1, "Kamil", "kamil123", "123"));
        teachers.add(new TeacherModel(2, "Riad", "riad456", "123"));
        teachers.add(new TeacherModel(3, "Ahmed", "ahmed24", "123"));
        teachers.add(new TeacherModel(4, "Ali", "ali322", "123"));

        return teachers;
    }
}
