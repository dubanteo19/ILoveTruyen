package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.util.NameMaxSizeHelper;
import com.example.ilovetruyen.util.TimeDifference;

import java.util.List;

public class NewComicAdapter extends RecyclerView.Adapter<NewComicAdapter.NewComicViewHolder> {

    private Context context;
    private List<Comic> comics;

    public NewComicAdapter(Context context){
        this.context = context;
    }
    public void setData(List<Comic> comics){
        this.comics = comics;notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NewComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_comic_item,parent,false);
        return new NewComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewComicViewHolder holder, int position) {
        var comic = comics.get(position);
        if(comic==null) return;
        holder.nameTv.setText(NameMaxSizeHelper.truncateName(comic.name(),25));
        Glide.with(holder.itemView).load(comic.thumbUrl()).into(holder.thumbIv);
        holder.chapterTv.setText("Ch. "+comic.latestChapter());
        holder.createdDateTv.setText(TimeDifference.getTimeDifference(comic.createdDate()));
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, ComicDetailActivity.class);
            intent.putExtra("comicId",comic.id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public class NewComicViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbIv;
        private TextView chapterTv,createdDateTv,nameTv;
        public NewComicViewHolder(@NonNull View itemView) {
            super(itemView);
            this.thumbIv = itemView.findViewById(R.id.comic_thumb);
            this.chapterTv =itemView.findViewById(R.id.comic_chapter);
            this.createdDateTv =itemView.findViewById(R.id.comic_createDate);
            this.nameTv =itemView.findViewById(R.id.comic_name);
        }
    }
}
