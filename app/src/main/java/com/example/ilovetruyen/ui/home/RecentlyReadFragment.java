package com.example.ilovetruyen.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.api.ComicDetailAPI;
import com.example.ilovetruyen.model.Chapter;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.model.ComicDetail;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.example.ilovetruyen.util.NameMaxSizeHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecentlyReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentlyReadFragment extends Fragment {
    private ImageView recent_read_thumb;
    private TextView comic_name, like, views, comic_count;
    private SharedPreferences sharedPreferences;
    private LinearLayout layout;
    private RetrofitService retrofitService;
    private ComicDetailAPI comicDetailAPI;
    private Comic comic;
    int recentlyComicId;

    public RecentlyReadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentlyReadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentlyReadFragment newInstance() {
        RecentlyReadFragment fragment = new RecentlyReadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        int recentlyComicId = sharedPreferences.getInt("recentlyComicId", -1);
        fetchComicDetail(recentlyComicId);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recently_read, container, false);
        comic_name = view.findViewById(R.id.comic_name);
        comic_count = view.findViewById(R.id.comic_count);
        like = view.findViewById(R.id.like);
        layout = view.findViewById(R.id.recent_read_container);
        views = view.findViewById(R.id.view);
        recent_read_thumb = view.findViewById(R.id.recent_read_thumb);
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", MODE_PRIVATE);
        recentlyComicId = sharedPreferences.getInt("recentlyComicId", -1);
        System.out.println(recentlyComicId + "=========================================");
        if (recentlyComicId != -1) {
            fetchComicDetail(recentlyComicId);
        }
        return view;
    }

    private void fetchComicDetail(int comicId) {
        retrofitService = new RetrofitService();
        comicDetailAPI = retrofitService.getRetrofit().create(ComicDetailAPI.class);

        comicDetailAPI.getComicDetailById(comicId).enqueue(new Callback<ComicDetail>() {
            @Override
            public void onResponse(Call<ComicDetail> call, Response<ComicDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    comic = response.body().comic();
                    comic_name.setText(NameMaxSizeHelper.truncateName(comic.name()));
                    comic_count.setText(String.valueOf(comic.latestChapter()));
                    like.setText(String.valueOf(comic.likes()));
                    views.setText(String.valueOf(comic.views()));
                    Glide.with(getContext())
                            .load(comic.thumbUrl())
                            .into(new CustomTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    resource.setAlpha(40);
                                  layout.setBackground(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                    Glide.with(getContext())
                            .load(comic.thumbUrl())
                            .into(recent_read_thumb);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ComicDetail> call, Throwable throwable) {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
            }
        });
    }
}