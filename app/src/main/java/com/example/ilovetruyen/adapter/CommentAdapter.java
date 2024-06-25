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
import com.example.ilovetruyen.model.Commentzz;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.ui.comments.OnEditCommentListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
        this.commentList = new ArrayList<>();
    }
    public void setData(List<Comment> comments) {
        this.commentList.clear();
        if (comments != null) {
            this.commentList.addAll(comments);
        }
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
        if (comment == null) return;

        Glide.with(holder.itemView).load(comment.text()).into(holder.userImage);
        holder.commentText.setText(comment.text());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = comment.createdDate().format(formatter);
        holder.commentTime.setText(formattedDate);
        holder.userName.setText(comment.user().fullName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ComicDetailActivity.class);
            intent.putExtra("commentId", comment.id());
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
