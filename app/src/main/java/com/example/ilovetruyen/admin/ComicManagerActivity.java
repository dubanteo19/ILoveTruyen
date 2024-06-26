package com.example.ilovetruyen.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.HistoryAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicManagerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<Comic> comicList;
    private ComicAPI comicAPI;
    private RetrofitService retrofitService;
    private ComicDetailAPI comicDetailAPI;
    private Comic comic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comic_manager);
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);
        renderListComics();
    }

    private void renderListComics() {
        recyclerView = findViewById(R.id.history_reading);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        historyAdapter = new HistoryAdapter(this);
        comicAPI.getAllComics().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if(response.isSuccessful() && response.body() != null){
                    recyclerView.setAdapter(historyAdapter);
                    historyAdapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {

            }
        });

    }

}