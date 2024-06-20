package com.example.ilovetruyen.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.LoginActivity;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.CarouselAdapter;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.adapter.HotComicsAdatper;
import com.example.ilovetruyen.adapter.NewComicAdapter;
import com.example.ilovetruyen.databinding.FragmentHomeBinding;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.ui.search.SearchActivity;
import com.github.islamkhsh.CardSliderViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private HotComicsAdatper hotComicsAdatper;
    private NewComicAdapter newComicAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

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
        renderHotComicsSection(root);
        renderNewComicsSection(root);
        return root;
    }

    private void renderNewComicsSection(View root) {
        recyclerView  = root.findViewById(R.id.new_comics);
        newComicAdapter = new NewComicAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newComicAdapter);
        newComicAdapter.setData(getHotComics());
    }

    private void renderHotComicsSection(View root) {
        recyclerView = root.findViewById(R.id.hot_comics);
        hotComicsAdatper = new HotComicsAdatper(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hotComicsAdatper);
        hotComicsAdatper.setData(getHotComics());
    }

    private void renderRecommendComicsSection(View root) {
        recyclerView = root.findViewById(R.id.recommend_comics);
        comicAdapter = new ComicAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(comicAdapter);
        comicAdapter.setData(getHotComics());
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
        var comics = getHotComics();
        CardSliderViewPager cardSliderViewPager = root.findViewById(R.id.home_carousel);
        cardSliderViewPager.setAdapter(new CarouselAdapter(comics));
    }
        private List<Comic> getHotComics () {
            var hotComics = new ArrayList<Comic>();
            var comic1 = new Comic("One Piece", R.drawable.one_piece);
            var comic2 = new Comic("Thanh Guong diet quy", R.drawable.thanh_guom_diet_quy);
            var comic3 = new Comic("One Punchman", R.drawable.one_puch_man);
            hotComics.add(comic1);
            hotComics.add(comic2);
            hotComics.add(comic3);
            hotComics.add(comic1);
            hotComics.add(comic2);
            hotComics.add(comic3);
            hotComics.add(comic1);
            hotComics.add(comic2);
            hotComics.add(comic3);
            return hotComics;
        }

        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }
    }