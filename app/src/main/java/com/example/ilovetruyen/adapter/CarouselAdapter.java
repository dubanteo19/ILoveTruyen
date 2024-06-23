package com.example.ilovetruyen.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.List;

public class CarouselAdapter extends CardSliderAdapter<CarouselAdapter.CarouselViewHolder> {

    private List<Comic> comics;

    public CarouselAdapter(List<Comic> comics) {
        this.comics = comics;
    }
    public void setData(List<Comic> comics){
        this.comics = comics;
    }
    @Override
    public void bindVH(@NonNull CarouselViewHolder carouselViewHolder, int i) {
        var comic = comics.get(i);
//        carouselViewHolder.comicThumb.setImageResource(comic.thumb());
        Glide.with(carouselViewHolder.itemView).load(comic.thumbUrl()).into(carouselViewHolder.comicThumb);
        carouselViewHolder.comicTitle.setText(comic.name());
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.home_carousel_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.comics.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        private TextView comicTitle;
        private ImageView comicThumb;
        private Button readNowBtn;
        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            comicTitle = itemView.findViewById(R.id.home_carousel_comic_title);
            comicThumb = itemView.findViewById(R.id.home_carousel_comic_thumb);
            readNowBtn = itemView.findViewById(R.id.home_carousel_btn);
            readNowBtn.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), ComicDetailActivity.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
