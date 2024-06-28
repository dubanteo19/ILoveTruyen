package com.example.ilovetruyen.admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comment;
import com.example.ilovetruyen.model.User;

import java.util.ArrayList;
import java.util.List;

public class CommentManagerAdminAdapter extends RecyclerView.Adapter<CommentManagerAdminAdapter.CommentManagerAdminViewHolder>{
    private Context context;
    private List<Comment> comments;

    public CommentManagerAdminAdapter(Context context) {
        this.context = context;
        this.comments = new ArrayList<>();
    }

    public void setData(List<Comment> comments) {
        this.comments.clear();
        if (comments != null) {
            this.comments.addAll(comments);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommentManagerAdminAdapter.CommentManagerAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_comment_admin, parent, false);
        return new CommentManagerAdminAdapter.CommentManagerAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentManagerAdminAdapter.CommentManagerAdminViewHolder holder, int position) {
        Comment comment = comments.get(position);
        if (comment == null) return;
        holder.user_comment_content.setText(comment.text());
        holder.user_delete_comment.setOnClickListener(v -> {
            showAddCategoryDialog();
        });
    }

    @Override
    public int getItemCount() {
        return comments.size() > 0 ? comments.size() : 0;
    }

    public class CommentManagerAdminViewHolder extends RecyclerView.ViewHolder {
        private TextView user_comment_content;
        private ImageView user_delete_comment;

        public CommentManagerAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            user_comment_content = itemView.findViewById(R.id.user_comment_content);
            user_delete_comment = itemView.findViewById(R.id.user_delete_comment);
        }
    }
    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_add_category)
                .setTitle("Xóa bình luận")
                .setMessage("Bạn có chắc muốn xóa bình luận này không ?")
                .setPositiveButton("Xóa", (dialog, which) -> {

                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
