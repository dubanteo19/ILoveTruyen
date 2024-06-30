package com.example.ilovetruyen.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.ilovetruyen.dto.CategoryDTO;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
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
        fab.setOnClickListener(v -> {
            showAddCategoryDialog();
        });
        retrofitService = new RetrofitService();
        categoryAPI = retrofitService.getRetrofit().create(CategoryAPI.class);
        renderListCategorys();
    }

    public void renderListCategorys() {
        recyclerView = findViewById(R.id.category_manager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        categoryManagerAdminAdapter = new CategoryManagerAdminAdapter(this);
        recyclerView.setAdapter(categoryManagerAdminAdapter);

        categoryAPI.findAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryManagerAdminAdapter.setData(response.body());
                } else {
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

// Inflate the dialog layout
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_category, null);
        builder.setView(dialogView);

// Get reference to the EditText
        TextInputLayout editTextCategory;
        editTextCategory = dialogView.findViewById(R.id.dialog_categoryName);
        builder.setTitle("Thêm danh mục mới")
                .setPositiveButton("Thêm", (dialog, which) -> {
                    // Get the text from the EditText
                    String category = editTextCategory.getEditText().getText().toString();
                    // Do something with the text
                    saveCategory(category);
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void saveCategory(String category) {
        categoryAPI.save(new CategoryDTO(category)).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "Thêm thể loại mới thành công", Toast.LENGTH_SHORT).show();
                renderListCategorys();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {

            }
        });
    }
}