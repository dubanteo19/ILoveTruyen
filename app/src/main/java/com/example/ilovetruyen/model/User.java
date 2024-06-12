package com.example.ilovetruyen.model;

public class User {
    private int resouceImage;
    private String name;

    public User(int resouceImage, String name) {
        this.resouceImage = resouceImage;
        this.name = name;
    }

    public int getResouceImage() {
        return resouceImage;
    }

    public void setResouceImage(int resouceImage) {
        this.resouceImage = resouceImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
