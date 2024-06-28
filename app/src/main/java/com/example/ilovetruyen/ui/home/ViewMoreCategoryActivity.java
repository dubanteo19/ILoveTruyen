package com.example.ilovetruyen.ui.home;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.CategoryItemAdapter;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.api.CategoryAPI;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMoreCategoryActivity extends AppCompatActivity {

    RetrofitService retrofitService;
    RecyclerView recyclerView;
    CategoryAPI categoryAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_more_category);
        recyclerView = findViewById(R.id.search_result_comics_rv);
        retrofitService = new RetrofitService();
        categoryAPI = retrofitService.getRetrofit().create(CategoryAPI.class);
        setBackListener();
        renderData();
    }

    private void setBackListener() {
        ImageButton backBtn = findViewById(R.id.search_back_icon);
        backBtn.setOnClickListener(v->{
        finish();
        });
    }

    private void renderData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        CategoryItemAdapter categoryItemAdapter = new CategoryItemAdapter(this);
        categoryAPI.findAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(categoryItemAdapter);
                    categoryItemAdapter.setData(response.body());
                }
            }


            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {

            }
        });
    }
}