package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.ui.comicDetail.ChapterContentActivity;

import java.util.List;

public class ChapterApdapter extends RecyclerView.Adapter<ChapterApdapter.ChapterViewHolder> {

    private Context context;
    private List<Chapter> chapterList;

    private static final int INITIAL_ITEM_COUNT = 10;
    private LinearLayout chapterItem;
    public ChapterApdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Chapter> chapterList){
        this.chapterList = chapterList;
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
//        System.out.println("OK chua"+chapter.comicDetail().comic().id());
        if (chapter == null) return;
        holder.chapterNameView.setText("Chương "+ chapter.count());
//        holder.createAt.setText(TimeDifference.getTimeDifference(chapter.createdDate()));
        chapterItem = holder.itemView.findViewById(R.id.detail_chapter_item);
        chapterItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChapterContentActivity.class);
            intent.putExtra("comicId", chapter.comicDetail().id());
            intent.putExtra("count", chapter.count());

//            // Nếu context không phải là Activity, thêm cờ FLAG_ACTIVITY_NEW_TASK
//            if (!(context instanceof android.app.Activity)) {
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }

            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return chapterList.size();
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
