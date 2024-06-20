package com.example.ilovetruyen.ui.comicDetail;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ChapterApdapter;

public class ChapterListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChapterApdapter chapterApdapter;
    private TextView name, titleNavTV;
    private ImageButton btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        rederNavTop();
        renderChapterList();
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
        chapterApdapter.setData(ComicDetailActivity.getChapters());
    }

}
