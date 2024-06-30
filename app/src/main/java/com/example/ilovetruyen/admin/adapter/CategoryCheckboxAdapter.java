package com.example.ilovetruyen.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.admin.AddComicActivity;
import com.example.ilovetruyen.model.Category;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CategoryCheckboxAdapter extends RecyclerView.Adapter<CategoryCheckboxAdapter.CategoryCheckBoxViewHolder> {

    Context context;
    List<Category> categoryList;
    Set<Integer> selectedCategories;
    AddComicActivity parent;
    public CategoryCheckboxAdapter(Context context) {
        this.context = context;
        this.selectedCategories=new LinkedHashSet<>();
    }

    public void setData(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryCheckBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_admin_category_checkbox, parent, false);
        return new CategoryCheckBoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCheckBoxViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if (category==null) return;
        System.out.println(category.id());
        holder.checkBox.setText(category.name());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) selectedCategories.add(category.id());
            else selectedCategories.remove(category.id());
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryCheckBoxViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        public CategoryCheckBoxViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBox = itemView.findViewById(R.id.category_checkbox);
        }
    }

    public Set<Integer> getSelectedCategories() {
        return this.selectedCategories;
    }
}
