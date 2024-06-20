package com.example.ilovetruyen.model;

import java.util.Date;

public record Comic(
        String name, int thumb, String chapter, int view, int like, Date createdDate
){
    public Comic(String name, int thumb) {
        this(name, thumb,"chapter 1",0,0,new Date());
    }
}
