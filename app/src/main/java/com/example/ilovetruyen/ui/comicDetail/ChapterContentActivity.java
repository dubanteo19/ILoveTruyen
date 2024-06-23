package com.example.ilovetruyen.ui.comicDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ChapterContentAdapter;
import com.example.ilovetruyen.model.Chapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChapterContentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView navTitle;
    private Chapter chapter;
    private List<String> imageList;

    private RecyclerView recyclerView;
    private ChapterContentAdapter recycleAdapter;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);

        recyclerView = findViewById(R.id.recycleChapterContent);
        toolbar = findViewById(R.id.chapter_content_toolbar);
        renderNavTop();
        createChapter();
        recycleAdapter = new ChapterContentAdapter(this,chapter, imageList);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        actionToolBar();
    }
    private void renderNavTop(){
        navTitle = findViewById(R.id.nav_top_chapter_name);
        navTitle.setText(chapter.chapterName());
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
        imageList = new ArrayList<>();
        imageList.add(String.valueOf(R.drawable.c0));
        imageList.add(String.valueOf(R.drawable.c2));
        imageList.add(String.valueOf(R.drawable.c2));
        imageList.add(String.valueOf(R.drawable.c3));

        chapter= new Chapter(1, "chapter 1", new Date("23-12-2004"), imageList);

    }


}