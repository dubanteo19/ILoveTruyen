package com.example.ilovetruyen.api;

import com.example.ilovetruyen.model.ComicDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ComicDetailAPI {
    @GET("/api/v1/comic-detail/{id}")
    Call<ComicDetail> getComicDetailById(@Path("id") int id);
}
