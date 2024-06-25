package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterApdapter extends RecyclerView.Adapter<ChapterApdapter.ChapterViewHolder> {

    private Context context;
    private List<Chapter> chapterList;

    private static final int INITIAL_ITEM_COUNT = 10;
    private boolean showAll = false;

    public ChapterApdapter(Context context) {
        this.context = context;
        this.chapterList = new ArrayList<>();
    }
    public void setData(List<Chapter> chapterList){
        this.chapterList = chapterList;
        notifyDataSetChanged();
    }
    public void showAll(){
        this.showAll = true;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChapterApdapter.ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item, parent, false);
        return new ChapterApdapter.ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        var chapter = chapterList.get(position);
        if (chapter == null) return;
        holder.chapterNameView.setText("Chương "+ chapter.count());
//        holder.createAt.setText(TimeDifference.getTimeDifference(chapter.createdDate()));
    }

    @Override
    public int getItemCount() {
        if (showAll) {
            return chapterList != null ? chapterList.size() : 0;
        } else {
            return Math.min(chapterList != null ? chapterList.size() : 0, INITIAL_ITEM_COUNT);
        }
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView chapterNameView;
        private TextView createAt;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterNameView = itemView.findViewById(R.id.chapter_fragment_title);
//            createAt = itemView.findViewById(R.id.chapter_fragment_time);

        }
    }
}
