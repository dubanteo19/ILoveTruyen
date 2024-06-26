package com.example.ilovetruyen.ui.comicDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.ComicDetail;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.example.ilovetruyen.ui.comments.CommentFragment;
import com.example.ilovetruyen.ui.search.SearchResultActivity;
import com.example.ilovetruyen.util.StatusHelper;
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
    private ImageButton heartBtn;
    private TextView comicName, authorName, likes, views, createdAt, status, chapterLength;
    private ImageView thumb;
    private ChipGroup keywordSearch;
    private MaterialButton detailSeeChaptersBtn;
    private ChapterApdapter chapterApdapter;
    private ComicDetailAPI comicDetailAPI;
    private RetrofitService retrofitService;
    private ComicDetail comicDetail;
    private Comic comic;
    private ComicAdapter comicAdapter;
    private List<Chapter> chapterList;
    private boolean isChecked;
    private int comicId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        comicId = getIntent().getIntExtra("comicId", 1);
        UserStateHelper.saveRecentlyReadComicId(getApplicationContext(), comicId);
        System.out.println(comicId + "++++++++++++++++++++++++++++++++++++++++++++");
        heartBtn = findViewById(R.id.detail_heartBtn);
        UserStateHelper.saveReadComicId(getApplicationContext(), comicId);
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
                    continueReadingEvent();
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
            }
        });
    }

    private void renderComicDetail() {
        renderInfomation();
        renderKeywords();
        renderSummaryComic();
        renderSimilarComics();
        heartEvent();
    }

    private void continueReadingEvent() {
        LinearLayout view = findViewById(R.id.detail_continue_reading_btn);
        if(chapterList.size() != 0) {
            view.setOnClickListener(v -> {
                Intent intent = new Intent(this, ChapterContentActivity.class);
                intent.putExtra("comicId", comic.id());
                intent.putExtra("count", 1);
                startActivity(intent);
            });
        }else{
            view.setVisibility(View.GONE);
        }
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

        comicDetailAPI.increaseViews(comicId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                // set views
                if (response.isSuccessful()) {
                    views = findViewById(R.id.detail_views);
                    views.setText(String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {

            }
        });

        // set date
        createdAt = findViewById(R.id.detail_created_at);
        createdAt.setText(TimeDifference.formatTimeOnChapter(comic.createdDate()));

        // set status
        status = findViewById(R.id.detail_processing);
        status.setText(StatusHelper.getStatusName(comicDetail.status()));

    }

    /* Summary comic*/
    private void renderSummaryComic() {
        ExpandableTextView expandableTextView = findViewById(R.id.expandable_text_view);
        expandableTextView.setText(comicDetail.description());
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
                intent.putExtra("title", category.name());
                intent.putExtra("categoryId", category.id());
                startActivity(intent);
            });
        }
    }

    private void renderChapter() {
        chapterLength = findViewById(R.id.detail_chapterLength);
        chapterLength.setText(String.valueOf(chapterList.size()) + " chương");
        final int MAX_CHAPTER = 5;
        RecyclerView recyclerView = findViewById(R.id.detail_chapters);
        chapterApdapter = new ChapterApdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chapterApdapter);
        System.out.println("Kiem tra chapters "+chapterList);
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
        RecyclerView recyclerView  = findViewById(R.id.detail_similar_categories);
        comicAdapter = new ComicAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        comicDetailAPI.getAllComicsByCategoryId(comicDetail.categories().get(0).id()).enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful()) {
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(comicAdapter);
                    comicAdapter.setData(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {

            }
        });
    }

    private void heartEvent() {
        Drawable drawable = heartBtn.getDrawable();
        // Cap nhat trang thai ban dau cua nut
        updateColorButton(drawable);

        // event
        heartBtn.setOnClickListener(v -> {
            isChecked = !isChecked; // Thay đổi trạng thái khi nhấn nút
            updateHeartButtonOnClick(drawable); // Cập nhật nút dựa trên trạng thái mới
        });
    }

    private void updateColorButton(Drawable drawable) {
        SharedPreferences sharedPreferences = getSharedPreferences(UserStateHelper.PREF_NAME, Context.MODE_PRIVATE);
        int isComicId = sharedPreferences.getInt(UserStateHelper.LIKE_STATUS + comicId, -1);
        isChecked = (isComicId == comicId);
        if (isChecked) {
            drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.secondary), PorterDuff.Mode.SRC_IN);
        } else {
            drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.text), PorterDuff.Mode.SRC_IN);
        }
    }

    private void updateHeartButtonOnClick(Drawable drawable) {
        likes = findViewById(R.id.detail_likes);

        if (isChecked) {
            comicDetailAPI.like(comicId).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        likes.setText(String.valueOf(response.body()));
                        UserStateHelper.saveLikeStatus(getApplicationContext(), comicId);
                        updateColorButton(drawable); // Cập nhật màu sắc nút sau khi like thành công
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Failed to like", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            comicDetailAPI.unlike(comicId).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        likes.setText(String.valueOf(response.body()));
                        UserStateHelper.removeLikeStatus(getApplicationContext(), comicId);
                        updateColorButton(drawable); // Cập nhật màu sắc nút sau khi unlike thành công
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Failed to unlike", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void attachCommentFragment(int comicId) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_comments, CommentFragment.newInstance(comicId))
                .commit();
    }

}
