package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.effect.BlinkingEffect;
import com.example.ilovetruyen.model.Comic;

import java.util.List;

public class FavoriteComicAdapter extends RecyclerView.Adapter<FavoriteComicAdapter.FavoriteComicsViewHolder>{
    private Context context;
    private List<Comic> favoriteComicList;
    public FavoriteComicAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Comic> favoriteComicList) {
        this.favoriteComicList = favoriteComicList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite_comics,parent,false);
        return new FavoriteComicsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteComicsViewHolder holder, int position) {
        var comic = favoriteComicList.get(position);
        if(comic==null) return;

        holder.comicNameTv.setText(comic.name());

        Glide.with(holder.itemView).load(comic.thumbUrl()).into(holder.thumb);
        holder.comicChapterTv.setText("Đang xem ch."+comic.latestChapter());

        BlinkingEffect.applyBlinkingEffect(holder.hotLableTV);//set blinking
        holder.comicNameTv.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return favoriteComicList.size();
    }

    public  class FavoriteComicsViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumb;
        private TextView hotLableTV,comicNameTv, comicChapterTv;

        public FavoriteComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            hotLableTV = itemView.findViewById(R.id.hot_lable);
            thumb = itemView.findViewById(R.id.favorite_comic_thumb);
            comicNameTv = itemView.findViewById(R.id.marquee_comic_name);
            comicChapterTv = itemView.findViewById(R.id.favorite_comic_currentChap);
        }
    }

}
