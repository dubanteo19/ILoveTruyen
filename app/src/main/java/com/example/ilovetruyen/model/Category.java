package com.example.ilovetruyen.model;

public class Category {
    private String name;
    private String description;
    private int background;

    public Category(String name, String description, int background) {
        this.name = name;
        this.description = description;
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
