package com.example.ilovetruyen.api;

import com.example.ilovetruyen.dto.CategoryDTO;
import com.example.ilovetruyen.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryAPI {
    @GET("/api/v1/categories")
    Call<List<Category>> findAllCategories();
    @POST("/api/v1/categories")
    Call<Boolean>  save(@Body CategoryDTO categoryDTO);
}
