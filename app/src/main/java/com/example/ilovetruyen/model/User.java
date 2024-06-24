package com.example.ilovetruyen.model;

import java.util.List;

public record User (Integer id, String email, String password, String fullName, List<Commentzz> comments){
    public User(Integer id, String fullName) {
        this(id, null, null, fullName, null);
    }
}
