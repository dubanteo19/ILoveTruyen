package com.example.ilovetruyen.ui.comicDetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ChapterApdapter;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChapterApdapter chapterApdapter;
    private TextView titleNavTV;
    private ImageButton btnBack;
    private RetrofitService retrofitService;
    private ComicDetailAPI comicDetailAPI;
    private List<Chapter> chapterList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        rederNavTop();
        fetchData();
    }

    private void fetchData() {
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);
        Intent intent = getIntent();
        int comicId = intent.getIntExtra("comicId", -1);
        comicDetailAPI.getAllChapterById(comicId).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if(response.isSuccessful()){
                    chapterList = response.body();
                    renderChapterList();
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Failed to fetch data",Toast.LENGTH_SHORT);
            }
        });
    }

    private void rederNavTop(){
        titleNavTV = findViewById(R.id.nav_top_title_name);
        titleNavTV.setText("Danh sách chương");
        btnBack = findViewById(R.id.back_btn);
        btnBack.setOnClickListener(v->finish());
    }
    private void renderChapterList(){
        recyclerView = findViewById(R.id.chapter_list_detail);
        chapterApdapter = new ChapterApdapter(getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chapterApdapter);
        chapterApdapter.showAll();
        chapterApdapter.setData(chapterList);
    }

}
