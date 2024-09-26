package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class SecurityModel {
    private int id;
    private String name;
    private String user;
    private String pass;

    // Constructor with parameters
    public SecurityModel(int id, String name, String user, String pass) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.pass = pass;
    }

    // Default constructor
    public SecurityModel() {
    }

    // Override toString for easier debugging
    @Override
    public String toString() {
        return "SecurityModel{" +
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
    public List<SecurityModel> getTeachers() {
        List<SecurityModel> securities = new ArrayList<>();
        securities.add(new SecurityModel(1, "Thabo", "thabo@gmail.com", "sec123"));
        securities.add(new SecurityModel(2, "Sipho", "sipho@gmail.com", "sec123"));
        return securities;
    }
}
