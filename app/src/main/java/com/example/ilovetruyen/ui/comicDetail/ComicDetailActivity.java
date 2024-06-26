package com.example.ilovetruyen.ui.comicDetail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ChapterApdapter;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.ComicDetail;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.example.ilovetruyen.ui.StatusHelper;
import com.example.ilovetruyen.ui.comments.CommentFragment;
import com.example.ilovetruyen.ui.search.SearchResultActivity;
import com.example.ilovetruyen.util.TimeDifference;
import com.example.ilovetruyen.util.UserStateHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDetailActivity extends AppCompatActivity {
    private ImageButton backBtn, heartBtn, saveBtn;
    private TextView titleNavTV, comicName, authorName, likes, views, createdAt, status, chapterLength;
    private ImageView thumb;
    private RecyclerView recyclerView;
    private ChipGroup keywordSearch;
    private MaterialButton detailSeeChaptersBtn;
    private ChapterApdapter chapterApdapter;
    private ComicDetailAPI comicDetailAPI;
    private RetrofitService retrofitService;
    private ComicDetail comicDetail;
    private Comic comic;
    private List<Chapter> chapterList;
    private boolean isChecked;
    private int chapterId = 1;
    private int comicId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        comicId = getIntent().getIntExtra("comicId",1);
        // Khoi tai dich vu retrofit
        UserStateHelper.saveReadComicId(getApplicationContext(),comicId);
        fetchComicDetail(comicId);

        attachCommentFragment(comicId);
    }

    private void fetchComicDetail(int comicId) {
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);
        comicDetailAPI.getComicDetailById(comicId).enqueue(new Callback<ComicDetail>() {
            @Override
            public void onResponse(Call<ComicDetail> call, Response<ComicDetail> response) {
                if (response.isSuccessful()) {
                    comicDetail = response.body();
                    comic = comicDetail.comic();
                    renderComicDetail();
                }
            }

            @Override
            public void onFailure(Call<ComicDetail> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
            }
        });
        comicDetailAPI.getAllChapterById(comicId).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful()) {
                    chapterList = response.body();
                    renderChapter();
                    renderChapterList();
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
            }
        });
    }

    private void renderComicDetail() {
        renderNavTop();
        renderInfomation();
        renderKeywords();
        renderSummaryComic();
        renderComments();
        renderSimilarComics();
        heartEvent();
        continueReadingEvent();
    }

    private void continueReadingEvent() {
        LinearLayout view = findViewById(R.id.detail_continue_reading_btn);
        view.setOnClickListener(v->{
            Intent intent = new Intent(this, ChapterContentActivity.class);
            intent.putExtra("comicId", comic.id());
            intent.putExtra("chapterId", chapterId);
            startActivity(intent);
        });
    }

    // Hien thi thong tin chinh cua truyen
    private void renderInfomation() {
        // set thumb
        thumb = findViewById(R.id.detail_comic_thumb);
        Glide.with(this).load(comic.thumbUrl()).into(thumb);
        // set comic name
        comicName = findViewById(R.id.detail_name_comic);
        comicName.setText(comic.name());
        // set author name
        authorName = findViewById(R.id.detail_author_name);
        authorName.setText("Đang cập nhật");

        // set likes
        likes = findViewById(R.id.detail_likes);
        likes.setText(String.valueOf(comic.likes()));

        // set views
        views = findViewById(R.id.detail_views);
        views.setText(String.valueOf(comic.views()));

        // set date
        createdAt = findViewById(R.id.detail_created_at);
        createdAt.setText(TimeDifference.getTimeDifference(comic.createdDate()));

        // set status
        status = findViewById(R.id.detail_processing);
        status.setText(StatusHelper.getStatusName(comicDetail.status()));

    }


    private void renderNavTop() {
//        titleNavTV = findViewById(R.id.nav_top_title_name);
//        titleNavTV.setText("");
//        backBtn = findViewById(R.id.back_btn);
//        backBtn.setOnClickListener(v -> finish());
    }

    /* Summary comic*/

    private void renderSummaryComic() {
        ExpandableTextView expandableTextView = findViewById(R.id.expandable_text_view);
        expandableTextView.setText(comicDetail.description());
    }
    private void renderComments() {
    }

    /* category keywords*/
    public void renderKeywords() {
        keywordSearch = findViewById(R.id.detail_keyword_search);
        for (Category category : comicDetail.categories()) {
            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.button_keyword, keywordSearch, false);
            chip.setText(category.name());
            keywordSearch.addView(chip);
            chip.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                intent.putExtra("title",category.name());
                intent.putExtra("categoryId",category.id());
                startActivity(intent);
            });
        }
    }

    private void renderChapter() {
        chapterLength = findViewById(R.id.detail_chapterLength);
        chapterLength.setText(String.valueOf(chapterList.size())+ " chương");
        final int MAX_CHAPTER = 5;
        recyclerView = findViewById(R.id.detail_chapters);
        chapterApdapter = new ChapterApdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chapterApdapter);
        chapterApdapter.setData(chapterList.stream().limit(MAX_CHAPTER).collect(Collectors.toList()));
    }

    public void renderChapterList() {
        detailSeeChaptersBtn = findViewById(R.id.detail_see_chapters_btn);
        detailSeeChaptersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChapterListActivity.class);
            intent.putExtra("comicId", comicId);
            startActivity(intent);
        });
    }

    private void renderSimilarComics() {

//        recyclerView = findViewById(R.id.detail_similar_categories);
//        comicAdapter = new ComicAdapter(this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(comicAdapter);
//        comicAdapter.setData(getHotComics());
    }

    private void heartEvent() {
        heartBtn = findViewById(R.id.detail_heartBtn);
        Drawable drawable = heartBtn.getDrawable();

        // Cap nhat trang thai ban dau cua nut
        updateColorButton(drawable);

        // event
        heartBtn.setOnClickListener(v -> {
            System.out.println("check --------------------------------");
            isChecked = !isChecked; // Thay đổi trạng thái
            updateHeartButtonOnClick(); // Cập nhật nút dựa trên trạng thái mới
            updateColorButton(drawable);
        });
    }
    private void updateColorButton(Drawable drawable){
        if (isChecked) {
            drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.secondary), PorterDuff.Mode.SRC_IN);
        } else {
            drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.text), PorterDuff.Mode.SRC_IN);
        }
    }

    private void updateHeartButtonOnClick() {
        likes = findViewById(R.id.detail_likes);
        if (isChecked) {
            comicDetailAPI.like(comic.id()).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        likes.setText(String.valueOf(response.body()));
                        System.out.println("check --------------------------------true " + response.body());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Failed to like", Toast.LENGTH_SHORT);
                }
            });
        } else {
            comicDetailAPI.like(comic.id()).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        likes.setText(String.valueOf(response.body()));
                        System.out.println("check --------------------------------false" + response.body());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Failed to unlike", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    private void attachCommentFragment(int comicId) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_comments, CommentFragment.newInstance(comicId, comicDetail))
                .commit();
    }

}
