package com.example.ilovetruyen.ui.favoriteComics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.FavoriteComicAdapter;
import com.example.ilovetruyen.database.FaComDAO;
import com.example.ilovetruyen.databinding.FragmentFavoriteComicsBinding;

public class FavoriteComicsFragment extends Fragment implements FavoriteComicAdapter.OnDataChangeListener {
    private FragmentFavoriteComicsBinding binding;
    private FavoriteComicAdapter favoriteComicAdapter;
        View view;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentFavoriteComicsBinding.inflate(inflater, container, false);
            view = binding.getRoot();
            renderFavoriteComics(view);
            return view;
        }

        private void renderFavoriteComics(View view) {
            RecyclerView recyclerView = view.findViewById(R.id.favoriteComicsRecycleView);
            favoriteComicAdapter = new FavoriteComicAdapter(getContext(),this);
            recyclerView.setAdapter(favoriteComicAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
             FaComDAO faComDAO = new FaComDAO(getContext());
            faComDAO.openForReading();
            var favoriteList = faComDAO.getAllFavoriteComics();
            if(!favoriteList.isEmpty()) {
                favoriteComicAdapter.setData(favoriteList);
            }
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDataChanged() {
        renderFavoriteComics(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        renderFavoriteComics(view);
    }
}