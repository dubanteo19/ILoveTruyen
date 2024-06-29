package com.example.ilovetruyen.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.PopupWindow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.admin.adapter.ComicManagerAdminAdapter;
import com.example.ilovetruyen.admin.adapter.UserManagerAdminAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.api.UserAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.User;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManagerActivity extends AppCompatActivity {
    private UserAPI userAPI;
    private RecyclerView recyclerView;
    private UserManagerAdminAdapter userManagerAdminAdapter;
    private RetrofitService retrofitService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_manager);

        retrofitService = new RetrofitService();
        userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        renderUserList();
    }
    private void renderUserList() {
        recyclerView = findViewById(R.id.user_manager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        userManagerAdminAdapter = new UserManagerAdminAdapter(this);
        recyclerView.setAdapter(userManagerAdminAdapter);
        userAPI.findAll().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                System.out.println(response.body()+"=======================");
                if(response.isSuccessful() && response.body() != null){
                    userManagerAdminAdapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call
                    , Throwable throwable) {

            }
        });

    }
}