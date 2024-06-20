package com.example.ilovetruyen.ui.comicDetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ChapterApdapter;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.model.Comic;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComicDetailActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Comic comic;

    private TextView name, titleNavTV;
    private ImageView thumb;

    private ExpandableTextView  expandTV;

    private RecyclerView recyclerView;


    private MaterialButton detailSeeChaptersBtn;

    private ChapterApdapter chapterApdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comic_detail);
        rederNavTop();
        renderSummaryComic();
        renderChapter();
        renderChapterList();

    }

    private void rederNavTop(){
        titleNavTV = findViewById(R.id.nav_top_title_name);
        titleNavTV.setText("");
        btnBack = findViewById(R.id.back_btn);
        btnBack.setOnClickListener(v->finish());
    }

    private void renderSummaryComic(){
        ExpandableTextView expandableTextView = findViewById(R.id.expandable_text_view);
        expandableTextView.setText("AppBarLayout also requires a separate scrolling sibling in order to know when to scroll. The binding is done through the AppBarLayout.ScrollingViewBehavior behavior class, meaning that you should set your scrolling view's behavior to be an instance of AppBarLayout.ScrollingViewBehavior. A string resource containing the full class name is available.");

    }

    private void renderChapter(){
        recyclerView = findViewById(R.id.detail_chapters);
        chapterApdapter = new ChapterApdapter(getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chapterApdapter);
        chapterApdapter.setData(getChapters());
    }

    public void renderChapterList(){
        detailSeeChaptersBtn = findViewById(R.id.detail_see_chapters_btn);
        detailSeeChaptersBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, ChapterListActivity.class);
            startActivity(intent);
        });
    }

    public static List<Chapter> getChapters () {
        var chapterList = new ArrayList<Chapter>();
        Chapter chapter1 = new Chapter("Chuong 1", new Date());
        Chapter chapter2 = new Chapter("Chuong 2", new Date());
        Chapter chapter3 = new Chapter("Chuong 3", new Date());
        chapterList.add(chapter1);
        chapterList.add(chapter2);
        chapterList.add(chapter3);
        chapterList.add(chapter1);
        chapterList.add(chapter2);
        chapterList.add(chapter3);
        chapterList.add(chapter1);
        chapterList.add(chapter2);
        chapterList.add(chapter3);
        chapterList.add(chapter1);
        chapterList.add(chapter2);
        chapterList.add(chapter3);
        return chapterList;
    }
}
