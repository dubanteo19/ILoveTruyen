package com.example.ilovetruyen.ui.comments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.CommentAdapter;
import com.example.ilovetruyen.api.ComicCommentAPI;
import com.example.ilovetruyen.dto.ComicCommentDto;
import com.example.ilovetruyen.model.ComicDetail;
import com.example.ilovetruyen.model.Comment;
import com.example.ilovetruyen.model.User;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentFragment extends Fragment {
    private static final String ARG_COMIC_ID = "comicId";
    private int comicId;
    private List<Comment> commentList;
    private ImageView editComment;
    private ComicCommentAPI comicCommentAPI;
    private CommentAdapter adapter;
    private RecyclerView recyclerView;

    public CommentFragment() {
    }

    public static CommentFragment newInstance(int comicId) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COMIC_ID, comicId);
        fragment.setArguments(args);
        Retrofit retrofit = new RetrofitService().getRetrofit();
        fragment.comicCommentAPI = retrofit.create(ComicCommentAPI.class);
        fragment.commentList = new ArrayList<>();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.comicId = getArguments().getInt(ARG_COMIC_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        // Get user logged in id
        User currentUser = getCurrentUser();
        int userId = currentUser.id();
        EditText commentInput = view.findViewById(R.id.comment_input);
        Button sendButton = view.findViewById(R.id.send_button);
        AtomicInteger commentEditPosition = new AtomicInteger(-1);
        this.recyclerView = view.findViewById(R.id.recycler_view_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new CommentAdapter();
        adapter.setOnEditCommentListener(position -> {
            if (commentList.isEmpty()) return;

            Comment comment = commentList.get(position);

            if (userId == 0) {
                Toast.makeText(getContext(), "Vui lòng đăng nhập để thực hiện chức năng này", Toast.LENGTH_LONG).show();
                return;
            }
            if (userId == comment.user().id()) {
                commentInput.setText(comment.text());
                commentEditPosition.set(position);
            } else {
                Toast.makeText(getContext(), "Bạn không thể chỉnh sửa comment của người khác", Toast.LENGTH_LONG).show();
            }
        });


        // Submit comment
        sendButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString();

            if (userId == 0) {
                Toast.makeText(getContext(), "Vui lòng đăng nhập để thực hiện chức năng này", Toast.LENGTH_LONG).show();
                return;
            }

            if (commentText.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập nội dung comment", Toast.LENGTH_LONG).show();
                return;
            }
            if (commentEditPosition.get() != -1) {
                Comment currentComment = commentList.get(commentEditPosition.get());
                ComicCommentDto newComment = new ComicCommentDto(commentText, userId, comicId);
                commentEditPosition.set(-1);

                updateComment(newComment, currentComment.id());
            } else {
                ComicCommentDto newComment = new ComicCommentDto(commentText, userId, comicId);

                getComments();
                createComment(newComment);
            }
            adapter.setData(commentList);
            commentInput.setText("");
        });



//        getComments();

        return view;
    }

    private void getComments() {
        comicCommentAPI.getCommentByComicId(comicId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(adapter);
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("CommentFragment", "Error when getting comments", t);
            }
        });

    }

    private void updateComment(ComicCommentDto comment, int idComment) {
        comicCommentAPI.updateComment(idComment, comment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật comment thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("CommentFragment", "Error when updating comment", t);
                Toast.makeText(getContext(), "Cập nhật comment thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createComment(ComicCommentDto comment) {
        comicCommentAPI.createComment(comment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Tạo comment thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("CommentFragment", "Error when creating comment", t);
                Toast.makeText(getContext(), "Tạo comment thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

    private User getCurrentUser() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        String fullName = sharedPreferences.getString("fullName", "");

        return new User(userId, email, password, fullName, new ArrayList<>());
    }
}
