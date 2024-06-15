package com.example.ilovetruyen.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ilovetruyen.LoginActivity;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.ComicAdapter;
import com.example.ilovetruyen.databinding.FragmentHomeBinding;
import com.example.ilovetruyen.model.Comic;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.hot_comics);
        comicAdapter = new ComicAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(comicAdapter);
        comicAdapter.setData(getHotComics());
        Button home_login_btn = root.findViewById(R.id.home_login_btn);
        home_login_btn.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), LoginActivity.class);
            startActivity(intent);
        });
        return root;
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