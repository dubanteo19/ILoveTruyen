package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.ReadingHistoryActivity;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.util.TimeDifference;
import com.example.ilovetruyen.util.UserStateHelper;

import java.util.List;

public class HotComicsAdapter extends RecyclerView.Adapter<HotComicsAdapter.HotComicsViewHolder> {

    private Context context;
    private List<Comic> comicList;
    public HotComicsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Comic> comics) {
        this.comicList = comics;
        notifyDataSetChanged();
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
        Glide.with(holder.itemView).load(comic.thumbUrl()).into(holder.thumb);
        holder.comicNameTv.setText(comic.name());
        String lastChapter = comic.latestChapter() == 0?"Chưa có chương":"Chương "+String.valueOf(comic.latestChapter());
        holder.comicChapterTv.setText(lastChapter);
        holder.comicCreatedDateTv.setText(TimeDifference.getTimeDifference(comic.createdDate()));
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, ComicDetailActivity.class);
            intent.putExtra("comicId",comic.id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class HotComicsViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumb;
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
