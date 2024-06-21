package com.example.ilovetruyen.model;

import java.util.Date;
import java.util.List;

public record Chapter (int id, String chapterName, Date craeteAt, List<String> images){
    public Chapter(String chapterName, Date craeteAt){
        this(1, chapterName, craeteAt, null);
    }
}
