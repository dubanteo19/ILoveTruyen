package com.example.ilovetruyen.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.admin.adapter.CommentManagerAdminAdapter;
import com.example.ilovetruyen.admin.adapter.UserManagerAdminAdapter;
import com.example.ilovetruyen.api.ComicCommentAPI;
import com.example.ilovetruyen.api.UserAPI;
import com.example.ilovetruyen.model.Comment;
import com.example.ilovetruyen.model.User;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentManagerActivity extends AppCompatActivity implements CommentManagerAdminAdapter.OnCommentDeleteListener {
    private RecyclerView recyclerView;
    private CommentManagerAdminAdapter commentManagerAdminAdapter;
    private RetrofitService retrofitService;
    private ProgressBar progressBar;
    private ComicCommentAPI comicCommentAPI;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comment_manager);
        progressBar = findViewById(R.id.process_bar);
        retrofitService = new RetrofitService();
        comicCommentAPI = retrofitService.getRetrofit().create(ComicCommentAPI.class);
        renderCommentList();
    }

    private void renderCommentList() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.comment_manager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        commentManagerAdminAdapter = new CommentManagerAdminAdapter(this,this);
        recyclerView.setAdapter(commentManagerAdminAdapter);
        comicCommentAPI.getAllComment().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                System.out.println(response.body()+"=======================");
                if(response.isSuccessful() && response.body() != null){
                    commentManagerAdminAdapter.setData(response.body());
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void deleteComment(Integer commentId) {
       comicCommentAPI.deleteComment(commentId).enqueue(new Callback<Boolean>() {
           @Override
           public void onResponse(Call<Boolean> call, Response<Boolean> response) {
               if(response.body()){
                   Toast.makeText(getApplicationContext(),"Xóa bình luận thành công",Toast.LENGTH_LONG).show();
                   renderCommentList();
               }
           }

           @Override
           public void onFailure(Call<Boolean> call, Throwable throwable) {

           }
       });
    }
}