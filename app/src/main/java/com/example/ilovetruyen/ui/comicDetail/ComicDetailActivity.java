package com.example.ilovetruyen.ui.comicDetail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ChapterApdapter;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.model.Comic;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComicDetailActivity extends AppCompatActivity {
    private ImageButton backBtn, heartBtn, saveBtn;
    private TextView name, titleNavTV;
    private ImageView thumb;

    private ExpandableTextView  expandTV;

    private RecyclerView recyclerView;
    private ChipGroup keywordSearch;


    private MaterialButton detailSeeChaptersBtn;

    private ChapterApdapter chapterApdapter;
    private ComicAdapter comicAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comic_detail);
        rederNavTop();
        renderKeywords();
        renderSummaryComic();
        renderChapter();
        renderChapterList();
        renderSimilarComics();
        heartEvent();
    }

    private void rederNavTop(){
        titleNavTV = findViewById(R.id.nav_top_title_name);
        titleNavTV.setText("");
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v->finish());
    }

    /* Summary comic*/
    private void renderSummaryComic(){
        ExpandableTextView expandableTextView = findViewById(R.id.expandable_text_view);
        expandableTextView.setText("AppBarLayout also requires a separate scrolling sibling in order to know when to scroll. The binding is done through the AppBarLayout.ScrollingViewBehavior behavior class, meaning that you should set your scrolling view's behavior to be an instance of AppBarLayout.ScrollingViewBehavior. A string resource containing the full class name is available.");
    }

    /* category keywords*/
    public void renderKeywords(){
        keywordSearch = findViewById(R.id.detail_keyword_search);
        for (String key:getStrings()) {
            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.button_keyword, keywordSearch, false);
            chip.setText(key);
            keywordSearch.addView(chip);
        }
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
    private void renderSimilarComics() {

        recyclerView = findViewById(R.id.detail_similar_categories);
        comicAdapter = new ComicAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(comicAdapter);
        comicAdapter.setData(getHotComics());
    }

    private void heartEvent(){
        heartBtn = findViewById(R.id.detail_heartBtn);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_heart_detail);
        if (drawable != null) {
            int width = 120; // Chiều rộng mong muốn
            int height = 120; // Chiều cao mong muốn
            System.out.println("Thay doi icon");
            drawable.setBounds(0, 0, width, height);
            heartBtn.setImageDrawable(drawable);
        }
        // event
        heartBtn.setOnClickListener(v -> {
            Drawable currentDrawable = heartBtn.getDrawable();
            if (currentDrawable != null) {
                currentDrawable.setColorFilter(ContextCompat.getColor(this, R.color.secondary), PorterDuff.Mode.SRC_IN);
            }
        });
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

    private List<String> getStrings(){
        var categoryList = new ArrayList<String>();
        categoryList.add("Mangan");
        categoryList.add("Manhua");
        categoryList.add("Drama");
        categoryList.add("Action");
        categoryList.add("Anime");
        return categoryList;
    }
}
