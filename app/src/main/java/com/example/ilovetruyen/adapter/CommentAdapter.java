package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.Comment;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.ui.comments.EditCommentActivity;
import com.example.ilovetruyen.ui.comments.OnEditCommentListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    private int userId;

    private Context context;
    private int comicId;

    public CommentAdapter(Context context, int userId, int comicId) {
        this.userId = userId;
        this.context = context;
        this.comicId = comicId;
    }

    public void setData(List<Comment> comments) {
        this.commentList = comments;
        notifyDataSetChanged();
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        Glide.with(holder.itemView).load(R.drawable.thanh_guom_diet_quy).into(holder.userImage);
        holder.commentText.setText(comment.text());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        holder.commentTime.setText(comment.createdDate().format(formatter));
        holder.userName.setText(comment.user().fullName());
        if (comment.user().id() == userId) {
            holder.editComment.setVisibility(View.VISIBLE);
        }
        holder.editComment.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCommentActivity.class);
            intent.putExtra("comment", comment);
            intent.putExtra("comicId", comicId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, commentText, commentTime;
        public ImageView userImage, editComment;

        public CommentViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.textUserName);
            commentText = view.findViewById(R.id.textComment);
            commentTime = view.findViewById(R.id.textTime);
            userImage = view.findViewById(R.id.imageUser);
            editComment = view.findViewById(R.id.imageMore);
        }
    }
}
