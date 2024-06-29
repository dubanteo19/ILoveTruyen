package com.example.ilovetruyen.ui.comments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
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
    private EditText commentEditText;
    private ComicCommentAPI comicCommentAPI;
    private CommentAdapter adapter;
    private RecyclerView recyclerView;
    private OnCommentFocusListener focusListener;

    public interface OnCommentFocusListener {
        void onCommentFocus();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCommentFocusListener) {
            focusListener = (OnCommentFocusListener) context;
        }
    }

    public CommentFragment() {
    }

    public static CommentFragment newInstance(int comicId) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COMIC_ID, comicId);
        fragment.setArguments(args);
        Retrofit retrofit = new RetrofitService().getRetrofit();
        fragment.comicCommentAPI = retrofit.create(ComicCommentAPI.class);
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
        recyclerView = view.findViewById(R.id.recycler_view_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new CommentAdapter(getContext(), getCurrentUser().id(),comicId);
        // Submit comment
        renderUserName(view);
        getComments();
        setSendCommentEvent(view);
        commentEditText = view.findViewById(R.id.comment_input);
        commentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (focusListener != null) {
                        focusListener.onCommentFocus();
                        Toast.makeText(getContext(), "Focus", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    private void renderUserName(View view) {
        TextView textView = view.findViewById(R.id.fragment_comment_userName);
        textView.setText(getCurrentUser().fullName());
    }

    private void setSendCommentEvent(View view) {
        User currentUser = getCurrentUser();
        int userId = currentUser.id();
        AtomicInteger commentEditPosition = new AtomicInteger(-1);
        Button sendButton = view.findViewById(R.id.send_button);
        EditText commentInput = view.findViewById(R.id.comment_input);
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
                ComicCommentDto newComment = new ComicCommentDto(commentText, userId, comicId);
                commentEditPosition.set(-1);

//                updateComment(newComment, currentComment.id());
            } else {
                ComicCommentDto newComment = new ComicCommentDto(commentText, userId, comicId);
                createComment(newComment);
            }
            commentInput.setText("");
        });
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
                    Toast.makeText(getContext(), "Thêm bình thành công", Toast.LENGTH_LONG).show();
                    getComments();
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
        String fullName = sharedPreferences.getString("user_name", "Khách");

        return new User(userId, email, password, fullName, new ArrayList<>());
    }
}
