package com.example.ilovetruyen.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.adapter.NewComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    ImageButton backBtn, searchBtn;
    RecyclerView recyclerView;
    NewComicAdapter comicAdapter;
    LinearLayout layoutNotFound;
    ProgressBar progressBar;
    RetrofitService retrofitService;
    ComicAPI comicAPI;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comic);
        searchBtn = findViewById(R.id.search_search_btn);
        backBtn = findViewById(R.id.search_back_icon);
        progressBar = findViewById(R.id.process_bar);
        layoutNotFound = findViewById(R.id.search_layout_not_found);
        backBtn.setOnClickListener(v -> finish());
        retrofitService = new RetrofitService();
        comicAPI =  retrofitService.getRetrofit().create(ComicAPI.class);


        recyclerView = findViewById(R.id.search_result_comics);
        comicAdapter = new NewComicAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        searchBtn.setOnClickListener(this::doSearch);


    }

    private void doSearch(View v) {
        progressBar.setVisibility(View.VISIBLE);
        layoutNotFound.setVisibility(View.INVISIBLE);
        EditText searchEditText = findViewById(R.id.search_edit_text);
        String searchValue = searchEditText.getText().toString();
        comicAdapter.setData(new ArrayList<>());
        comicAPI.searchComicsByName(searchValue).enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful() && !response.body().isEmpty()) {
                    recyclerView.setAdapter(comicAdapter);
                    comicAdapter.setData(response.body());
                }
                else{
                    handleNotFound();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {
                handleNotFound();
            }
        });
    }

    private void handleNotFound() {
        TextView textView= layoutNotFound.findViewById(R.id.search_notification_title);
        textView.setText("Không tìm thấy truyện!");
        layoutNotFound.setVisibility(View.VISIBLE);
    }
}
