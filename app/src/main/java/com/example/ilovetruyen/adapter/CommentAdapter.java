package com.example.ilovetruyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comment;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.userName.setText(comment.getUserName());
        holder.commentText.setText(comment.getCommentText());
        holder.time.setText(comment.getTime());
        holder.userImage.setImageResource(comment.getUserImage());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView userName, commentText, time;
        ImageView userImage, moreOptions;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.text_user_name);
            commentText = itemView.findViewById(R.id.text_comment);
            time = itemView.findViewById(R.id.text_time);
            userImage = itemView.findViewById(R.id.image_user);
            moreOptions = itemView.findViewById(R.id.image_more);
        }
    }
}
