package com.example.ilovetruyen.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ilovetruyen.R;
import com.google.android.material.button.MaterialButton;

public class AddComicActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private MaterialButton add_comic, btnClosePopup;
    private ImageView edit;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_comic);
        add_comic = findViewById(R.id.add_comic);
        textViewTitle = findViewById(R.id.textViewTitle);
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isEditComic = sharedPreferences.getBoolean("isEditComic", false);
        if(isEditComic){
            textViewTitle.setText("Chỉnh sửa truyện");
            add_comic.setText("Chỉnh sửa truyện");
        }
        btnClosePopup = findViewById(R.id.btnClosePopup);
        btnClosePopup.setOnClickListener(v ->{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isEditComic", false);
            editor.apply();
            this.finish();
        });
    }
}