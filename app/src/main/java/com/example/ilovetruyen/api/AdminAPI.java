package com.example.ilovetruyen.api;

import com.example.ilovetruyen.dto.DashBoardDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminAPI {
    @GET("api/v1/admin")
    Call<DashBoardDTO> getDashboard();
}
