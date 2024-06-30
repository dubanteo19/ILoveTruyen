package com.example.ilovetruyen.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.admin.adapter.CategoryCheckboxAdapter;
import com.example.ilovetruyen.api.CategoryAPI;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.dto.ComicAdd;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComicActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private MaterialButton add_comic, btnClosePopup;
    private ImageView edit;
    private ImageView thumbIv, selectImgBtn;
    private SharedPreferences sharedPreferences;
    RetrofitService retrofitService;
    CategoryAPI categoryAPI;
    ComicAPI comicAPI;
    StorageReference storageReference;
    CategoryCheckboxAdapter adapter;
    ProgressDialog progressDialog;

    ComicAdd comicAdd;

    TextInputLayout comicNameEt, comicDesEt;
    Uri thumb;
    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK && o.getData() != null) {
                        thumb = o.getData().getData();
                        Glide.with(getApplicationContext()).load(thumb).into(thumbIv);
                    }
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_comic);
        bindingView();
        boolean isEditComic = sharedPreferences.getBoolean("isEditComic", false);
        if (isEditComic) {
            textViewTitle.setText("Chỉnh sửa truyện");
            add_comic.setText("Chỉnh sửa truyện");
        }
        btnClosePopup = findViewById(R.id.btnClosePopup);
        btnClosePopup.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isEditComic", false);
            editor.apply();
            this.finish();
        });
        renderCategories();
        setSelectImgEvent();
        setAddComicEvent();
    }

    private void bindingView() {
        retrofitService = new RetrofitService();
        add_comic = findViewById(R.id.add_comic);
        categoryAPI = retrofitService.getRetrofit().create(CategoryAPI.class);
        comicAPI = retrofitService.getRetrofit().create(ComicAPI.class);
        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        textViewTitle = findViewById(R.id.textViewTitle);
        thumbIv = findViewById(R.id.thumb_Iv);
        comicNameEt = findViewById(R.id.input_layout_edit_name_comic);
        comicDesEt = findViewById(R.id.input_layout_edit_description_comic);
        selectImgBtn = findViewById(R.id.add_comic_selectImgBtn);
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
    }

    private void renderCategories() {
        RecyclerView recyclerView = findViewById(R.id.admin_categoies_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CategoryCheckboxAdapter(this);
        categoryAPI.findAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response != null) {
                    recyclerView.setAdapter(adapter);
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {

            }
        });
    }

    private void setSelectImgEvent() {
        selectImgBtn.setOnClickListener(v -> {
            var intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activityResultLauncher.launch(intent);
        });
    }

    public ComicAdd getComicAdd() {
        var reference = storageReference.child("images/" + UUID.randomUUID().toString());
        reference.putFile(thumb).addOnSuccessListener(taskSnapshot -> {
            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                String name = comicNameEt.getEditText().getText().toString();
                String description = comicDesEt.getEditText().getText().toString();
                comicAdd = new ComicAdd(name, uri.toString(), description, adapter.getSelectedCategories());
                progressDialog.dismiss();
                saveComic(comicAdd);
            });
        });
        return comicAdd;
    }

    private void saveComic(ComicAdd comicAdd ) {
        comicAPI.saveComic(comicAdd).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
              if(response.isSuccessful())  {
              }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable throwable) {

            }
        });
    }

    private void setAddComicEvent() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Tao truyen moi");
        add_comic.setOnClickListener(v -> {
            if (thumb != null) {
                progressDialog.show();
                getComicAdd();
            } else {
                showToast("Please select one thumb");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}