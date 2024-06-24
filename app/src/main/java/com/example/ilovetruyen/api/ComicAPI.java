package com.example.ilovetruyen.api;

import com.example.ilovetruyen.model.Comic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ComicAPI {
    @GET("/api/v1/comics")
    Call<List<Comic>> getAllComics();
    @GET("/api/v1/comics/recomendations")
    Call<List<Comic>> getAllRecommendationsComics();
    @GET("/api/v1/comics/recomendations")
    Call<List<Comic>> getAllHotComics();
    @GET("/api/v1/comics/latest")
    Call<List<Comic>> getAllNewComics();
    @GET("/api/v1/comics/category/{id}")
    Call<List<Comic>> getAllComicsByCategoryId(@Path("id") Integer categoryIds);
    @GET("/api/v1/comics/search/{name}")
    Call<List<Comic>> searchComicsByName(@Path("name") String searchValue);

}
