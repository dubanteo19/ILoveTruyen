package com.example.ilovetruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.database.FaComDAO;
import com.example.ilovetruyen.effect.BlinkingEffect;
import com.example.ilovetruyen.model.FavoriteComics;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.ui.dashboard.FavoriteComicsFragment;
import com.example.ilovetruyen.ui.dashboard.FavoriteComicsViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteComicAdapter extends RecyclerView.Adapter<FavoriteComicAdapter.FavoriteComicsViewHolder>{
    private Context context;
    private List<FavoriteComics> favoriteComicsList = new ArrayList<>();
    private OnDataChangeListener dataChangeListener;
    public FavoriteComicAdapter(Context context, OnDataChangeListener dataChangeListener) {
        this.context = context;
        this.dataChangeListener = dataChangeListener;
    }
    public interface OnDataChangeListener {
        void onDataChanged();
    }

    public void setData(List<FavoriteComics> favoriteComicsList) {
        this.favoriteComicsList = favoriteComicsList;
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
        try{
            if(favoriteComicsList.size() == 0 ){
                Toast.makeText(context.getApplicationContext(), "Danh sách trống", Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException e){
            Toast.makeText(context.getApplicationContext(), "Danh sách trống", Toast.LENGTH_SHORT).show();
        }
        FavoriteComics favoriteComics = favoriteComicsList.get(position);
        holder.bindData(favoriteComics); //bind data vào holder
        //effect
        BlinkingEffect.applyBlinkingEffect(holder.hotLableTV);//set blinking hot label
        holder.comicNameTv.setSelected(true);
        //action button
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComicDetailActivity.class);
                intent.putExtra("comicId",Integer.valueOf(favoriteComicsList.get(position).id()));
                context.startActivity(intent);
            }
        });
        holder.removeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Integer id = Integer.valueOf(favoriteComicsList.get(position).id());
                if(id == null){
                    Toast.makeText(v.getContext(), "Danh sách trống", Toast.LENGTH_SHORT).show();
                    return ;
                }else {
                    System.out.println("pre_delete"+id);
                    FaComDAO faComDAO = new FaComDAO(context);
                    faComDAO.open();
                    if(faComDAO.deleteData(String.valueOf(id))){
                        dataChangeListener.onDataChanged();
                        //lỗi khi chuyển về nút home thì data chuyển ngược , không update
                        System.out.println("remove favorite coms");
                    }else {
                        System.out.println("lỗi");
                    }

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteComicsList.size();
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

        public void bindData(FavoriteComics favoriteComics) {
            comicNameTv.setText(favoriteComics.name());
            Glide.with(itemView).load(favoriteComics.thumbUrl().trim()).into(thumb);
            String currentRead ;
            try{
                currentRead = favoriteComics.currRead(); //may be null
            }
            catch (IndexOutOfBoundsException e){
                currentRead ="1";
            }
            comicChapterTv.setText("Đang xem ch."+currentRead);
        }
    }

}
