package com.example.ilovetruyen.model;


import java.util.List;

public record Category(Integer id, String name, List<ComicDetail> comicDetails) {
}
