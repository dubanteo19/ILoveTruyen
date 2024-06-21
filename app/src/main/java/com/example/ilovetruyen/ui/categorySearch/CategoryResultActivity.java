package com.example.ilovetruyen.ui.categorySearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comic;

import java.util.ArrayList;
import java.util.List;

public class CategoryResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String category = intent.getStringExtra("categoryName");
        Toolbar toolbar = findViewById(R.id.toolbar_category_result);
        toolbar.setTitle(category);

        // Set back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

        // List<Commic> comics = CommicService.getCommicByCategory(category);


        List<Comic> comics = new ArrayList<>();
        Comic comic1 = new Comic("One Piece", R.drawable.one_piece);
        Comic comic2 = new Comic("Thanh Guong diet quy", R.drawable.thanh_guom_diet_quy);
        Comic comic3 = new Comic("One Punchman", R.drawable.one_piece);
        comics.add(comic1);
        comics.add(comic2);
        comics.add(comic3);

        GridLayout gridLayout = findViewById(R.id.grid_layout_category_result);

        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < comics.size(); i++) {
            Comic comic = comics.get(i);
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_category_result, gridLayout, false);

            TextView comicName = viewGroup.findViewById(R.id.comic_name_category_result);
            comicName.setText(comic.name());
            ImageView comicThumb = viewGroup.findViewById(R.id.comic_thumb_category_result);
            comicThumb.setImageResource(comic.thumb());

            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.columnSpec = GridLayout.spec(i % 3, 1f);

            gridLayout.addView(viewGroup);
        }



    }
}