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
import com.example.ilovetruyen.model.ContentImg;

import java.util.List;

public class ChapterContentAdapter extends RecyclerView.Adapter<ChapterContentAdapter.ViewHolder>{
    private Context mContext;
    private Chapter chapter;
    private List<ContentImg> contentImgList;
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
        if(contentImgList == null) return;
        ContentImg image = contentImgList.get(position);
        var width= holder.itemView.getWidth();
        //load ảnh với glide ??
        Glide.with(mContext)
                .load(image.url())
                .fitCenter()
                .into(holder.imageView);
    }
    public void setData(List<ContentImg> imageList){
        this.contentImgList = imageList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contentImgList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.chapterImage);
        }
    }

    public ChapterContentAdapter(Context mContext){
        this.mContext= mContext;
    }


}
