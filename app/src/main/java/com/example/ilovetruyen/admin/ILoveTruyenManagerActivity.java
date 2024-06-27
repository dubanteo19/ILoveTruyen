package com.example.ilovetruyen.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ilovetruyen.R;

public class ILoveTruyenManagerActivity extends AppCompatActivity {
    private CardView card_view_manager_comic, card_view_manager_category, card_view_manager_comment, card_view_manager_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ilove_truyen_manager);
        card_view_manager_comic = findViewById(R.id.card_view_manager_comic);
        card_view_manager_user = findViewById(R.id.card_view_manager_user);
        card_view_manager_category = findViewById(R.id.card_view_manager_category);
        card_view_manager_comment = findViewById(R.id.card_view_manager_comment);

        card_view_manager_comic.setOnClickListener(v -> {
            Intent intent = new Intent(this, ComicManagerActivity.class);
            startActivity(intent);
        });
        card_view_manager_user.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserManagerActivity.class);
            startActivity(intent);
        });
        card_view_manager_category.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoryManagerActivity.class);
            startActivity(intent);
        });
        card_view_manager_comment.setOnClickListener(v -> {
            Intent intent = new Intent(this, CommentManagerActivity.class);
            startActivity(intent);
        });



    }
}