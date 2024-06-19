package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Chapter;

import java.util.List;

public class ChapterContentAdapter extends RecyclerView.Adapter<ChapterContentAdapter.ViewHolder>{
    private Context mContext;
    private Chapter chapter;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.item_image, parent, false);
        ViewHolder photoHolder = new ViewHolder(viewHolder);
        return photoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Integer> imageList = chapter.getImageList();
        Integer image = imageList.get(position);
        Glide.with(mContext)
                .load(image)
                .into(holder.imageView);
//        holder.imageView.setImageResource(image);

    }

    @Override
    public int getItemCount() {
        return chapter.getImageList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.chapter_image);
        }
    }

    public ChapterContentAdapter(Context mContext, Chapter chapter){
        this.chapter=chapter;
        this.mContext= mContext;
    }


}
