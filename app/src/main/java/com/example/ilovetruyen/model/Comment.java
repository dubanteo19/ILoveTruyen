package com.example.ilovetruyen.model;

import java.time.LocalDateTime;
// Comment.java
public record Comment ( Integer id,
                        String text,
                        UserDto user,
                        ComicDetail comicDetail,
                        LocalDateTime createdDate){
}
