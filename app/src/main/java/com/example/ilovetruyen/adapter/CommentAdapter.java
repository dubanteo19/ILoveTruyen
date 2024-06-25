package com.example.ilovetruyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Commentzz;
import com.example.ilovetruyen.ui.comments.OnEditCommentListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Commentzz> commentList;
    private OnEditCommentListener onEditCommentListener;

    public CommentAdapter(List<Commentzz> commentList, OnEditCommentListener onEditCommentListener) {
        this.commentList = commentList;
        this.onEditCommentListener = onEditCommentListener;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
//        holder.userName.setText(comment.getUserName());
//        holder.commentText.setText(comment.getCommentText());
//        holder.commentTime.setText(comment.getTime());
//        holder.userImage.setImageResource(comment.getUserImage());
        holder.editComment.setOnClickListener(v -> onEditCommentListener.onEditComment(position));
    }
    public void setData(List<Commentzz> commentList){
        this.commentList = commentList;
        notifyDataSetChanged();
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
            userName = view.findViewById(R.id.text_user_name);
            commentText = view.findViewById(R.id.text_comment);
            commentTime = view.findViewById(R.id.text_time);
            userImage = view.findViewById(R.id.image_user);
            editComment = view.findViewById(R.id.image_more);
        }
    }
}
