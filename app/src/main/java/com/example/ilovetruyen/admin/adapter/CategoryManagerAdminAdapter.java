package com.example.ilovetruyen.admin.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.admin.AddComicActivity;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.util.NameMaxSizeHelper;
import com.example.ilovetruyen.util.UserStateHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagerAdminAdapter extends RecyclerView.Adapter<CategoryManagerAdminAdapter.CategoryManagerAdminAdapterViewHolder>{

    private Context context;
    List<Category> categories;

    public CategoryManagerAdminAdapter(Context context) {
        this.context = context;
        this.categories = new ArrayList<>();
    }

    public void setData(List<Category> categories) {
        this.categories.clear();
        if (categories != null) {
            this.categories.addAll(categories);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryManagerAdminAdapter.CategoryManagerAdminAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryManagerAdminAdapter.CategoryManagerAdminAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryManagerAdminAdapter.CategoryManagerAdminAdapterViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.text_stt.setText(String.valueOf(position + 1)  + ".");
        holder.text_category_name.setText(category.name());
        holder.editBtn.setOnClickListener(v->{
            showAddCategoryDialog();
        });
    }
    @Override
    public int getItemCount() {
        return categories.size() > 0 ? categories.size() : 0;
    }
    public class CategoryManagerAdminAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView text_stt, text_category_name;
        private ImageView deleteBtn, editBtn;
        public CategoryManagerAdminAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            text_stt = itemView.findViewById(R.id.text_stt);
            text_category_name = itemView.findViewById(R.id.text_category_name);
            deleteBtn = itemView.findViewById(R.id.icon_delete);
            editBtn = itemView.findViewById(R.id.icon_edit);
        }
    }
    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_add_category)
                .setTitle("Chỉnh sửa danh mục")
                .setPositiveButton("Sửa", (dialog, which) -> {

                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
