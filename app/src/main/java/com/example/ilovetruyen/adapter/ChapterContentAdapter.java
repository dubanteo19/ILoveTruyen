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
    private List<String> imageList;
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

        if(imageList == null) return;
        String image = imageList.get(position);
        //load ảnh với glide ??
        Glide.with(mContext)
                .load(image)
                .into(holder.imageView);
//        holder.imageView.setImageResource(Integer.parseInt(image));

    }
    public void setData(List<String> imageList){
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    private List<Integer> getImageData() {
        return null;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.chapterImage);
        }
    }

    public ChapterContentAdapter(Context mContext,Chapter chapter, List<String> imageList){
        this.chapter= chapter;
        this.imageList= imageList;
        this.mContext= mContext;
    }


}
