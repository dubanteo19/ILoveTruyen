package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.database.DBHelper;
import com.example.ilovetruyen.effect.BlinkingEffect;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.ui.dashboard.FavoriteComicsFragment;

import java.util.ArrayList;
import java.util.List;

public class FavoriteComicAdapter extends RecyclerView.Adapter<FavoriteComicAdapter.FavoriteComicsViewHolder>{
    private Context context;
    ArrayList<String> conmicsId, comicsName, comicsThumb, comicsCurr;
    private DBHelper dbHelper;
    public FavoriteComicAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<String> conmicsId,ArrayList<String> comicsName,ArrayList<String> comicsThumb, ArrayList<String> comicsCurr) {
        this.conmicsId = conmicsId;
        this.comicsName = comicsName;
        this .comicsThumb = comicsThumb;
        this.comicsCurr = comicsCurr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_comic_item,parent,false);
        return new FavoriteComicsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteComicsViewHolder holder, int position) {
        holder.comicNameTv.setText(comicsName.get(position));
        Glide.with(holder.itemView).load(comicsThumb.get(position).trim()).into(holder.thumb);
        String currentRead ;
        try{
         currentRead = comicsCurr.get(position);
        }
        catch (IndexOutOfBoundsException e){
            currentRead ="1";
        }
        holder.comicChapterTv.setText("Đang xem ch."+currentRead);
        BlinkingEffect.applyBlinkingEffect(holder.hotLableTV);//set blinking
        holder.comicNameTv.setSelected(true);
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComicDetailActivity.class);
                intent.putExtra("comicId",Integer.valueOf(conmicsId.get(position)));
                context.startActivity(intent);

            }
        });
        holder.removeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(v.getContext());
                Integer id = Integer.valueOf(conmicsId.get(position));
                if(id == null){
                    return ;
                }else {
                    System.out.println("pre_delete"+id);
                    System.out.println();
                    if(dbHelper.deleteData(String.valueOf(id-1))){
                        conmicsId.remove(position);
                        //lỗi khi chuyển về nút home thì data chuyển ngược , không update
                        setData(conmicsId,comicsName,comicsThumb,comicsCurr);
                        System.out.println("remove favorite coms");
                    }else {
                        System.out.println("lỗi");
                    }

                }

            }
        });
        System.out.println("chheck5");

    }

    @Override
    public int getItemCount() {
        return conmicsId.size();
    }

    public  class FavoriteComicsViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumb;
        private TextView hotLableTV,comicNameTv, comicChapterTv;
        private ImageButton removeBut ;
        private CardView itemCard;

        public FavoriteComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            hotLableTV =(TextView) itemView.findViewById(R.id.hot_lable);
            thumb = itemView.findViewById(R.id.favorite_comic_thumb);
            comicNameTv =(TextView) itemView.findViewById(R.id.marquee_comic_name);
            comicChapterTv = (TextView)itemView.findViewById(R.id.favorite_comic_currentChap);
            removeBut = itemView.findViewById(R.id.detail_removeBtn);
            itemCard = itemView.findViewById(R.id.favorite_comic_itemLayout);
        }
    }

}
