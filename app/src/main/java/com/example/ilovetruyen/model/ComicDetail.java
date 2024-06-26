package com.example.ilovetruyen.model;

import java.io.Serializable;
import java.util.List;
public record ComicDetail (
        Integer id, String description, Status status, Comic comic, List<Comment> comments, List<Category> categories
) implements Serializable {}
