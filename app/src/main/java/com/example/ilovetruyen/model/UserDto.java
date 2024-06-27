package com.example.ilovetruyen.model;

import java.io.Serializable;
import java.util.List;

public record UserDto(
        Integer id,
        String email,
        String password,
        String fullName)implements Serializable {
}
