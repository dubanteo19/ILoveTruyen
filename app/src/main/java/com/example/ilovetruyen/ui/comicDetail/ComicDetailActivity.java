package com.example.ilovetruyen.ui.comicDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ilovetruyen.R;

public class ComicDetailActivity extends AppCompatActivity {
    ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comic_detail);
        // truyen du lieu
        Intent myIntent = getIntent();



        setEventBtnBack();
    }

    public void setEventBtnBack(){
        btnBack = findViewById(R.id.back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();// Quay về activity trước đó
            }
        });
    }
}
