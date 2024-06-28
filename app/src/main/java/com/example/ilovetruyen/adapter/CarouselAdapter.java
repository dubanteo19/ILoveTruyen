package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.util.NameMaxSizeHelper;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.List;

public class CarouselAdapter extends CardSliderAdapter<CarouselAdapter.CarouselViewHolder> {

    private List<Comic> comics;
    private Context context;

    public CarouselAdapter(List<Comic> comics,Context context) {
        this.comics = comics;
        this.context = context;
    }

    public void setData(List<Comic> comics) {
        this.comics = comics;
        notifyDataSetChanged();
    }

    @Override
    public void bindVH(@NonNull CarouselViewHolder carouselViewHolder, int i) {
        var comic = comics.get(i);
        Glide.with(carouselViewHolder.itemView).load(comic.thumbUrl()).into(carouselViewHolder.comicThumb);
       Glide.with(carouselViewHolder.itemView).load(comic.thumbUrl()).into(new CustomTarget<Drawable>() {
           @Override
           public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
             resource.setAlpha(50);
               carouselViewHolder.itemView.setBackground(resource);
           }

           @Override
           public void onLoadCleared(@Nullable Drawable placeholder) {

           }
       });
        carouselViewHolder.readNowBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, ComicDetailActivity.class);
            intent.putExtra("comicId", comic.id());
            context.startActivity(intent);
        });
        carouselViewHolder.comicTitle.setText(NameMaxSizeHelper.truncateName(comic.name()));
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

        }
    }
}
