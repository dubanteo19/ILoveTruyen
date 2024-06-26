package com.example.ilovetruyen.api;

import com.example.ilovetruyen.dto.GoogleUserDTO;
import com.example.ilovetruyen.dto.UserRegister;
import com.example.ilovetruyen.dto.UserUpdate;
import com.example.ilovetruyen.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("/api/v1/users/register")
    Call<User> register(@Body UserRegister userRegister);

    @POST("/api/v1/users/login")
    Call<User> login(@Body UserRegister userRegister);

    @POST("/api/v1/users/login-google")
    Call<User> loginGoogle(@Body GoogleUserDTO googleUserDTO);

    @PUT("/api/v1/users/{id}")
    Call<User> update(@Path("id") int id, @Body UserUpdate userUpdate);

    @POST("/api/v1/users/forgot-password/{email}")
    Call<String> forgotPassword(@Path("email") String email);

    @GET("/api/v1/users")
    Call<List<User>> findAll();
}
