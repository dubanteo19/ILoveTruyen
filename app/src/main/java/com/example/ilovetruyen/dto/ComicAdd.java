package com.example.ilovetruyen.dto;

public record ComicAdd(
        String name,
        String thumbUrl,
        String description,
        java.util.Set<Integer> categoriesId
) {
}
