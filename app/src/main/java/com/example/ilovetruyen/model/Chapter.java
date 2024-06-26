package com.example.ilovetruyen.model;

import java.time.LocalDateTime;
import java.util.List;

public record Chapter (Integer id, int count, ComicDetail comicDetail, List<ContentImg> contentImgList, LocalDateTime createdDate){
}
