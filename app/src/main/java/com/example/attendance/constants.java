package com.example.attendance;

public class constants {
    private String name;
    private String course;
    private String username;
    private String password;
    private String typeuser;

    public constants() {

    }
    public constants(String name, String course, String username , String password, String typeuser) {
        this.name = name;
        this.course = course;
        this.username = username;
        this.password = password;
        this.typeuser = typeuser;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return  password;
    }
    public String getName() {
        return name;
    }
    public String getCourse() {
        return course;
    }
    public String getTypeuser() {
        return typeuser;
    }
}
