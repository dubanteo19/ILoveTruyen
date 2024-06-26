package com.example.ilovetruyen.model;

import java.time.LocalDateTime;
// Comment.java
public record Comment ( Integer id,
                        String text,
                        User user,
                        ComicDetail comicDetail,
                        LocalDateTime createdDate){
}
