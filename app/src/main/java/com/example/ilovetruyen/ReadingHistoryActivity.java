package com.example.ilovetruyen;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.example.ilovetruyen.ui.home.RecentlyReadFragment;

import java.util.ArrayList;
import java.util.List;

public class ReadingHistoryActivity extends AppCompatActivity {
    private Context context;
    private List<Comic> comicList;
    RetrofitService retrofitService;
    ComicAPI comicAPI;
    public void setData(List<Comic> comics) {
        this.comicList = comics;

    }


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
//        recyclerView = findViewById(R.id.history_reading);
//        comicAdapter = new ComicAdapter(getApplicationContext());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(comicAdapter);
//        comicAdapter.setData(getHotComics());
    }

    private void renderReadingSection(View root) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.history_fragment_read_comics, RecentlyReadFragment.newInstance());
        fragmentTransaction.commit();
    }
}