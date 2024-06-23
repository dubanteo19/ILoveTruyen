package com.example.ilovetruyen.model;

import java.util.List;

public record Chapter (Integer id, int count, ComicDetail comicDetail, List<ContentImg> contentImgs){
}
