package com.example.ilovetruyen.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMoreActivity extends AppCompatActivity {
    RetrofitService retrofitService;
    RecyclerView recyclerView;
    ComicAPI comicAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_more);
        retrofitService = new RetrofitService();
        comicAPI = retrofitService.getRetrofit().create(ComicAPI.class);
        recyclerView = findViewById(R.id.search_result_comics_rv);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int target = intent.getIntExtra("target",1);
        TextView titleTv = findViewById(R.id.search_title);
        titleTv.setText(title);
        renderData(target);
        setBackListener();
    }

    private void setBackListener() {
        ImageButton backBtn = findViewById(R.id.search_back_icon);
        backBtn.setOnClickListener(v->{
            finish();
        });
    }
    private void renderData(int target) {
        ComicAdapter comicAdapter = new ComicAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        Call<List<Comic>> call;
        switch (target){
            case 2 -> call = comicAPI.getAllRecommendationsComics();
            case 3 -> call = comicAPI.getAllHotComics();
            default -> call = comicAPI.getAllNewComics();
        }
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && !response.body().isEmpty()) {
                    recyclerView.setAdapter(comicAdapter);
                    comicAdapter.setData(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {

            }
        });
    }
}