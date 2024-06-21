package com.example.ilovetruyen.ui.categorySearch;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.CategoryItemAdapter;
import com.example.ilovetruyen.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        Category c1 = new Category("Action", "Hành động", R.drawable.gradient_1);
        Category c2 = new Category("Adventure", "Phiêu lưu", R.drawable.gradient_2);
        Category c3 = new Category("Comedy", "Hài hước", R.drawable.gradient_3);
        Category c4 = new Category("Drama", "Kịch tính", R.drawable.gradient_4);
        Category c5 = new Category("Fantasy", "Huyễn ảo", R.drawable.gradient_5);
        Category c6 = new Category("Horror", "Kinh dị", R.drawable.gradient_6);
        Category c7 = new Category("Mystery", "Bí ẩn", R.drawable.gradient_7);
        Category c8 = new Category("Romance", "Lãng mạn", R.drawable.gradient_8);
        Category c9 = new Category("Sci-fi", "Khoa học viễn tưởng", R.drawable.gradient_9);
        Category c10 = new Category("Slice of life", "Đời thường", R.drawable.gradient_10);

        List<Category> listData = new ArrayList<>(List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));
        RecyclerView recyclerView = view.findViewById(R.id.category_recycler_view);
        CategoryItemAdapter adapter = new CategoryItemAdapter(listData, new CategoryItemAdapter.OnCategoryItemClickListener() {
            @Override
            public void onCategoryItemClick(int position) {
                // Handle click event
                Category category = listData.get(position);
                String categoryName = category.getName();
                Intent intent = new Intent(getContext(), CategoryResultActivity.class);
                intent.putExtra("categoryName", categoryName);
                startActivity(intent);
                Toast.makeText(getContext(), categoryName, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Inflate the layout for this fragment
        return view;
    }
}