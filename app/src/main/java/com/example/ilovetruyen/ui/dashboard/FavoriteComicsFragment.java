package com.example.ilovetruyen.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.FavoriteComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.database.DBHelper;
import com.example.ilovetruyen.databinding.FragmentFavoriteComicsBinding;
import com.example.ilovetruyen.model.FavoriteComics;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.List;

public class FavoriteComicsFragment extends Fragment {
    private FragmentFavoriteComicsBinding binding;
    FavoriteComicsViewModel favoriteComicsViewModel;
    private FavoriteComicAdapter favoriteComicAdapter;
    private DBHelper dbHelper;
    View view;
    ComicAPI comicAPI;
    RetrofitService retrofitService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteComicsBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        favoriteComicsViewModel= new ViewModelProvider(this).get(FavoriteComicsViewModel.class);
        renderFavoriteComics(view);
        return view;
    }

    private void renderFavoriteComics(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.favoriteComicsRecycleView);
        favoriteComicAdapter = new FavoriteComicAdapter(getContext(),favoriteComicsViewModel);
        recyclerView.setAdapter(favoriteComicAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        testData();
        favoriteComicsViewModel.getDataList(getContext()).observe(getViewLifecycleOwner(), new Observer<List<FavoriteComics>>() {
            @Override
            public void onChanged(List<FavoriteComics> favoriteComics) {
                if(favoriteComics.size() == 0){
                    Toast.makeText(requireContext(),"Danh sách trống", Toast.LENGTH_SHORT).show();
                }
                favoriteComicAdapter.setData(favoriteComics);
            }
        });


    }
    private void showError() {
        Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}