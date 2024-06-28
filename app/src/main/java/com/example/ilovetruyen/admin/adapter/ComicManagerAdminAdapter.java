package com.example.ilovetruyen.admin.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.HistoryAdapter;
import com.example.ilovetruyen.admin.AddComicActivity;
import com.example.ilovetruyen.admin.FeatureComicActivity;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Category;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.ComicDetail;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.example.ilovetruyen.ui.comicDetail.ComicDetailActivity;
import com.example.ilovetruyen.util.NameMaxSizeHelper;
import com.example.ilovetruyen.util.UserStateHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicManagerAdminAdapter extends RecyclerView.Adapter<ComicManagerAdminAdapter.ComicManagerAdminViewHolder>{
    private Context context;
    private List<Comic> comicList;
    private ComicDetailAPI comicDetailAPI;
    private RetrofitService retrofitService;
    private ComicDetail comicDetail;
    String genresText, genresDes;

    public ComicManagerAdminAdapter(Context context) {
        this.context = context;
        this.comicList = new ArrayList<>();
    }

    public void setData(List<Comic> comicList) {
        this.comicList.clear();
        if (comicList != null) {
            this.comicList.addAll(comicList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicManagerAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_comic_admin, parent, false);
        return new ComicManagerAdminAdapter.ComicManagerAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicManagerAdminViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        if (comic == null) return;
        Glide.with(holder.itemView).load(comic.thumbUrl()).into(holder.comic_thumb);
        holder.comic_title.setText("Tên:" +NameMaxSizeHelper.truncateName(comic.name()));
        holder.comic_chapters.setText("Chương: " + String.valueOf(comic.latestChapter()));
        holder.comic_views.setText(String.valueOf("Lượt xem: " + comic.views()));
        holder.comic_likes.setText(String.valueOf("Lượt like: " + comic.likes()));
        holder.comic_author.setText("Tác giả: DBT19");
        fetchComicDetail(comic.id());
        holder.comic_genre.setText("Danh mục: " + genresText);
        holder.comic_description.setText("Mô tả: " + NameMaxSizeHelper.truncateName(genresDes));
        holder.editBtn.setOnClickListener(v->{
            UserStateHelper.saveEditComicStatus(context, true);
            Intent intent = new Intent(context, AddComicActivity.class);
            intent.putExtra("comicId",comic.id());
            context.startActivity(intent);
        });
        holder.deleteBtn.setOnClickListener(v ->{
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa truyện này khỏi ứng dụng?")
                    .setNeutralButton("Hủy bỏ", (dialog, which) -> {

                    })
                    .setPositiveButton("Đồng ý", (dialog, which) -> {

                    })
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return comicList.size() > 0 ? comicList.size() : 0;
    }

    public class ComicManagerAdminViewHolder extends RecyclerView.ViewHolder {
        private TextView comic_title, comic_author, comic_genre, comic_chapters, comic_views, comic_likes,comic_description;
        private ImageView comic_thumb,editBtn, deleteBtn;

        public ComicManagerAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            comic_title = itemView.findViewById(R.id.comic_title);
            comic_author = itemView.findViewById(R.id.comic_author);
            comic_genre = itemView.findViewById(R.id.comic_genre);
            comic_chapters = itemView.findViewById(R.id.comic_chapters);
            comic_views = itemView.findViewById(R.id.comic_views);
            comic_likes = itemView.findViewById(R.id.comic_likes);
            comic_description = itemView.findViewById(R.id.comic_description);
            comic_thumb = itemView.findViewById(R.id.comic_thumb);
            editBtn = itemView.findViewById(R.id.edit);
            deleteBtn = itemView.findViewById(R.id.delete);
        }
    }
    private void fetchComicDetail(int comicId) {
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);
        comicDetailAPI.getComicDetailById(comicId).enqueue(new Callback<ComicDetail>() {
            @Override
            public void onResponse(Call<ComicDetail> call, Response<ComicDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    comicDetail = response.body();
                    List<Category> genres = comicDetail.categories();
                    System.out.println(genres + "============================");
                    if (genres != null && !genres.isEmpty()) {
                        List<String> genreNames = new ArrayList<>();
                        for (Category genre : genres) {
                            genreNames.add(genre.name());
                        }
                        genresDes = comicDetail.description();
                        genresText = TextUtils.join(", ", genreNames);
                    }
                }
            }

            @Override
            public void onFailure(Call<ComicDetail> call, Throwable throwable) {
                Toast.makeText(context.getApplicationContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
            }
        });
    }

}
