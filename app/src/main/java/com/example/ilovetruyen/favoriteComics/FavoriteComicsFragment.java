package com.example.ilovetruyen.favoriteComics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.FavoriteComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.databinding.FragmentFavoriteComicsBinding;
import com.example.ilovetruyen.model.Comic;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteComicsFragment extends Fragment {

    private FragmentFavoriteComicsBinding binding;
    private FavoriteComicAdapter favoriteComicAdapter;
    ComicAPI comicAPI;
    RetrofitService retrofitService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FavoriteComicsViewModel favoriteComicsViewModel =
                new ViewModelProvider(this).get(FavoriteComicsViewModel.class);

        binding = FragmentFavoriteComicsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        renderFavoriteComics(root);


        return root;
    }

    private void renderFavoriteComics(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.favoriteComicsRecycleView);
        favoriteComicAdapter = new FavoriteComicAdapter(requireContext());

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        comicAPI.getFavoriteComics().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(favoriteComicAdapter);
                    favoriteComicAdapter.setData(response.body());
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable throwable) {

            }
        });
    }
    //NOTE: đổ dữ liệu comics yêu thích ở đây
    private void getfavoriteComicsData() {

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