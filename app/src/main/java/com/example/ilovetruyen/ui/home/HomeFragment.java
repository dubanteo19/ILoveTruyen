package com.example.ilovetruyen.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ilovetruyen.LoginActivity;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.CarouselAdapter;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.adapter.HotComicsAdatper;
import com.example.ilovetruyen.adapter.NewComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.databinding.FragmentHomeBinding;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.search.SearchActivity;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.github.islamkhsh.CardSliderViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ComicAdapter comicAdapter;
    private HotComicsAdatper hotComicsAdatper;
    private NewComicAdapter newComicAdapter;
    RetrofitService retrofitService;
    ComicAPI comicAPI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        retrofitService = new RetrofitService();
        comicAPI = retrofitService.getRetrofit().create(ComicAPI.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button home_login_btn = root.findViewById(R.id.home_login_btn);
        home_login_btn.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), LoginActivity.class);
            startActivity(intent);
        });
        SearchView searchView = root.findViewById(R.id.home_search_view);
        searchView.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), SearchActivity.class);
            startActivity(intent);
        });
        renderCarousel(root);
        renderTitle();
        renderReadingSection(root);
        renderRecommendComicsSection(root);
        renderNewComicsSection(root);
        renderHotComicsSection(root);

        return root;
    }

    private void showError() {
        Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
    }

    private void renderNewComicsSection(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.new_comics);
        newComicAdapter = new NewComicAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        comicAPI.getAllNewComics().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(newComicAdapter);
                    newComicAdapter.setData(response.body());
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {

            }
        });
    }

    private void renderHotComicsSection(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.hot_comics);
        hotComicsAdatper = new HotComicsAdatper(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        comicAPI.getAllHotComics().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(hotComicsAdatper);
                    hotComicsAdatper.setData(response.body());

                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {
            }
        });
    }

    private void renderRecommendComicsSection(View root) {
        RecyclerView recyclerView2 = root.findViewById(R.id.recommend_comics);
        comicAdapter = new ComicAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager);
        comicAPI.getAllRecommendationsComics().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView2.setAdapter(comicAdapter);
                    comicAdapter.setData(response.body());
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {
                showError();
            }
        });
    }


    private void renderReadingSection(View root) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment_reacently_read_comics, RecentlyReadFragment.newInstance());
        fragmentTransaction.commit();
    }

    private void renderTitle() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction
                .replace(R.id.home_fragment_recentLyReadComicsTitle, HomeSectionTitleFragment
                        .newInstance("Bạn vừa đọc", "", R.drawable.ic_home_clock));
        fragmentTransaction
                .replace(R.id.home_fragment_hotComicsTitle, HomeSectionTitleFragment
                        .newInstance("Hot nhất mọi thời gian", "", R.drawable.category_icon));
        fragmentTransaction
                .replace(R.id.home_fragment_recommendComicsTitle, HomeSectionTitleFragment
                        .newInstance("Gợi ý truyện tranh", "", R.drawable.thumb_up_icon));
        fragmentTransaction
                .replace(R.id.home_fragment_newComicsTitle, HomeSectionTitleFragment
                        .newInstance("Truyện tranh mới", "", R.drawable.category_icon));
        fragmentTransaction.commit();
    }

    private void renderCarousel(View root) {
        comicAPI.getAllComics().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                CardSliderViewPager cardSliderViewPager = root.findViewById(R.id.home_carousel);
                cardSliderViewPager.setAdapter(new CarouselAdapter(response.body()));
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {
                showError();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}