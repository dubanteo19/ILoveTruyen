package com.example.ilovetruyen;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.model.Comic;

import java.util.ArrayList;
import java.util.List;

public class ReadingHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reading_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        renderRecommendComicsSection();
    }
    private void renderRecommendComicsSection() {
        recyclerView = findViewById(R.id.history_reading);
        comicAdapter = new ComicAdapter(getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(comicAdapter);
        comicAdapter.setData(getHotComics());
    }
    private List<Comic> getHotComics () {
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