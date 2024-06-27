package com.example.ilovetruyen.api;

import com.example.ilovetruyen.dto.UserRegister;
import com.example.ilovetruyen.dto.UserUpdate;
import com.example.ilovetruyen.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("/api/v1/users/register")
    Call<User> register(@Body UserRegister userRegister);

    @POST("/api/v1/users/login")
    Call<User> login(@Body UserRegister userRegister);

    @PUT("/api/v1/users/{id}")
    Call<User> update(@Path("id") int id, @Body UserUpdate userUpdate);

    @POST("/api/v1/users/forgot-password/{email}")
    Call<String> forgotPassword(@Path("email") String email);
}
