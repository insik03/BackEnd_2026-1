package com.example.demo;

public class Article {
    private String description;

    public Article() {}

    public Article(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}