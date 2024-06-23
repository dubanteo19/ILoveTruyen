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
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.util.NameMaxSizeHelper;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private Context context;
    private List<Comic> comicList;

    public ComicAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Comic> comicList) {
        this.comicList = comicList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_item, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        var comic = comicList.get(position);
        if (comic == null) return;
        holder.comicNameView.setText(NameMaxSizeHelper.truncateName(comic.name(),25));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ComicDetailActivity.class);
            intent.putExtra("comicId",comic.id());
            context.startActivity(intent);

        });
        Glide.with(holder.itemView).load(comic.thumbUrl()).into(holder.comicThumbImage);
    }

    @Override
    public int getItemCount() {
        return comicList.size() > 0 ? comicList.size() : 0;
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {
        private TextView comicNameView;
        private ImageView comicThumbImage;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            comicNameView = itemView.findViewById(R.id.comic_name);
            comicThumbImage = itemView.findViewById(R.id.comic_thumb);
        }

    }
}
