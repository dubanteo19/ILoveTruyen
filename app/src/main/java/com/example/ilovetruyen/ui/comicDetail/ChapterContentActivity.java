package com.example.ilovetruyen.ui.comicDetail;

import android.os.Bundle;
import android.widget.TextView;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        comicId = getIntent().getIntExtra("comicId", 1);
        count = getIntent().getIntExtra("count", 1);
        renderContentChapter();
    }

    private void renderContentChapter() {
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);
        recyclerView = findViewById(R.id.recycleChapterContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new ChapterContentAdapter(this);

        comicDetailAPI.getAllChapterById(comicId).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("-------------------------" + response.body());
                    recyclerView.setAdapter(recycleAdapter);
                    recycleAdapter.setData(response.body().get(count-1).contentImgList());
                    titleBar = findViewById(R.id.title_bar);
                    titleBar.setText("Chương "+response.body().get(count-1).count());
                }
            }
            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable throwable) {

            }
        });

    }

}