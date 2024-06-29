package com.example.ilovetruyen.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.admin.adapter.CategoryManagerAdminAdapter;
import com.example.ilovetruyen.admin.adapter.ComicManagerAdminAdapter;
import com.example.ilovetruyen.api.CategoryAPI;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iamageo.library.BeautifulDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryManagerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryManagerAdminAdapter categoryManagerAdminAdapter;
    private CategoryAPI categoryAPI;
    private RetrofitService retrofitService;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_manager);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v ->{
            showAddCategoryDialog();
        });
        retrofitService = new RetrofitService();
        categoryAPI = retrofitService.getRetrofit().create(CategoryAPI.class);
        renderListCategorys();
    }

    public void renderListCategorys(){
        recyclerView = findViewById(R.id.category_manager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        categoryManagerAdminAdapter = new CategoryManagerAdminAdapter(this);
        recyclerView.setAdapter(categoryManagerAdminAdapter);

        categoryAPI.findAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful() && response.body() != null){
                    categoryManagerAdminAdapter.setData(response.body());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Fetch data to failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Fetch data to failed !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_add_category)
                .setTitle("Thêm danh mục mới")
                .setPositiveButton("Thêm", (dialog, which) -> {

                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}