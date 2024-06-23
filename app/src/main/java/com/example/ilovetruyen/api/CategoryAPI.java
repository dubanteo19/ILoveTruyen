package com.example.ilovetruyen.api;

import com.example.ilovetruyen.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPI {
    @GET("/api/v1/categories")
    Call<List<Category>> findAllCategories();
}
