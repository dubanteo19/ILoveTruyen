package com.example.ilovetruyen.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ilovetruyen.LoginActivity;
import com.example.ilovetruyen.MainActivity;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.api.AdminAPI;
import com.example.ilovetruyen.dto.DashBoardDTO;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ILoveTruyenManagerActivity extends AppCompatActivity {
    private CardView card_view_manager_comic,
            card_view_manager_category, card_view_manager_comment, card_view_manager_user;
    private TextView totalComicTv, totalCategoryTv, totalCommentTv, totalUserTv;
    private ImageView logoutBtn;
    private RetrofitService retrofitService;
    private AdminAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ilove_truyen_manager);
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(AdminAPI.class);
        card_view_manager_comic = findViewById(R.id.card_view_manager_comic);
        card_view_manager_user = findViewById(R.id.card_view_manager_user);
        card_view_manager_category = findViewById(R.id.card_view_manager_category);
        card_view_manager_comment = findViewById(R.id.card_view_manager_comment);
        logoutBtn = findViewById(R.id.logoutbtn);
        renderDashboard();
        logoutBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
                }
        );
        card_view_manager_comic.setOnClickListener(v -> {
            Intent intent = new Intent(this, ComicManagerActivity.class);
            startActivityForResult(intent, 1);
        });
        card_view_manager_user.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserManagerActivity.class);
            startActivityForResult(intent, 1);
        });
        card_view_manager_category.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoryManagerActivity.class);
            startActivityForResult(intent, 1);
        });
        card_view_manager_comment.setOnClickListener(v -> {
            Intent intent = new Intent(this, CommentManagerActivity.class);
            startActivityForResult(intent, 1);
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }


    private void renderDashboard() {
        totalCategoryTv = findViewById(R.id.total_category_description);
        totalComicTv = findViewById(R.id.total_comic_description);
        totalCommentTv = findViewById(R.id.total_comment_description);
        totalUserTv = findViewById(R.id.total_user_description);
        api.getDashboard().enqueue(new Callback<DashBoardDTO>() {
            @Override
            public void onResponse(Call<DashBoardDTO> call, Response<DashBoardDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DashBoardDTO dashBoardDTO = response.body();
                    System.out.println(dashBoardDTO);
                    totalCategoryTv.setText(MessageFormat.format("{0}", dashBoardDTO.totalCategory()));
                    totalUserTv.setText(MessageFormat.format("{0}", dashBoardDTO.totalUser()));
                    totalComicTv.setText(MessageFormat.format("{0}", dashBoardDTO.totalComic()));
                    totalCommentTv.setText(MessageFormat.format("{0}", dashBoardDTO.totalComment()));
                }
            }

            @Override
            public void onFailure(Call<DashBoardDTO> call, Throwable throwable) {

            }
        });
    }

}