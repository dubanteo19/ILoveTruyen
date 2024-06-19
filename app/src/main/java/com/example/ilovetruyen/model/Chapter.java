package com.example.ilovetruyen.model;

import java.util.List;

//Đây là model cho các chapter của truyện
public class Chapter {
    private int id;
    private String name; // tên của chapter vd : chapter1, volumn1,...
    private List<Integer> imageList; //danh sách các ảnh theo từng chapter
    //NOTE : chapter có thể có thêm createAt ??
    public Chapter() {
    }

    public Chapter(int id, String name, List<Integer> imageList) {
        this.id = id;
        this.name = name;
        this.imageList = imageList;
    }

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

    public List<Integer> getImageList() {
        return imageList;
    }

    public void setImageList(List<Integer> imageList) {
        this.imageList = imageList;
    }
}
