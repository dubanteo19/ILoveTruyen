package com.example.ilovetruyen;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.adapter.HistoryAdapter;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.ComicDetail;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.example.ilovetruyen.util.UserStateHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<Comic> comicList;
    private ComicDetail comicDetail;
    private RetrofitService retrofitService;
    private ComicDetailAPI comicDetailAPI;
    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_history);
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);
        renderReadComics();
    }

    private void renderReadComics() {
        var readComicIdList = UserStateHelper.getReadComics(this);
        recyclerView = findViewById(R.id.history_reading);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        historyAdapter = new HistoryAdapter(this);
         comicList=   new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(readComicIdList.size());
        for (int comicId:readComicIdList){
            fetchHistoryData(comicId,comicList,countDownLatch);
        }
        recyclerView.setAdapter(historyAdapter);
        new Thread(()->{
            try {
                countDownLatch.await();
                runOnUiThread(()->{
                    if(historyAdapter!=null){
                        historyAdapter.setData(comicList);
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        historyAdapter.setData(comicList);
    }

    private void fetchHistoryData(int comicId,List<Comic> comicList,CountDownLatch countDownLatch) {
        comicDetailAPI.getComicDetailById(comicId).enqueue(new Callback<ComicDetail>() {
            @Override
            public void onResponse(Call<ComicDetail> call, Response<ComicDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("++++++++++++++++++++++++++++++++"+response.body());
                    comicList.add(response.body().comic());

                } else {
                    Toast.makeText(ReadingHistoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
                countDownLatch.countDown();
            }
            @Override
            public void onFailure(Call<ComicDetail> call, Throwable throwable) {
                Log.e("ReadingHistoryActivity", "Failed to fetch data", throwable);
                Toast.makeText(ReadingHistoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                countDownLatch.countDown();
            }
        });
    }
}
