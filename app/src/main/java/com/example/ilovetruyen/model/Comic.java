package com.example.ilovetruyen.model;

import java.util.Date;
import java.time.LocalDateTime;

public record Comic(
        Integer id,String name, String thumbUrl, int latestChapter, int views, int likes, LocalDateTime createdDate
) {
}
