package com.example.ilovetruyen.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.ReadingHistoryActivity;
import com.example.ilovetruyen.admin.ComicManagerActivity;
import com.example.ilovetruyen.admin.FeatureComicActivity;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<Comic> comicList;

    public HistoryAdapter(Context context) {
        this.context = context;
        this.comicList = new ArrayList<>();
    }

    public void setData(List<Comic> comics) {
        this.comicList.clear();
        if (comics != null) {
            this.comicList.addAll(comics);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        if (comic == null) return;
        Glide.with(holder.itemView).load(comic.thumbUrl()).into(holder.history_read_thumb);
        holder.history_name.setText(comic.name());
        holder.history_chapter.setText("Chương " + String.valueOf(comic.latestChapter()));
        holder.history_view.setText(String.valueOf(comic.views()));
        holder.history_heart.setText(String.valueOf(comic.likes()));
        Glide.with(holder.itemView)
                .load(comic.thumbUrl())
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                resource.setAlpha(80);
                                holder.framelayout_background.setBackground(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ComicDetailActivity.class);
                intent.putExtra("comicId", comic.id());
                context.startActivity(intent);

            });
    }

    @Override
    public int getItemCount() {
        return comicList.size() > 0 ? comicList.size() : 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView history_name, history_chapter, history_heart, history_view;
        private ImageView backgroundImageView, history_read_thumb;

        private LinearLayout framelayout_background;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            history_chapter = itemView.findViewById(R.id.history_chapter);
            history_name = itemView.findViewById(R.id.history_name);
            history_heart = itemView.findViewById(R.id.history_heart);
            history_view = itemView.findViewById(R.id.history_view);
            history_read_thumb = itemView.findViewById(R.id.history_read_thumb);
            framelayout_background = itemView.findViewById(R.id.framelayout_background);
        }
    }
}
