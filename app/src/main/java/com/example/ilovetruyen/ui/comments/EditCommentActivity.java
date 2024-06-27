package com.example.ilovetruyen.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.api.ComicCommentAPI;
import com.example.ilovetruyen.dto.ComicCommentDto;
import com.example.ilovetruyen.model.Comment;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCommentActivity extends AppCompatActivity {
    Comment comment;
    RetrofitService retrofitService;
    ComicCommentAPI comicCommentAPI;
    int comicId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_comment);
        Intent intent = getIntent();
        comment = (Comment) intent.getSerializableExtra("comment");
        comicId = intent.getIntExtra("comicId",0);
        retrofitService = new RetrofitService();
        comicCommentAPI = retrofitService.getRetrofit().create(ComicCommentAPI.class);
        renderComment();
        setUpdateCommentEvent();
    }

    private void setUpdateCommentEvent() {
        Button updateCommentBtn = findViewById(R.id.send_button);
        updateCommentBtn.setOnClickListener(v->{
            EditText editText = findViewById(R.id.comment_input);
            var newCommentText = editText.getText().toString();
          ComicCommentDto newComment = new ComicCommentDto(newCommentText,comment.user().id(),comicId);
          updateComment(newComment);
        });
    }

    private void updateComment(ComicCommentDto newComment) {
        comicCommentAPI.updateComment(comment.id(),newComment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
               if(response.isSuccessful()){
                   Intent intent = new Intent(getApplicationContext(), ComicDetailActivity.class);
                   intent.putExtra("comicId",comicId);
                   startActivity(intent);
               }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable throwable) {

            }
        });
    }

    private void renderComment() {
        TextView userNameTv = findViewById(R.id.fragment_comment_userName);
        EditText editText = findViewById(R.id.comment_input);
        editText.setText(comment.text());
        userNameTv.setText(comment.user().fullName());
    }
}