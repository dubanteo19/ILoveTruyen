package com.example.ilovetruyen.admin;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.HistoryAdapter;
import com.example.ilovetruyen.admin.adapter.ComicManagerAdminAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.ComicDetail;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicManagerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ComicManagerAdminAdapter comicManagerAdminAdapter;
    private ComicAPI comicAPI;
    private RetrofitService retrofitService;
    private PopupWindow popupWindow;
    private FloatingActionButton fab;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comic_manager);
        progressBar = findViewById(R.id.process_bar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddComicActivity.class);
            startActivityForResult(intent,1);
        });

        retrofitService = new RetrofitService();
        comicAPI = retrofitService.getRetrofit().create(ComicAPI.class);
        renderListComics();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }

    private void renderListComics() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.history_reading);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        comicManagerAdminAdapter = new ComicManagerAdminAdapter(this);
        comicAPI.getAllComicsDetail().enqueue(new Callback<List<ComicDetail>>() {
            @Override
            public void onResponse(Call<List<ComicDetail>> call, Response<List<ComicDetail>> response) {
                System.out.println(response.body() + "=======================");
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(comicManagerAdminAdapter);
                    comicManagerAdminAdapter.setData(response.body());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ComicDetail>> call
                    , Throwable throwable) {

            }
        });

    }

}