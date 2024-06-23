package com.example.ilovetruyen.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.adapter.NewComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {
    RetrofitService retrofitService;
    ComicAPI comicAPI;
    private NewComicAdapter comicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitService = new RetrofitService();
        comicAPI = retrofitService.getRetrofit().create(ComicAPI.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_result);
        renderData(savedInstanceState);
    }

    private void renderData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        TextView searchTitleTv = findViewById(R.id.search_title);
        searchTitleTv.setText(intent.getStringExtra("title"));
        int categoryId = intent.getIntExtra("categoryId", 0);
        RecyclerView recyclerView = findViewById(R.id.search_result_comics_rv);
        comicAdapter = new NewComicAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        comicAPI.getAllComicsByCategoryId(categoryId).enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if(response.isSuccessful()){
                    recyclerView.setAdapter(comicAdapter);
                    comicAdapter.setData(response.body());
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {

            }
        });

    }

    private void hideLoading() {
        ProgressBar progressBar = findViewById(R.id.process_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }
}