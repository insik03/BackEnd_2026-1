package com.example.demo;

public class UserResponse {
    private String age;
    private String name;

    public UserResponse(String age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getAge() { return age; }
    public String getName() { return name; }
}