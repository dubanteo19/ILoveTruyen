package com.example.ilovetruyen;

import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.adapter.ChapterContentAdapter;
import com.example.ilovetruyen.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterContentFragment extends AppCompatActivity {
    Toolbar toolbar;
    private Chapter chapter;
    private RecyclerView recyclerView;
    private ChapterContentAdapter recycleAdapter;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);

        recyclerView = findViewById(R.id.recycleChapterContent);
        toolbar = findViewById(R.id.chapter_content_toolbar);

        createChapter();
        recycleAdapter = new ChapterContentAdapter(this,chapter);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        actionToolBar();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createChapter() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.c0);
        imageList.add(R.drawable.c2);
        imageList.add(R.drawable.c2);
        imageList.add(R.drawable.c3);

        chapter= new Chapter(1,"chapter 1",imageList);

    }


}