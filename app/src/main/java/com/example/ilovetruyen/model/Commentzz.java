package com.example.ilovetruyen.model;

import java.time.LocalDateTime;

public record Commentzz (Integer id, String text, User user, ComicDetail comicDetail, LocalDateTime createdDate){
    public Commentzz(String text, User user){
        this(null, text, user, null, null);
    }
}
