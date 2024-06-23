package com.example.ilovetruyen.model;


import java.io.Serializable;
import java.util.List;

public record User(
        Integer id,
        String email,
        String password,
        String fullName,
        List<Comment> commentList) implements Serializable {
}
