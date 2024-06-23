package com.example.ilovetruyen.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.util.GradientHelper;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder> {
    private  List<Category> categoryList;
    private Context context;
    public CategoryItemAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Category> categoryList){
        this.categoryList=categoryList;
        notifyDataSetChanged();
    }
    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtCategoryName.setText(category.name());
//        holder.txtCategoryDescription.setText(category.getDescription());
        holder.cardView.setBackgroundResource(GradientHelper.getColor(category.id()));
//        holder.cardView.setOnClickListener(v -> {
//            int currentPosition = holder.getBindingAdapterPosition();
//            if (currentPosition != RecyclerView.NO_POSITION) {
//                onCategoryItemClickListener.onCategoryItemClick(currentPosition);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return this.categoryList.size();
    }

    public static class CategoryItemViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView txtCategoryName;
        public TextView txtCategoryDescription;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.category_item_card_view);
            txtCategoryName = itemView.findViewById(R.id.category_name);
            txtCategoryDescription = itemView.findViewById(R.id.category_description);
        }
    }
}
