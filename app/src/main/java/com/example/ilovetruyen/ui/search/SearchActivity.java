package com.example.ilovetruyen.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.model.Comic;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ImageButton backBtn, searchBtn;
    RecyclerView recyclerView;
    ComicAdapter comicAdapter;
    LinearLayout layoutNotFound;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comic);
        searchBtn = findViewById(R.id.search_search_btn);
        backBtn = findViewById(R.id.search_back_icon);
        layoutNotFound = findViewById(R.id.search_layout_not_found);
        backBtn.setOnClickListener(v -> finish());

        renderResult();
    }

    private void renderResult() {

        searchBtn.setOnClickListener(v -> {
            List<Comic> results = getHotComics();//Danh sach kết quả tìm kiếm
            if (results != null) {
                recyclerView = findViewById(R.id.search_result_comics);
                comicAdapter = new ComicAdapter(getApplicationContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(comicAdapter);
                comicAdapter.setData(results);
                recyclerView.setVisibility(View.VISIBLE);
                layoutNotFound.setVisibility(View.GONE);
            }else{
                recyclerView.setVisibility(View.GONE);
                layoutNotFound.setVisibility(View.VISIBLE);
            }
        });

    }

    private List<Comic> getHotComics() {
        var hotComics = new ArrayList<Comic>();
        var comic1 = new Comic("One Piece", R.drawable.one_piece);
        var comic2 = new Comic("Thanh Guong diet quy", R.drawable.thanh_guom_diet_quy);
        var comic3 = new Comic("One Punchman", R.drawable.one_puch_man);
        hotComics.add(comic1);
        hotComics.add(comic2);
        hotComics.add(comic3);
        hotComics.add(comic1);
        hotComics.add(comic2);
        hotComics.add(comic3);
        hotComics.add(comic1);
        hotComics.add(comic2);
        hotComics.add(comic3);
        return hotComics;
    }

}
