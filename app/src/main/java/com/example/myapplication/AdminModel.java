package com.example.myapplication;

public class AdminModel {
    private int id;
    private String name;
    private String user;
    private String pass;

    // Constructor with parameters
    public AdminModel(int id, String name, String user, String pass) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.pass = pass;
    }
    public AdminModel(String name, String user, String pass) {
        this.name = name;
        this.user = user;
        this.pass = pass;
    }

    // Default constructor
    public AdminModel() {
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

}
