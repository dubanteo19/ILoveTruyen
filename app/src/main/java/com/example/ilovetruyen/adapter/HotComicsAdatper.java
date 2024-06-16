package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comic;

import java.util.List;

public class HotComicsAdatper extends RecyclerView.Adapter<HotComicsAdatper.HotComicsViewHolder> {

    private Context context;
    private List<Comic> comicList;

    public HotComicsAdatper(Context context) {
        this.context = context;
    }

    public void setData(List<Comic> comics) {
        this.comicList = comics;
    }

    @NonNull
    @Override
    public HotComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.hot_comic_item, parent, false);
        return new HotComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotComicsViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        if (comic ==null) return;
        holder.thumb.setBackgroundResource(comic.thumb());
        holder.comicNameTv.setText(comic.name());
        holder.comicChapterTv.setText(comic.chapter());
        holder.comicCreatedDateTv.setText(comic.createdDate().toString());
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class HotComicsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout thumb;
        private TextView comicNameTv, comicChapterTv, comicCreatedDateTv;

        public HotComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.comic_thumb);
            comicNameTv = itemView.findViewById(R.id.comic_name);
            comicChapterTv = itemView.findViewById(R.id.comic_chapter);
            comicCreatedDateTv = itemView.findViewById(R.id.comic_createDate);
        }
    }
}
