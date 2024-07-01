package com.example.ilovetruyen.ui.comicDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ChapterContentAdapter;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.model.ContentImg;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterContentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView navTitle;
    private List<String> imageList;

    private RecyclerView recyclerView;
    private ChapterContentAdapter recycleAdapter;
    private RetrofitService retrofitService;
    private ComicDetailAPI comicDetailAPI;
    private int comicId;
    private int count;
    List<ContentImg> contentImgList;
    private TextView titleBar;
    private ImageView backBtn;
    private Button prevChapBut, nextChapBut;
    private List<Chapter> chapterList;
    private int chapterTotal = 0;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        Intent intent = getIntent();
        progressBar = findViewById(R.id.process_bar);
        if (intent.hasExtra("extra_comicsId")) {
            comicId = intent.getIntExtra("extra_comicsId", 1);
        } else {
            comicId = getIntent().getIntExtra("comicId", 1);
        }
        if (intent.hasExtra("extra_count")) {
            count = intent.getIntExtra("extra_count", 1);
        } else {
            count = getIntent().getIntExtra("count", 1);
        }
        if (intent.hasExtra("chapterTotal")) {
            chapterTotal = intent.getIntExtra("chapterTotal", 0);
        }
        renderContentChapter();
        backBtn = findViewById(R.id.chapter_backBtn);
        backBtn.setOnClickListener(v -> {
            finish();
        });

    }

    private void renderContentChapter() {
        progressBar.setVisibility(View.VISIBLE);
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);
        recyclerView = findViewById(R.id.recycleChapterContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new ChapterContentAdapter(this);
        prevChapBut = findViewById(R.id.prevChapButton);
        nextChapBut = findViewById(R.id.nextChapButton);
        comicDetailAPI.getAllChapterById(comicId).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    System.out.println("số lượng chapter ******" + response.body());
                    recyclerView.setAdapter(recycleAdapter);
                    recycleAdapter.setData(response.body().get(count - 1).contentImgList());
                    titleBar = findViewById(R.id.title_bar);
                    titleBar.setText("Chương " + response.body().get(count - 1).count());
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable throwable) {

            }
        });
        //set thực thi
        if (count == chapterTotal) { //xem chap mới nhất
            prevChapBut.setEnabled(true);
            nextChapBut.setEnabled(false);
        }
        if (chapterTotal == 0) { //trường hợp lỗi không chặn nút
            prevChapBut.setEnabled(false);
            nextChapBut.setEnabled(false);
        }
        if (count == 1) { // mới bắt đầu xem
            prevChapBut.setEnabled(false);
            nextChapBut.setEnabled(true);
        }
        if (1 < count && count < chapterTotal) { //trong khoảng 1 đến gần chap cuối
            prevChapBut.setEnabled(true);
            nextChapBut.setEnabled(true);
        }
        nextChapBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sau", Toast.LENGTH_SHORT).show();
                recreateActivityWithNewData(comicId, count + 1, chapterTotal);
            }
        });
        prevChapBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Trước", Toast.LENGTH_SHORT).show();
                recreateActivityWithNewData(comicId, count - 1, chapterTotal);


            }
        });
    }

    public void recreateActivityWithNewData(int newComicsId, int newCount, int chapterTotal) {
        // Tạo một Intent mới hoặc cập nhật Intent hiện tại
        Intent newIntent = new Intent(this, ChapterContentActivity.class);
        newIntent.putExtra("extra_comicsId", newComicsId);
        newIntent.putExtra("extra_count", newCount);
        newIntent.putExtra("chapterTotal", chapterTotal);

        // Thiết lập lại Intent cho Activity hiện tại
        setIntent(newIntent);

        // Gọi recreate() để tạo lại Activity với Intent mới
        recreate();
    }

    @Override
    public void recreate() {
        super.recreate();
    }
}